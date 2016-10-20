/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 4
 * Class: CSI 4321
 *
 ************************************************/
package tefub.serialization;

/**
 * The acknowledge message of TeFubMessage
 */
public class ACK extends TeFubMessage {
    /**
     * Construct an ACK message given a TeFubMessage ID
     *
     * @param msgID the message ID to set
     * @throws IllegalArgumentException if messageID is out of range
     */
    public ACK(int msgID) throws IllegalArgumentException {
        super(msgID);
        code = 4;
    }

    /**
     * Retur the the data part of ACK message
     * @return null, because there is no data field in ACK
     */
    @Override
    public byte[] getData() {
        return null;
    }
}
