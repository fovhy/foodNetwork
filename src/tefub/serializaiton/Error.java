/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 4
 * Class: CSI 4321
 *
 ************************************************/
package tefub.serializaiton;

/**
 * Error message of TeFubMessage. It simply stores an ErrorMessage.
 */
public class Error extends TeFubMessage{
    /**
     * Construct an Error Message
     * @param msgId message ID
     * @param errorMessage error message
     * @throws IllegalArgumentException if validation fails
     */
    public Error(int msgId, String errorMessage) throws IllegalArgumentException{
        super(msgId);
    }

    /**
     * Set the error message
     * @param errorMesage error message
     * @throws IllegalArgumentException if validation fails (e.g. null or non-ASCII)
     */
    public void setErrorMessage(String errorMesage)throws IllegalArgumentException{

    }

    /**
     * Get error message
     * @return error message
     */
    public String getErrorMessage() {
        return null;
    }
    /**
     * Get the data of message
     * @return the data of the TeFub Message
     */
    public byte[] getData(){
        return null;
    }

}
