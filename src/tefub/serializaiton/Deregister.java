/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 4
 * Class: CSI 4321
 *
 ************************************************/
package tefub.serializaiton;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * The Deregister TeFubMessage. It stores the information of what to deregister.
 */
public class Deregister extends TeFubMessage {
    /**
     * Construct a Deregister message
     *
     * @param msgId   message ID
     * @param address address of deregister
     * @param port    port to deregister
     * @throws IllegalArgumentException if validation fails
     */
    public Deregister(int msgId,
                      InetAddress address,
                      int port)
            throws IllegalArgumentException {
        super(msgId);
    }

    /**
     * Get deregister address
     *
     * @return deregsiter address
     */
    public InetAddress getAddress() {
        return null;
    }

    /**
     * Set the address to deregister
     *
     * @param address the address to deregister
     * @throws IllegalArgumentException if validation fails (e.g. null, multicast)
     */
    public void setAddress(InetAddress address) throws IllegalArgumentException {

    }

    /**
     * Get deregister port
     *
     * @return deregister port
     */
    public int getPort() {
        return 0;
    }

    /**
     * Set the port to deregister
     *
     * @param port deregister port
     * @throws IllegalArgumentException if the port is out of range
     */
    public void setPort(int port) throws IllegalArgumentException {

    }

    /**
     * Get the socket address
     * @return socket address
     */
    public InetSocketAddress getSocketAddress(){
        return null;
    }
}

