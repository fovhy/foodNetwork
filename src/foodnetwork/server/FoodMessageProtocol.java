/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 3
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.server;

import edu.baylor.googlefit.FoodManager;
import foodnetwork.serialization.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;


/**
 *  The protocol for Server to communicate with client. It has a 10 second timeout time for each
 *  established connections.
 */
public class FoodMessageProtocol implements Runnable{
    private static final String TIMELIMIT = "10000"; // Default time out time (ms)
    private static final String TIMELIMITPROP = "Timelimit"; // property
    private static final String addFoodRequest = "ADD";
    private static final String getFoodRequest = "GET";
    private static final String intervalRequest = "INTERVAL";

    private static int timelimit;
    private Socket clntSock;
    private FoodManager foodManager;
    private Logger logger;
    private MessageInput in;
    private MessageOutput out;

    /**
     * Constructor for the protocol
     * @param aClntSock the client socket
     * @param logger the logger used to log
     * @param foodManager the food manager used to connect with google
     * @throws IOException if the server disconnected with client, or the IOStream simply fails
     */
    public FoodMessageProtocol(Socket aClntSock, Logger logger, FoodManager foodManager) throws IOException {
        this.clntSock = aClntSock;
        timelimit = Integer.parseInt(System.getProperty(TIMELIMITPROP, TIMELIMIT));
        this.logger = logger;
        this.foodManager = foodManager;
        // hook the input and output to socket io
        this.in = new MessageInput(this.clntSock.getInputStream());
        this.out = new MessageOutput(this.clntSock.getOutputStream());
        int recvMsgSize;    // size of received message
   }

    /**
     * The execute or run function for sending and receiving FoodMessage over a single thread
     */
    public void  handleFoodNetworkClient(){
        try{
            logNewConnection();
            int totalBytes = 0; // bytes received from client
            long endTime = System.currentTimeMillis() + timelimit;  // when it shall end
            int timeBoundMillis = timelimit;
            clntSock.setSoTimeout(timeBoundMillis); // set up time out time
            FoodMessage message = null;
            try {
                message = FoodMessage.decode(in);
            }catch (FoodNetworkException e){
                if(e.getMessage().contains("Version")){
                    sendErrorMessage("Unexpected version: " + e.getMessage(), System.currentTimeMillis());
                }else if(e.getMessage().contains("Unknown operation:")){
                    sendErrorMessage(e.getMessage(), System.currentTimeMillis());
                }else {
                    sendErrorMessage("Unable to parse message", System.currentTimeMillis());
                }
            }
            if(message != null) {
                switch (message.getRequest()) {
                    case addFoodRequest:
                        addFoodToServer((AddFood) message);
                        logReceiveMessage(message);
                        break;
                    case getFoodRequest:
                        getListFromServer();
                        logReceiveMessage(message);
                        break;
                    case intervalRequest:
                        getIntervalListFromServer((Interval) message);
                        logReceiveMessage(message);
                        break;
                    default:
                        sendErrorMessage("Unexpected messageType: " + message.getRequest(),
                                message.getMessageTimestamp());
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FoodNetworkException e) {
            e.printStackTrace();
        }
    }

    /**
     * The interface for runnable. Establish a unicast between client and server
     */
    @Override
    public void run() {
        handleFoodNetworkClient();
    }

    /**
     * Send error message to client
     * @param message error message
     * @param timestamp when it is being sent
     * @throws FoodNetworkException illegal message
     */
    public void sendErrorMessage(String message, long timestamp) throws FoodNetworkException {
        ErrorMessage temp = new ErrorMessage(timestamp, message);
        temp.encode(out);
        logSentMessage(temp);
    }

    /**
     * A message to Google server to add the food to the server
     * @param addFood the add food message to be sent
     * @throws FoodNetworkException illegal addFood message
     */
    public void addFoodToServer(AddFood addFood) throws FoodNetworkException {
        FoodItem foodItem = addFood.getFoodItem();
        foodManager.addFood(foodItem.getName(), foodItem.getMealType(),
                foodItem.getCalories(), foodItem.getFat());
    }

    /**
     * Get list from google server
     * @throws FoodNetworkException illegal list
     */
    public void getListFromServer() throws FoodNetworkException {
        FoodList temp = new FoodList(System.currentTimeMillis(), foodManager.getLastModified());
        List<FoodItem> tempList = foodManager.getFoodItems();
        for(FoodItem i : tempList){
            temp.addFoodItem(i);
        }
        temp.encode(out);
        logSentMessage(temp);
    }

    /**
     * Get list from Google server within a certain amount of interval
     * @param interval the time frame for data
     * @throws FoodNetworkException ileegal interval
     */
    public void getIntervalListFromServer(Interval interval) throws FoodNetworkException {
        FoodList temp = new FoodList(System.currentTimeMillis(), foodManager.getLastModified());
        List<FoodItem> tempList = foodManager.getFoodItems(interval.getIntervalTime());
        for(FoodItem i : tempList){
            temp.addFoodItem(i);
        }
        temp.encode(out);
        logSentMessage(temp);
    }

    /**
     * Log there is a new connection
     */
    public void logNewConnection(){
        logger.log(INFO, "Handling client: " + getClientIP() + "with thread id " +
                Thread.currentThread().getName() + '\n');
    }

    /**
     * Log receiving a message from client
     * @param message the message it received
     */
    public void logReceiveMessage(FoodMessage message){
        logger.log(INFO, "Receive from: " + getClientIP() + message.toString() + '\n');
    }

    /**
     * Log sending message to Client
     * @param message the message it sent out
     */
    public void logSentMessage(FoodMessage message){
        logger.log(INFO, "Sent to: " + getClientIP() + message.toString() + '\n');
    }

    /**
     * Helper function that returns a string of client IP and port
     * @return client IP and port
     */
    public String getClientIP(){
        return clntSock.getRemoteSocketAddress() + "-" + clntSock.getPort() + " ";
    }
}
