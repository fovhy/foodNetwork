package foodnetwork.server;

import edu.baylor.googlefit.FoodManager;
import foodnetwork.serialization.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Logger;



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
    public void  handleFoodNetworkClinet(Socket clntSock, Logger logger,
                                               FoodManager foodManager){
        try{
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
                        break;
                    case getFoodRequest:
                        getListFromServer();
                        break;
                    case intervalRequest:
                        getIntervalListFromServer((Interval) message);
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

    @Override
    public void run() {
        handleFoodNetworkClinet(this.clntSock, this.logger, this.foodManager);
    }
    public void sendErrorMessage(String message, long timestamp) throws FoodNetworkException {
        new ErrorMessage(timestamp, message).encode(out);
    }
    public void addFoodToServer(AddFood addFood) throws FoodNetworkException {
        FoodItem foodItem = addFood.getFoodItem();
        foodManager.addFood(foodItem.getName(), foodItem.getMealType(),
                foodItem.getCalories(), foodItem.getFat());
    }
    public void getListFromServer() throws FoodNetworkException {
        FoodList temp = new FoodList(System.currentTimeMillis(), foodManager.getLastModified());
        foodManager.getFoodItems().forEach(temp::addFoodItem);
        temp.encode(out);
    }
    public void getIntervalListFromServer(Interval interval) throws FoodNetworkException {
        FoodList temp = new FoodList(System.currentTimeMillis(), foodManager.getLastModified());
        foodManager.getFoodItems(interval.getIntervalTime()).forEach(temp::addFoodItem);
        temp.encode(out);
    }
}
