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

    public AddressHelper(DataInputStream in) throws IOException {
        byte[] rawAddressBytes = new byte[ADDRESS_SIZE];
        in.read(rawAddressBytes, 0, ADDRESS_SIZE);
        byte[] rawPortBytes = new byte[PORT_SIZE];
        in.read(rawPortBytes, 0, PORT_SIZE);
        EndianCoder.reverse(rawAddressBytes);
        EndianCoder.reverse(rawPortBytes);
        setAddress((Inet4Address)Inet4Address.getByAddress(rawAddressBytes));
        setPort(EndianCoder.decodeShort(rawPortBytes, 0));
    }

    public AddressHelper() {
    }

    public void setAddress(Inet4Address address) throws IllegalArgumentException{
        if(address == null){
            throw new IllegalArgumentException("Null address");
        }
        if(address.isMulticastAddress()){
            throw new IllegalArgumentException("Multicast address");
        }
        this.adress = address;
    }
    public void setPort(int port) throws IllegalArgumentException{
        if(port >= 0 && port <= MAX_PORT){
            this.port = port;
        }else{
            throw new IllegalArgumentException("Port " + port + " out of range.");
        }
    }
    public int getPort(){
        return port;
    }
    public Inet4Address getAdress(){
        return adress;
    }

    /**
     * Get the data of message
     * @return the data of the TeFub Message
     * @throws IOException if the ByteBuffer fails to write out the data
     */
    public byte[] getData() throws IOException {
        int addressInInt = ByteBuffer.wrap(getAdress().getAddress()).getInt();
        ByteBuffer first = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE);
        first.order(ByteOrder.LITTLE_ENDIAN).putInt(addressInInt);
        ByteBuffer second = ByteBuffer.allocate(Short.SIZE / Byte.SIZE);
        second.order(ByteOrder.LITTLE_ENDIAN).putShort((short) getPort());
        return EndianCoder.concat(first.array(), second.array());
    }
}
