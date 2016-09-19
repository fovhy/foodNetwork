/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 1
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization;

import java.io.EOFException;
import java.io.IOException;

/**
 * An abstract class that represents a FoodMessage. It provides a genral form for all the foodMessage subclass to
 * encode and decode. The only abstract method is getRequest here.
 *
 */
abstract public class FoodMessage {
    protected long timestamp;
    /**
     * Encode a foodMessage subclass object
     * @param out MessageOutput object that wraps around a outputStream
     * @throws FoodNetworkException if fails to encode foodMessage
     */
    public void encode(MessageOutput out) throws FoodNetworkException {
        try {
            out.writeAndStore("FN1.0 " + timestamp + " " + this.getRequest() + "\n");
        }catch (IOException e){
            throw new FoodNetworkException("OutputStream closes prematurely", e);
        }
    }


    /**
     * Decode a stream of bytes to a foodMessage item
     * @return a FoodMessage that constructs that based on the MessageInput
     * @param in MessageInput object that wraps around a input stream
     * @throws FoodNetworkException if fails to construct the foodMessage item
     * @throws EOFException if the stream prematurally closes
     */
    public static FoodMessage decode(MessageInput in) throws FoodNetworkException, EOFException {
        String version =  in.getNextFixedBytes("FN1.0".length());
        if(!"FN1.0".equals(version)){
            throw new FoodNetworkException("Failed to read version number");
        }
        in.getNextSpace();
        long messageTimestamp = in.getNextUnsignedLong();
        in.getNextSpace();
        String type = in.getNextStringWithPattern("[A-Z]+");
        switch(type){
            case "ADD":
                in.getNextSpace();
                FoodItem foodItem = new FoodItem(in);
                in.getNextNewLine();
                return new AddFood(messageTimestamp, foodItem);
            case "GET":
                in.getNextSpace();
                in.getNextNewLine();
                return new GetFood(messageTimestamp);
            case "LIST":
                in.getNextSpace();
                long modifiedTimestamp = in.getNextUnsignedLong();
                in.getNextSpace();
                int count = in.getNextUnsignedInt();
                FoodList tempFoodList = new FoodList(messageTimestamp, modifiedTimestamp);
                for(int i = 0; i < count; i++){
                    tempFoodList.addFoodItem(new FoodItem(in));
                }
                in.getNextNewLine();
                return tempFoodList;
            case "ERROR":
                in.getNextSpace();
                String message = in.getNextString();
                return new ErrorMessage(messageTimestamp, message);
            default:
                return null;
        }
    }


    /**
     * Get the timestamp for when the message is created in a long
     * @return a long that represents timestamp
     */
    public final long getMessageTimestamp(){
        return timestamp;
    }

    /**
     * Set the message timestamp for when it is created
     * @param messageTimestamp message timestamp
     * @throws FoodNetworkException if the messageTimestamp is negative
     */
    public final void setMessageTimestamp(long messageTimestamp) throws FoodNetworkException{
        if(messageTimestamp < 0){
            throw new FoodNetworkException("negative messageTimestamp");
        }
        timestamp = messageTimestamp;
    }

    /**
     * Get a request message
     * @return a request message based on subclass
     */
    abstract public String getRequest();

    /**
     * Get a hash code for the object
     * @return a hash code
     */
    @Override
    public int hashCode(){
        return new Long(timestamp).hashCode() * 13;
    }

    /**
     * Presents the foodMessage in a human readable format
     * @return a String that represents the messageItem
     */
    @Override
    public String toString(){
        String temp = "";
        temp += "Version: FN1.0\n";
        temp += "MessageTime: " + timestamp + "\n";
        return temp;
    }

}
