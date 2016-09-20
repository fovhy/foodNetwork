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
    /**
     * A constructor should never be called outside of foodMessage's decode function.
     * @param messageTimestamp the time stamp already read in from MessageInput
     * @param in the messageInput to use
     * @throws FoodNetworkException if it fails to construct the class
     * @throws EOFException the stream ends prematurally
     */
    public GetFood(long messageTimestamp, MessageInput in) throws FoodNetworkException, EOFException {
        setMessageTimestamp(messageTimestamp);
        in.getNextSpace();
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
     * Get the GetFood request message. GET + SP
     * @return GetFood request message
     */
    public String getRequest() {
        return "GET ";
    }

    /**
     * Presents the GetFood class in a human readable format
     * @return a String that represents the messageItem
     */
    @Override
    public String toString(){
        return super.toString() + "Type: GET\n";
    }
}
