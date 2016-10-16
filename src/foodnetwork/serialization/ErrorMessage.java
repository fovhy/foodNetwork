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
 * ErrorMessage is a class that stores all the information needed for a error message.
 */
public class ErrorMessage extends FoodMessage{


    private final static String type = "ERROR";
    private String errorMessage; // the error messages stored inside of ErrorMessage class
    /**
     * A constructor should never be called outside of foodMessage's decode function.
     * @param messageTimestamp the time stamp already read in from MessageInput
     * @param in the messageInput to use
     * @throws FoodNetworkException if it fails to construct the class
     * @throws EOFException the stream ends prematurally
     */
    public ErrorMessage(long messageTimestamp, MessageInput in) throws FoodNetworkException, EOFException {
        setMessageTimestamp(messageTimestamp);
        int messageLength = in.getNextUnsignedInt();
        String message = in.getNextFixedBytes(messageLength);
        this.setErrorMessage(message);
        in.getNextNewLine();
    }
    /**
     * Constructor for ErrorMessage
     * @param timestamp when message is created
     * @param errorMessage what is the error
     * @throws FoodNetworkException if the message is negative
     */
    public ErrorMessage(long timestamp, String errorMessage)throws FoodNetworkException {
        setMessageTimestamp(timestamp);
        setErrorMessage(errorMessage);
    }

    /**
     * Return the errorMessage stored in ErrorMessage class
     * @return error message
     */
    public final String getErrorMessage(){
        return errorMessage;
    }

    /**
     * Return the request of a Error message. ERROR + SP + ErrorMessage
     * @return request of error message.
     */
    public final String getFullRequest(){
        return type + " " + errorMessage.length() + " " + errorMessage;
    }

    /**
     * Get what the type of message this is
     * @return request type
     */
    @Override
    public final String getRequest(){
        return type;
    }
    /**
     * Compare this object with another one to see if they are equal.
     * @param obj another object
     * @return equals or not
     */
    @Override
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }
        if(obj == this){
            return true;
        }
        if(!(obj instanceof ErrorMessage)){
            return false;
        }
        boolean results = false;
        ErrorMessage testObj = (ErrorMessage) obj;
        if(this.hashCode()== testObj.hashCode() &&
                this.timestamp == testObj.timestamp &&
                this.errorMessage.equals(testObj.errorMessage)){
            results = true;
        }
        return results;
    }

    /**
     * Hash code function for ErrorMessage
     * @return a hashcode
     */
    @Override
    public int hashCode(){
        return super.hashCode() + errorMessage.hashCode() * 7;
    }

    /**
     * Set errorMessage.
     * @param errorMessage errorMessage to be stored
     * @throws FoodNetworkException if the message is empty String or null
     */
    public void setErrorMessage(String errorMessage) throws FoodNetworkException{
        if(errorMessage == null || "".equals(errorMessage)){
            throw new FoodNetworkException("Invalid error message");
        }
        this.errorMessage = errorMessage;
    }

    /**
     * Turn ErrorMessage into a human readable format
     * @return the human readable string
     */
    public String toString(){
        String temp = super.toString();
        temp   = temp + "Type: " + type + "\n";
        temp  = temp +  "Error Message: " + errorMessage + "\n";
        return temp;
    }

}
