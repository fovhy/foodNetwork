/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 4
 * Class: CSI 4321
 *
 ************************************************/
package tefub.serializaiton;

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
    }
}
