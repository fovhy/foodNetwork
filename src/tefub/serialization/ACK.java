/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 4
 * Class: CSI 4321
 *
 ************************************************/
package tefub.serialization;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

/**
 * The acknowledge message of TeFubMessage
 */
public class ACK extends TeFubMessage {
    /**
     * Construct an ACK message given a TeFubMessage ID
     *
     * @param msgID the message ID to set
     * @param in used to check if it is the end of the stream
     * @throws IllegalArgumentException if messageID is out of range
     */
    public ACK(int msgID, DataInputStream in) throws IllegalArgumentException, IOException {
        super(msgID);
        code = ACK;
        try {
            in.readByte();
            throw new IOException("More bytes than expected");
        }catch (EOFException e){
            // expected
        }
    }

    /**
     * The normal constructor for ack message
     * @param msgID the message ID
     */
    public ACK(int msgID){
        super(msgID);
        code = 4;
    }
    /**
     * Retur the the data part of ACK message
     * @return null, because there is no data field in ACK
     */
    @Override
    public byte[] getData() {
        return "".getBytes();
    }
}
