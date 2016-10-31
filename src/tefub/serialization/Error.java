/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 4
 * Class: CSI 4321
 *
 ************************************************/
package tefub.serialization;


import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Error message of TeFubMessage. It simply stores an ErrorMessage.
 */
public class Error extends TeFubMessage {
    private String errorMessage;

    /**
     * Construct an Error Message
     *
     * @param msgId        message ID
     * @param errorMessage error message
     * @throws IllegalArgumentException if validation fails
     */
    public Error(int msgId, String errorMessage) throws IllegalArgumentException {
        super(msgId);
        code = 3;
        setErrorMessage(errorMessage);
    }

    /**
     * Constructor that uses a DataInputStream
     *
     * @param msgId message ID
     * @param in    DataInputStream to construct the error message
     * @throws IOException if DataInputStream closes prematurely
     */
    public Error(int msgId, DataInputStream in) throws IOException {
        super(msgId);
        code = 3;
        byte temp;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int count = 0;
        while (in.available() > 0) {
            count++;
            temp = in.readByte();
            out.write(temp);
        }
        /*get rid of the rest of Strings, /u0000's */
        errorMessage = new String(out.toByteArray(), "ASCII").substring(0, count);
    }

    /**
     * Set the error message
     *
     * @param errorMesage error message
     * @throws IllegalArgumentException if validation fails (e.g. null or non-ASCII)
     */
    public void setErrorMessage(String errorMesage) throws IllegalArgumentException {
        if (errorMesage != null) {
            if(EndianCoder.checkForAscii(errorMesage)) {
                this.errorMessage = errorMesage;
            }else{
                throw new IllegalArgumentException("Not in ascii");
            }
        } else {
            throw new IllegalArgumentException("Null error message");
        }
    }

    /**
     * Get error message
     *
     * @return error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Get the data of message
     *
     * @return the data of the TeFub Message
     */
    @Override
    public byte[] getData() throws UnsupportedEncodingException {
        if (errorMessage == null) {
            return "".getBytes("ASCII");
        }
        else {
            return errorMessage.getBytes("ASCII");
        }
    }
}
