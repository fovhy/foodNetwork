/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 4
 * Class: CSI 4321
 *
 ************************************************/
package tefub.serializaiton;

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

    /**
     * Return the ACK in a human readable form
     * @return human readable String
     */
    @Override
    public String toString(){
        return null;
    }

    /**
     * Get Code of ACK
     * @return message Code
     */
    public int getCode(){
        return 0;
    }

}
