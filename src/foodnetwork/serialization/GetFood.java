/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 1
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization;

/**
 * Message class for getting a food. Subclass of FoodMessage.
 */
public class GetFood extends FoodMessage{
    /**
     * Constructor for GetFood. Takes a timestamp for when the message is created.
     * @param messageTimestamp time stamp when the message is created
     * @throws FoodNetworkException if the message is empty or null
     */
    public GetFood(long messageTimestamp) throws FoodNetworkException{

    }

    /**
     * Get the GetFood request message. GET + SP
     * @return GetFood request message
     */
    public String getRequest() {
        return null;
    }

    /**
     * Presents the GetFood class in a human readable format
     * @return a String that represents the messageItem
     */
    @Override
    public String toString(){
        return null;
    }
}
