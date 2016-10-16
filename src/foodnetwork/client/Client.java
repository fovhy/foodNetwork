/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 2
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.client;

import foodnetwork.serialization.*;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.Instant;

import static foodnetwork.client.Client.States.*;


/**
 * A simple client for user to communicate with a server using foodMessage. It will print out error
 * if the communication fails.
 */
public class Client {
    /*
    Store common message headings here.
     */
    private final static String commuError = "Unable to communicate: ";     // communicate error message header
    private final static String invalidMessage = "Invalid message: ";       // invalid message header
    private final static String unexpectedMessage = "Unexpected message: "; // unexpected message header
    private final static String errorMessage = "Error: ";                   // Receive error header
    private final static String invalidInput = "Invalid user input: ";      // Local client validation header

    private final static String caseAdd = "ADD";                            // ADD choice
    private final static String caseGet = "GET";                            // GET choice
    private final static String caseInterval = "INTERVAL";
    private final static String request = "Request(" + caseAdd + "|" +
            caseGet + "|" + caseInterval + ")"; // request
    private final static String setInterval = "Interval> ";
    private final static String selName = "Name> ";                         // select name prompt
    private final static String selMealType = "Meal Type(B, L, D, S)> ";    // select MealType code prompt
    private final static String enterCalories = "Calories> ";               // enter calories prompt
    private final static String enterFat = "Fat> ";                         // enter fat prompt
    private final static String askContinue = "Continue (y/n)> ";           // continue? prompt
    /**
     * The different steps of the whole client. From getRequest to the end.
     */
    public enum States{
        getRequest,
        getAndSendInterval,
        getName,
        getMealType ,
        getCalories ,
        getFat ,
        waitServerRespond,
        askAgain ,
        sendAddFood,
        sendGetFood,
        end
    }
    public static void main(String args[]) throws IOException, FoodNetworkException {
        if (args.length != 2) {          //check if user entered server and port or not
            throw new IllegalArgumentException("Parameters(s): <Server> <Port>");
        }
        String server = args[0]; // the server address
        int serPort = Integer.parseInt(args[1]); // server port
        Socket socket;                           // the TCP socket with the server
        try {
            socket = new Socket(server, serPort);
        } catch (IOException e) {
            throw new IOException(commuError + "Failed to create socket");
        }
        InputStream in = socket.getInputStream();      // TCP socket inputStream
        OutputStream out = socket.getOutputStream();   // TCP socket outputStream
        System.out.println("Successfully connected to server...");
        /*
         * Create a list that contains all types of mealType, so you can loop through them
         */
       MessageInput messageInput = new MessageInput(in);      // the messageInput that wraps input from Socket
        MessageOutput messageOutput = new MessageOutput(out);  // the messageOutput that wraps output to Socket
        MessageInput userMessageInput = new MessageInput(System.in); // get user input
        processInput(messageInput, userMessageInput, messageOutput);
    }

    /**
     * Send a AddFoodMessage to a outputStream using a MessageOutput object
     * @param foodItem the foodItem to add
     * @param out the MessageOutput object that wraps around the outputStream
     * @return next state for the client
     */
    public static States sendAddFoodMessage(FoodItem foodItem, MessageOutput out){
        try {
            new AddFood(Instant.now().toEpochMilli(), foodItem).encode(out);
        } catch (FoodNetworkException e) {
            System.err.print(commuError + "Failed to send " + caseAdd + " message to server. " + e.getMessage());
            System.exit(1);
        }
        return States.waitServerRespond;
    }

    /**
     * Get the request from user input
     * @param in MessageInput object that wraps around user input
     * @return next state for the client
     * @throws FoodNetworkException Failed to read in Next line of user input
     */
    public static States getRequest(MessageInput in) throws FoodNetworkException {
        String userInput = in.getNextLine();
        States steps = getRequest;
        switch (userInput){
            case caseAdd:
                steps = States.getName;
                break;
            case caseGet:
                steps = States.sendGetFood;
                break;
            case caseInterval:
                steps = States.getAndSendInterval;
                break;
            default:
                System.err.println(invalidInput + "please enter " + caseAdd + " or " + caseGet +
                        " or " + caseInterval);
                break;
        }
        return steps;
    }

    /**
     * Get the foodItem name from user
     * @param foodItem foodItem to be added to server
     * @param in MessageInput object that wraps around server's InputStream
     * @return next state for the client
     */
    public static States getName(FoodItem foodItem, MessageInput in){
        States steps = States.getName;
        try {
            foodItem.setName(in.getNextLine());
        } catch (FoodNetworkException e) {
            System.err.println(invalidInput + "Failed to set name " + e.getMessage());
            return steps;
        }
        steps = States.getMealType;
        return steps;
    }

    /**
     * Get the mealType code from user
     * @param foodItem foodItem to be added to server
     * @param in MessageInput object that wraps around server's InputStream
     * @return next state for the client
     * @throws FoodNetworkException Failed to read in Next line of user input
     */
    public static States getMealType(FoodItem foodItem, MessageInput in) throws FoodNetworkException {
        States steps = States.getMealType;
        String userInput= in.getNextLine();
        if(userInput.length()!= 1){           // if user input is not a char
            System.err.println(invalidInput + "MealType code must be a single character");
            return steps;
        }
        try {
            foodItem.setMealType(MealType.getMealType(userInput.charAt(0)));         // get the character
        } catch (FoodNetworkException e) {
            System.err.println(invalidInput + "Wrong MealType code " + e.getMessage());
            return steps;
        }
        steps = States.getCalories;
        return steps;
    }

