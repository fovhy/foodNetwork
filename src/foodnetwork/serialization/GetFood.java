/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 1
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization;

import java.io.EOFException;

/**
 * Message class for getting a food. Subclass of FoodMessage.
 */
public class GetFood extends FoodMessage{
    private final static String type = "GET";
    /**
     * A constructor should never be called outside of foodMessage's decode function.
     * @param messageTimestamp the time stamp already read in from MessageInput
     * @param in the messageInput to use
     * @throws FoodNetworkException if it fails to construct the class
     * @throws EOFException the stream ends prematurally
     */
    public GetFood(long messageTimestamp, MessageInput in) throws FoodNetworkException, EOFException {
        setMessageTimestamp(messageTimestamp);
        in.getNextNewLine();
    }
    /**
     * Constructor for GetFood. Takes a timestamp for when the message is created.
     * @param messageTimestamp time stamp when the message is created
     * @throws FoodNetworkException if the message is empty or null
     */
    public GetFood(long messageTimestamp) throws FoodNetworkException{
        setMessageTimestamp(messageTimestamp);
    }

    /**
     * Get the GetFood request message. waitServerRespond + SP
     * @return GetFood request message
     */
    public String getFullRequest() {
        return  type + " ";
    }

    /**
     * Presents the GetFood class in a human readable format
     * @return a String that represents the messageItem
     */
    @Override
    public String toString(){
        return super.toString() + "Type: " + type + "\n";
    }
    /**
     * Get what the type of message this is
     * @return request type
     */
    @Override
    public String getRequest(){
        return type;
    }
    /**
     * Check if this GetFood object equals to another object
     * @param obj another object
     * @return equal or not
     */

    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }
        if(obj == this){
            return true;
        }
        if(!(obj instanceof GetFood)){
            return false;
        }
        GetFood testObj = (GetFood) obj;
        if(this.hashCode()== testObj.hashCode() &&
                this.timestamp == testObj.timestamp) {
            return true;
        }else{
            return false;
        }
    }
}
