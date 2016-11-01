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
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * The helper that does all the stuff register and deregister message class do.
 */
public class AddressHelper {
    private int port;
    private Inet4Address adress;
    private final int MAX_PORT = 65535; // max range for unsigned short
    private final static int ADDRESS_SIZE = 4;
    private final static int PORT_SIZE = 2;

    /**
     * Construct an address helper with a DataInputStream
     * @param in the stream to be used
     * @throws IOException if the stream closes unexpectedly
     */
    public AddressHelper(DataInputStream in) throws IOException {
        byte[] rawAddressBytes = new byte[ADDRESS_SIZE];
        if(in.read(rawAddressBytes, 0, ADDRESS_SIZE) < ADDRESS_SIZE){
            throw new IOException("Too less of bytes");
        }

        byte[] rawPortBytes = new byte[PORT_SIZE];
        if(in.read(rawPortBytes, 0, PORT_SIZE) < PORT_SIZE){
            throw new IOException("Too less of bytes");
        }
        if(in.available() > 0){ // if there is extra byre throw
            throw new IOException("More bytes than expected");
        }

        EndianCoder.reverse(rawAddressBytes); // reverse bytes order from big endian to small endian
        //EndianCoder.reverse(rawPortBytes);

        setAddress((Inet4Address)Inet4Address.getByAddress(rawAddressBytes));
        setPort(EndianCoder.decodeShort(rawPortBytes, 0));
    }

    /**
     * Default constructor, simply make setAddress
     */
    public AddressHelper() {
    }

    /**
     * Set the Address in the helper
     * @param address address to be set
     * @throws IllegalArgumentException if the address is null or multicast address
     */
    public void setAddress(Inet4Address address) throws IllegalArgumentException{
        if(address == null){
            throw new IllegalArgumentException("Null address");
        }
        if(address.isMulticastAddress()){
            throw new IllegalArgumentException("Multicast address");
        }
        this.adress = address;
    }

    /**
     * Set the port
     * @param port the port to be set
     * @throws IllegalArgumentException if the port is out of range
     */
    public void setPort(int port) throws IllegalArgumentException{
        if(port >= 0 && port <= MAX_PORT){
            this.port = port;
        }else{
            throw new IllegalArgumentException("Port " + port + " out of range.");
        }
    }

    /**
     * Get the port
     * @return port
     */
    public int getPort(){
        return port;
    }

    /**
     * Get the IPv4 address
     * @return IPv4 address
     */
    public Inet4Address getAddress(){
        return adress;
    }

    /**
     * Get the data of message
     * @return the data of the TeFub Message
     * @throws IOException if the ByteBuffer fails to write out the data
     */
    public byte[] getData() throws IOException {
        int addressInInt = ByteBuffer.wrap(getAddress().getAddress()).getInt();

        ByteBuffer first = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE);
        first.order(ByteOrder.LITTLE_ENDIAN).putInt(addressInInt);

        ByteBuffer second = ByteBuffer.allocate(Short.SIZE / Byte.SIZE);
        second.order(ByteOrder.LITTLE_ENDIAN).putShort((short) getPort());

        return EndianCoder.concat(first.array(), second.array());
    }
}
