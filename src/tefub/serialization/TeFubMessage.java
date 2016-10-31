/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 4
 * Class: CSI 4321
 *
 ************************************************/
package tefub.serialization;





import java.io.*;
import java.lang.*;
import java.util.Arrays;

/**
 * The abstract base class for TeFubMessage group. It has a message ID, and a code that represents for
 * different type of TeFubMessage.
 */
public abstract class TeFubMessage {
    protected int msgID;
    protected int code;
    protected final static int currentVersion = 3;
    private final static int HEADER_SIZE = 2;
    public static final int VERSION = 0x3000;
    private static final int VERSION_SHIFT = 12;
    public static final int VERSION_MASK =  0xF000;

    private static final int CODE_MASK = 0xF00;
    private static final int CODE_SHIFT = 8;

    private static final int ID_MASK = 0xFF;
    public static final int REGISTER = 0;
    public static final int ADDITION = 1;
    public static final int DEREGISTER = 2;
    public static final int ERROR = 3;
    public static final int ACK = 4;
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
        if(pkt.length < HEADER_SIZE){
            throw new IOException("Runt message");
        }
        InputStream bs = new ByteArrayInputStream(pkt);
        DataInputStream in = new DataInputStream(bs);
        int header = in.readShort();
        if((header & VERSION_MASK) != VERSION){
            throw new IllegalArgumentException("Bad Version number: " +
                    ((header & VERSION_MASK) >> VERSION_SHIFT));
        }
        int readCode = (header & CODE_MASK) >> CODE_SHIFT;
        int messageID = (header & ID_MASK);
        switch(readCode){
            case REGISTER:
                return new Register(messageID, in);
            case ADDITION:
                return new Addition(messageID, in);
            case DEREGISTER:
                return new Deregister(messageID, in);
            case ERROR:
                return new Error(messageID, in);
            case ACK:
                return new ACK(messageID, in);
            default:
                throw new IllegalArgumentException("Code: " + readCode+ " does not exist.");
        }
    }

    /**
     * Serialize message
     * @return serialized message bytes
     */
    public byte[] encode() {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(byteStream);
        byte versionAndCode = (byte) currentVersion;
        byte byteCode = (byte)getCode();
        versionAndCode = (byte) (versionAndCode << 4);
        versionAndCode |= byteCode;
        int count = 0;  // the offset for Bytearray
        try {
            out.writeByte(versionAndCode);
            count += 1;
            out.writeByte(getMsgId());
            count += 1;
        } catch (IOException e) {
            // will not happen
        }
        try {
            out.write(getData());
            count += getData().length;
        }catch(IOException e){
            // it will not happen
        }
        return Arrays.copyOfRange(byteStream.toByteArray(), 0, count);
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
        if(msgId < 0){
            throw new IllegalArgumentException("Negative message ID");
        }
        if(msgId > 255){
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
            return (msgID * 13) + (code * 113);
    }

    /**
     * Check if this TeFubMessage object equals another object
     * @param obj the other object
     * @return equals or not
     */
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }
        if(obj == this){
            return true;
        }
        if(!(obj instanceof TeFubMessage)){
            return false;
        }
        boolean results = false;
        TeFubMessage testObj = (TeFubMessage) obj;
        try {
            if(this.hashCode()== testObj.hashCode() &&
                    this.getCode() == testObj.getCode() &&
                    this.getMsgId() == testObj.getMsgId()&&
                    Arrays.equals(this.getData(), testObj.getData())){
                results = true;
            }
        } catch (IOException e) {
            // won't happen for the ASCII encoding
        }
        return results;
    }

    /**
     * Get the data of message
     * @return the data of the TeFub Message
     * @throws IOException if fails to output the data
     */
    public abstract byte[] getData() throws IOException;

}
