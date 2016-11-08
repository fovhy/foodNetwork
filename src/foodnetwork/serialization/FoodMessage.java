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
 * encode and decode. The only abstract method is getFullRequest here.
 *
 */
public abstract class FoodMessage {
    /**
     * when the message is being created
     */
    protected long timestamp;
    protected static final String currentVersion = "FN1.0";
    /**
     * Encode a foodMessage subclass object
     * @param out MessageOutput object that wraps around a outputStream
     * @throws FoodNetworkException if fails to encode foodMessage
     */
    public void encode(MessageOutput out) throws FoodNetworkException {
        try {
            out.writeAndStore(currentVersion + " " + timestamp + " " + this.getFullRequest() + "\n");
        }catch (IOException e){
            throw new FoodNetworkException("OutputStream closes prematurely", e);
        }
    }


    /**
     * Decode a stream of bytes to a foodMessage item
     * @return a FoodMessage that constructs that based on the MessageInput
     * @param in MessageInput object that wraps around a input stream
     * @throws FoodNetworkException if fails to construct the foodMessage item
     * @throws EOFException if the stream prematurely closes
     */
    public static FoodMessage decode(MessageInput in) throws FoodNetworkException, EOFException {
        String version =  in.getNextFixedBytes(currentVersion.length());
        if(!currentVersion.equals(version)){
            throw new FoodNetworkException("Version: " +  version);
        }
        in.getNextSpace();
        long messageTimestamp = in.getNextUnsignedLong();
        String type = in.getNextStringWithPattern("[A-Z]+");
        switch(type){
            case "ADD":
                return new AddFood(messageTimestamp, in);
            case "GET":
                return new GetFood(messageTimestamp, in);
            case "LIST":
                return new FoodList(messageTimestamp, in);
            case "ERROR":
                return new ErrorMessage(messageTimestamp, in);
            case "INTERVAL":
                return new Interval(messageTimestamp, in);
            default:
                throw new FoodNetworkException("Unknown operation: " + type);
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
            throw new FoodNetworkException("negative messageTimestamp: " + messageTimestamp);
        }
        timestamp = messageTimestamp;
    }

    /**
     * Get a request message
     * @return a request message based on subclass
     */
    abstract public String getFullRequest();

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
        temp += "Version: +" + currentVersion + "\n";
        temp += "MessageTime: " + timestamp + "\n";
        return temp;
    }

}