    /**
     * Get the calories of the food from user
     * @param foodItem foodItem to be added to server
     * @param in MessageInput object that wraps around server's InputStream
     * @return next state for the client
     * @throws FoodNetworkException Failed to read in Next line of user input
     */
    public static States getCalories(FoodItem foodItem, MessageInput in) throws FoodNetworkException {
        States steps = States.getCalories;
        try {
            foodItem.setCalories(in.getNextUnsignedLong());
        } catch (FoodNetworkException | EOFException e) {
            System.err.println(invalidInput + "Failed to set calories.");
            in.getNextLine();                     // clear the buffer
            return steps;
        }
        steps = States.getFat;
        return steps;
    }

    /**
     * Get the fat from user input
     * @param foodItem foodItem to be added to server
     * @param in MessageInput object that wraps around server's InputStream
     * @return next state for the client
     * @throws FoodNetworkException Failed to read in Next line of user input
     */
    public static States getFat(FoodItem foodItem, MessageInput in) throws FoodNetworkException {
        States steps = States.getFat;
        try {
            foodItem.setFat(in.getNextUnsignedDouble());
        } catch (FoodNetworkException | EOFException e) {
            System.err.println(invalidInput + "Failed to set fat " + e.getMessage());
            in.getNextLine();            // clear the buffer
            return steps;
        }
        steps = States.sendAddFood;
        return steps;
    }

    /**
     * Ask if user want to continue
     * @param in MessageInput object that wraps around user input
     * @return next state for the client
     * @throws FoodNetworkException Failed to read in Next line of user input
     */
    public static States getContinue(MessageInput in) throws FoodNetworkException {
        String userInput = in.getNextLine();
        switch(userInput){
            case "y":
                return States.getRequest;
            case "n":
                return States.end;
            default:
                System.err.println(invalidInput + " please enter y or n");
                return States.askAgain;
        }
    }

    /**
     * Send GetFood message to the server
     * @param out MessageOutput object that wraps around the Socket's outputStream
     * @return next state for the client
     */
    public static States sendGetFoodMessage(MessageOutput out){
        States steps;
        try {
            new GetFood(Instant.now().toEpochMilli()).encode(out);
        } catch (FoodNetworkException e) {
            System.err.println(commuError + " failed to send GetFoodMessage. " + e.getMessage());
            System.exit(-2);
        }
        steps = States.waitServerRespond;
        return steps;
    }

    /**
     * Wait for server's response
     * @param in MessageInput that wraps around Socket's inputStream
     * @return next state for the client
     */
    public static States waitResponse(MessageInput in){
        States steps;
        FoodMessage message = null;
        try {
            message = FoodMessage.decode(in);
        } catch (FoodNetworkException | EOFException e) {
            System.err.println(invalidMessage + e.getMessage());
            System.exit(2);
        }
        if(message instanceof ErrorMessage){
            System.out.println(errorMessage + message.toString());
        }else if(message instanceof FoodList){
            System.out.println(message.toString());
        }else{
            System.out.println(unexpectedMessage + message.toString());
        }
        steps = States.askAgain;
        return steps;
    }
    public static States getAndSendInterval(MessageInput in, MessageOutput out){
        int interval;
        try {
            interval = in.getNextUnsignedInt();
        } catch (EOFException | FoodNetworkException e) {
            System.err.println(invalidInput + " failed to set interval time. " + e.getMessage());
            return getAndSendInterval;                        // loop back, reenter the string
        }
        try {
            new Interval(System.currentTimeMillis(), interval).encode(out);
        } catch (FoodNetworkException e) {
            System.err.println(commuError + " failed to send Interval Message. " + e.getMessage());
            System.exit(-2);
        }
        return States.waitServerRespond;
    }
    public static void processInput(MessageInput messageInput, MessageInput userMessageInput,
                                    MessageOutput messageOutput) throws FoodNetworkException {
         /*possible foodItem to be added in the method*/
        FoodItem foodItem = new FoodItem("temp", MealType.Snack, 0, "0");
        States steps = getRequest;           // set the step to the first one
        while (steps != States.end) {         // the 7th step, out of the loop, terminate the program
            switch (steps) {
                case getRequest:               // this step you get request from user (ADD OR GET for now)
                    System.out.print(request);
                    steps = getRequest(userMessageInput);
                    break;
                case sendGetFood:              // this send a GetFood message to server asking for foodList.
                    steps = sendGetFoodMessage(messageOutput);
                    break;
                case getAndSendInterval:
                    System.out.print(setInterval);
                    steps = getAndSendInterval(messageInput, messageOutput);
                    break;
                case getName:                  // get the name for foodItem from user
                    System.out.print(selName);
                    steps = getName(foodItem, userMessageInput);
                    break;
                case getMealType:               // get the mealType from user
                    System.out.print(selMealType);
                    steps = getMealType(foodItem, userMessageInput);
                    break;
                case getCalories:               // get calories from user
                    System.out.print(enterCalories);
                    steps = getCalories(foodItem, userMessageInput);
                    break;
                case getFat:                    // get fat from user. After this success, go to sendAddFood
                    System.out.print(enterFat);
                    steps = getFat(foodItem, userMessageInput);
                    break;
                case sendAddFood:              // sent a addFood message to server
                    steps = sendAddFoodMessage(foodItem, messageOutput);
                    break;
                case waitServerRespond:        // wait for server's response
                    steps = waitResponse(messageInput);
                    break;
                case askAgain:                // ask if the user want to do one more request
                    System.out.print(askContinue);
                    steps = getContinue(userMessageInput);
                    break;
            }
        }
    }
}

