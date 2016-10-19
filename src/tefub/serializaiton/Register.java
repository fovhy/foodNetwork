/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 4
 * Class: CSI 4321
 *
 ************************************************/
package tefub.serializaiton;


import java.net.Inet4Address;
import java.net.InetSocketAddress;

/**
 * Register message type for TeFubMessage. It contains information about what to register.
 */
public class Register extends TeFubMessage{
    /**
     * @param msgId message ID
     * @param address address to register
     * @param port port to register
     * @throws IllegalArgumentException if validation fails
     */
    public Register(int msgId, Inet4Address address, int port) throws IllegalArgumentException{
        super(msgId);
    }

    /**
     * Get the address to register
     * @return address to register
     */
    public Inet4Address getAddress(){
        return null;
    }

    /**
     * Set register address
     * @param address register address
     * @throws IllegalArgumentException if validation fails (e.g. null, multicast)
     */
    public void setAddres(Inet4Address address) throws IllegalArgumentException{

    }

    /**
     * Get port to register
     * @return port to register
     */
    public int getPort(){
        return 0;
    }

    /**
     * Set port to register
     * @param port port to register
     * @throws IllegalArgumentException if port is out of range
     */
    public void setPort(int port) throws IllegalArgumentException{

    }

    /**
     * Get the socket address
     * @return socket address
     */
    public InetSocketAddress getSocketAddress(){
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
