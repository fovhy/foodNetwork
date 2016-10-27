/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 4
 * Class: CSI 4321
 *
 ************************************************/
package tefub.serialization;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Error message of TeFubMessage. It simply stores an ErrorMessage.
 */
public class Error extends TeFubMessage{
    private String errorMessage;
    /**
     * Construct an Error Message
     * @param msgId message ID
     * @param errorMessage error message
     * @throws IllegalArgumentException if validation fails
     */
    public Error(int msgId, String errorMessage) throws IllegalArgumentException{
        super(msgId);
        code = 3;
    }

    /**
     * Constructor that uses a DataInputStream
     * @param msgId message ID
     * @param in DataInputStream to construct the error message
     * @throws IOException if DataInputStream closes prematurely
     */
    public Error(int msgId, DataInputStream in) throws IOException {
        super(msgId);
        code = 3;
        byte temp;
        while((temp = in.readByte()) != -1){
            errorMessage += (char)temp;
        }
    }

    /**
     * Set the error message
     * @param errorMesage error message
     * @throws IllegalArgumentException if validation fails (e.g. null or non-ASCII)
     */
    public void setErrorMessage(String errorMesage)throws IllegalArgumentException{
        errorMessage = errorMesage;
    }

    /**
     * Get error message
     * @return error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Get the data of message
     * @return the data of the TeFub Message
     */
    public byte[] getData() throws UnsupportedEncodingException {
        return errorMessage.getBytes("ASCII");
    }

}
