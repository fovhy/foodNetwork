/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 1
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization;

/**
 * An abstract class that represents a FoodMessage. It provides a genral form for all the foodMessage subclass to
 * encode and decode. The only abstract method is getRequest here.
 *
 */
abstract public class FoodMessage {
    protected long timeStamp;

    /**
     * Encode a foodMessage subclass object
     * @param in MessageOutput object that wraps around a outputStream
     * @throws FoodNetworkException if fails to encode foodMessage
     */
    public void encode(MessageOutput out) throws FoodNetworkException{

    }


    /**
     * Decode a stream of bytes to a foodMessage item
     * @return a FoodMessage that constructs that based on the MessageInput
     * @param in MessageInput object that wraps around a input stream
     * @throws FoodNetworkException if fails to construct the foodMessage item
     */
    public static FoodMessage decode(MessageInput in)throws FoodNetworkException{
        return null;
    }


    /**
     * Get the timestamp for when the message is created in a long
     * @return a long that represents timestamp
     */
    public final long getMessageTimestamp(){
        return 0L;
    }

    /**
     * Set the message timestamp for when it is created
     * @param messageTimestamp message timestamp
     * @throws FoodNetworkException if the messageTimestamp is negative
     */
    public final void setMessageTimestamp(long messageTimestamp) throws FoodNetworkException{

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
        return 0;
    }

    /**
     * Presents the foodMessage in a human readable format
     * @return a String that represents the messageItem
     */
    @Override
    public String toString(){
        return null;
    }

}
