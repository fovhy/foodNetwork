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
 * Register message type for TeFubMessage. It contains information about what to register.
 */
public class Register extends TeFubMessage{
    private AddressHelper addressHelper;
    /**
     * @param msgId message ID
     * @param address address to register
     * @param port port to register
     * @throws IllegalArgumentException if validation fails
     * @throws IOException if the dataInputStream fails to read
     */
    public Register(int msgId, Inet4Address address, int port) throws IllegalArgumentException, IOException {
        super(msgId);
        code = 0;
        addressHelper = new AddressHelper();
        setAddress(address);
        setPort(port);
    }
    public Register(int msgId, DataInputStream in) throws IOException {
        super(msgId);
        code = 0;
        addressHelper = new AddressHelper(in);
    }

    /**
     * Get the address to register
     * @return address to register
     */
    public Inet4Address getAddress(){
        return addressHelper.getAddress();
    }

    /**
     * Set register address
     * @param address register address
     * @throws IllegalArgumentException if validation fails (e.g. null, multicast)
     */
    public void setAddress(Inet4Address address) throws IllegalArgumentException{
        addressHelper.setAddress(address);
    }

    /**
     * Get port to register
     * @return port to register
     */
    public int getPort(){
        return addressHelper.getPort();
    }

    /**
     * Set port to register
     * @param port port to register
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
