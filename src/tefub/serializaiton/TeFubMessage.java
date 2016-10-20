/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 4
 * Class: CSI 4321
 *
 ************************************************/
package tefub.serializaiton;


import java.io.IOException;
import java.util.IllegalFormatCodePointException;

/**
 * The abstract base class for TeFubMessage group. It has a message ID, and a code that represents for
 * different type of TeFubMessage.
 */
public abstract class TeFubMessage {
    protected int msgID;
    protected int code;
    protected final int currentVersion = 3;
    /**
     * Construct a TeFubMessage given a TeFubMessage ID
     * @param msgID the message ID to set
     * @throws IllegalArgumentException if messageID is out of range
     */
    public TeFubMessage(int msgID) throws IllegalArgumentException{
        setMsgId(msgID);
    }

    /**
     * @param pkt The code array to decode from
     * @return a TeFubMessage
     * @throws IllegalArgumentException if bad version, code, or attribute setter failure
     * @throws IOException if I/O problem including packet too long/short (EOFException)
     */
    public static TeFubMessage decode (byte[] pkt) throws IllegalArgumentException, IOException{
        return null;
    }

    /**
     * Serialize message
     * @return serialized message bytes
     */
    public byte[] encode(){
        return null;
    }

    /**
     * Return the TeFubMessage in a human readable form
     * @return human readable String
     */
    @Override
    public String toString(){
        String temp = "";
        temp = temp + "Current Version: " + currentVersion + "\n";
        temp = temp + "Code: " + code + "\n";
        temp = temp + "Message ID: " + msgID + "\n";
        return temp;
    }

    /**
     * Set the Msg ID
     * @param msgId message ID
     * @throws IllegalArgumentException if the message ID is out of range
     */
    public void setMsgId(int msgId) throws IllegalArgumentException{
        if(msgID < 0){
            throw new IllegalArgumentException("Negative message ID");
        }
        if(msgID > 255){
            throw new IllegalArgumentException("Message ID greater than 255 cannot be contained in 8 bit");
        }
        this.msgID = msgId;
    }

    /**
     * Get message ID
     * @return message ID
     */
    public int getMsgId(){
        return msgID;
    }

    /**
     * Get Code of TeFubMessage, each code represents a type of TeFubMessage
     * @return message Code
     */
    public int getCode(){
        return code;
    }

    /**
     * Hash function for TeFubMessage
     * @return a hashCode
     */
    @Override
    public int hashCode(){
        return 0;
    }

    /**
     * Check if this TeFubMessage object equals another object
     * @param obj the other object
     * @return equals or not
     */
    public boolean equals(Object obj){
        return false;
    }

    /**
     * Get the data of message
     * @return the data of the TeFub Message
     */
    public abstract byte[] getData();

}
