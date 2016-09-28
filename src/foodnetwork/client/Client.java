package foodnetwork.client;

import foodnetwork.serialization.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static foodnetwork.client.Client.states.*;

/**
 * A simple client for user to communicate with a server using foodMessage. It will print out error
 * if the communication fails.
 */
public class Client {
    private final static String commuError = "Unable to communicate: ";
    private final static String invalidMessage = "Invalid message: ";
    private final static String unexpectedMessage = "Unexpected message: ";
    private final static String errorMessage = "Error: ";
    private final static String invalidInput = "Invalid user input: ";
    private final static String request = "Request(ADD|GET)> ";
    private final static String selName = "Name> ";
    private final static String selMealType = "Meal Type(B, L, D, S)> ";
    private final static String enterCalories = "Calories> ";
    private final static String enterFat = "Fat> ";
    private final static String askContinue = "Continue (y/n)> ";

    /**
     * The different steps of the whole client. From getRequest to the end.
     */
    public enum states{
        getRequest,
        getName,
        getMealType ,
        getCalories ,
        getFat ,
        waitServerRespond,
        askAgain ,
        sendAddFood,
        end
    }
    public static void main(String args[]) throws IOException, FoodNetworkException {
        if (args.length != 2) {
            throw new IllegalArgumentException("Parameters(s): <Server> <Port>");
        }
        String server = args[0]; // the server address
        int serPort = Integer.parseInt(args[1]); // server port
        Socket socket;
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
        final List<String> optionsForMealType = Arrays.asList("B", "L", "D", "S");
        states steps = getRequest;           // set the step to the first one
        String userInput;                    // store user input
        Scanner reader = new Scanner(System.in);
        String name = null;
        char mealType = 'K';
        long calories = 0;
        String fat = null;
        MessageInput messageInput = new MessageInput(in);
        MessageOutput messageOutput = new MessageOutput(out);
        while (steps != states.end) {         // the 7th step, out of the loop
            switch (steps) {
                case getRequest:
                    System.out.print(request);
                    userInput = reader.nextLine();
                    switch (userInput){
                        case "ADD":
                            steps = getName;
                            break;
                        case "GET":
                            steps = waitServerRespond;
                            new GetFood(Instant.now().toEpochMilli()).encode(messageOutput);
                            break;
                        default:
                            System.err.println(invalidInput + "please enter ADD or GET");
                    }
                    break;
                case getName:
                    System.out.print(selName);
                    userInput = reader.nextLine();
                    if("".equals(userInput) || userInput == null){
                        System.err.println(invalidInput + "please enter a non empty string");
                    }else{
                        name = userInput;
                        steps = getMealType;
                    }
                    break;
                case getMealType:
                    System.out.print(selMealType);
                    userInput = reader.nextLine();
                    if(optionsForMealType.contains(userInput)){
                        mealType = userInput.charAt(0);
                        steps = getCalories;
                    }
                    break;
                case getCalories:
                    System.out.print(enterCalories);
                    userInput = reader.nextLine();
                    if((calories = validateUnsignedLong(userInput)) < 0){
                        System.err.println(invalidInput + "please enter a unsigned long");
                    }else{
                        steps = getFat;
                    }
                    break;
                case getFat:
                    System.out.print(enterFat);
                    userInput = reader.nextLine();
                    if(userInput.matches("^[0-9]*\\.?[0-9]+$")){
                        fat = userInput;
                        steps = sendAddFood;
                    }
                    break;
                case sendAddFood:
                    try {
                        new AddFood(Instant.now().toEpochMilli(),
                                new FoodItem(name, MealType.getMealType(mealType), calories, fat)).encode(messageOutput);
                    } catch (FoodNetworkException e) {
                        System.out.println(invalidInput + "bad request " + e.getMessage());
                        steps = getRequest;
                    }
                    steps = waitServerRespond;
                    break;
                case waitServerRespond:
                    FoodMessage message;
                    try {
                        message = FoodMessage.decode(messageInput);
                    } catch (FoodNetworkException e) {
                        throw new FoodNetworkException(invalidMessage + e.getMessage());
                    }
                    if(message instanceof ErrorMessage){
                        System.out.println(errorMessage + message.toString());
                    }else if(message instanceof FoodList){
                        System.out.println(message.toString());
                        steps = askAgain;
                    }else{
                        System.out.println(unexpectedMessage + message.toString());
                        steps = askAgain;
                    }
                    break;
                case askAgain:
                    System.out.print(askContinue);
                    userInput = reader.nextLine();
                    if("n".equals(userInput)) {
                        steps = end;
                    }else if("y".equals(userInput)){
                        steps = getRequest;
                    }else{
                        System.err.println(invalidInput + "please enter y or n");
                    }
                    break;
            }
        }
    }

    private static long validateUnsignedLong(String userInput) {
        long value = -1;
        try {
            value = Long.parseLong(userInput);
        } catch (NumberFormatException e) {
            return value;  // leave the loop directly
        }
        return value;
    }

}
