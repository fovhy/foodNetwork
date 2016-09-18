/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 1
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization;

/**
 * ErrorMessage is a class that stores all the information needed for a error message.
 */
public class ErrorMessage extends FoodMessage{

    /**
     * Constructor for ErrorMessage
     * @param timestamp when message is created
     * @param errorMessage what is the error
     */
    public ErrorMessage(long timestamp, String errorMessage)throws FoodNetworkException {
    }

    /**
     * Return the errorMessage stored in ErrorMessage class
     * @return error message
     */
    public final String getErrorMessage(){
        return null;
    }

    /**
     * Return the request of a Error message. ERROR + SP + ErrorMessage
     * @return request of error message.
     */
    public final String getRequest(){
        return null;
    }
    /**
     * Compare this object with another one to see if they are equal.
     * @param obj another object
     * @return equals or not
     */
    @Override
    public boolean equals(Object obj){
        return false;
    }

    /**
     * Hash code function for ErrorMessage
     * @return a hashcode
     */
    @Override
    public int hashCode(){
        return 0;
    }

    /**
     * Set errorMessage.
     * @param errorMessage errorMessage to be stored
     * @throws FoodNetworkException if the message is empty String or null
     */
    void setErrorMessage(String errorMessage) throws FoodNetworkException{

    }
}
