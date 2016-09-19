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

    private String errorMessage;
    /**
     * Constructor for ErrorMessage
     * @param timestamp when message is created
     * @param errorMessage what is the error
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
    public final String getRequest(){
        return "ERROR " + errorMessage;
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
    void setErrorMessage(String errorMessage) throws FoodNetworkException{
        if(errorMessage == null || "".equals(errorMessage)){
            throw new FoodNetworkException("Invalid error message");
        }
        this.errorMessage = errorMessage;
    }
}
