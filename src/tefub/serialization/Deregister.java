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
import java.net.Inet4Address;
import java.net.InetSocketAddress;

/**
 * The Deregister TeFubMessage. It stores the information of what to deregister.
 */
public class Deregister extends TeFubMessage {
   private AddressHelper addressHelper;
    /**
     * @param msgId message ID
     * @param address address to register
     * @param port port to register
     * @throws IllegalArgumentException if validation fails
     * @throws IOException if the dataInputStream fails to read
     */
    public Deregister(int msgId, Inet4Address address, int port) throws IllegalArgumentException, IOException {
        super(msgId);
        code = DEREGISTER;
        addressHelper = new AddressHelper();
        setAddress(address);
        setPort(port);
    }
    public Deregister(int msgId, DataInputStream in) throws IOException {
        super(msgId);
        code = DEREGISTER;
        addressHelper = new AddressHelper(in);
    }

    /**
     * Get the address to Deregister
     * @return address to Deregister
     */
    public Inet4Address getAddress(){
        return addressHelper.getAddress();
    }

    /**
     * Set Deregister address
     * @param address Deregister address
     * @throws IllegalArgumentException if validation fails (e.g. null, multicast)
     */
    public void setAddress(Inet4Address address) throws IllegalArgumentException{
        addressHelper.setAddress(address);
    }

    /**
     * Get port to Deregister
     * @return port to Deregister
     */
    public int getPort(){
        return addressHelper.getPort();
    }

    /**
     * Set port to Deregister
     * @param port port to Deregister
     * @throws IllegalArgumentException if port is out of range
     */
    public void setPort(int port) throws IllegalArgumentException{
        addressHelper.setPort(port);
    }

    /**
     * Get the socket address
     * @return socket address
     */
    public InetSocketAddress getSocketAddress(){
        return new InetSocketAddress(getAddress(), getPort());
    }
    /**
     * Get the data of message
     * @return the data of the TeFub Message
     */
    @Override
    public byte[] getData() throws IOException {
       return addressHelper.getData();
    }


}

