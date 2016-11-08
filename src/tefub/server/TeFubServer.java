/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 6
 * Class: CSI 4321
 *
 ************************************************/
package tefub.server;

import foodnetwork.serialization.AddFood;
import foodnetwork.serialization.FoodItem;
import tefub.client.TeFubClient;
import tefub.serialization.*;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * For now it is just an echo server sort of
 */
public class TeFubServer extends Thread implements Observer {
    private int portNumber; // the port number for UDP socket, should be the same as the foodNetwork one
    private DatagramSocket sock; // the server data socket
    private Set<SocketAddress> addressesSet; // the set of sockets it connects to, no duplicate
    public static final int MAX_PORT_NUMBER = 65535;
    private Logger logger;

    /**
     * Construct a teFub Server with a port
     * @param portNumber the port number for the teFub server
     * @param logger a logger this server will be using
     */
    public TeFubServer(int portNumber, Logger logger){
        this.portNumber = portNumber;
        addressesSet = new HashSet<>();
        this.logger = logger;
    }

    /**
     * The setup of the connection of the UDP socket
     * @throws SocketException if it fails connect in the start up
     */
    private void constructSocket() throws SocketException {
        checkPort(this.portNumber);
        sock = new DatagramSocket(portNumber);
    }

    /**
     * Check if the port is out of range
     * @param portNumber the port number you wish to check
     */
    private void checkPort(int portNumber) throws IllegalArgumentException{
        if(portNumber < 0 || portNumber >  MAX_PORT_NUMBER){
            throw new IllegalArgumentException("Invalid Port Range");
        }
    }

    /**
     * The process of waiting to receive, and respond to the recevied message
     */
    private void receiveAndRespond(){
        TeFubMessage receviedMessage = receiveData();
    }

    /**
     * Respond based on the TefubMessage
     * @param message
     */
    private void respond(TeFubMessage message){
        // if there is parse error or unknown code, do nothing
        if(message == null){
            return;
        }
        switch (message.getCode()){
            case TeFubMessage.REGISTER:
                register((Register)message);
                break;
            case TeFubMessage.DEREGISTER:
                deRegister((Deregister)message);
                break;
            default:                          // any others type of TeFubMessage
                sendErrorMessage("Unexpected Message type " + message.getCode(), message.getMsgId());
                break;
        }
    }

    /**
     * Register process
     * @param message the register message server received
     */
    private void register(Register message){
        if(!sock.getRemoteSocketAddress().equals(message.getSocketAddress())){
            sendErrorMessage("Incorrect address or port", message.getMsgId());
            return;
        }
        if(!addressesSet.add(message.getSocketAddress())){
            sendErrorMessage("Already registered", message.getMsgId());
            return;
        }
        // correct case
        sendACK(message.getMsgId());
    }

    /**
     * Send ACK message with a specific messageID
     * @param messageID message ID you want to set
     */
    void sendACK(int messageID){
        int ackSize = 16;
        byte buffer[] = new byte[ackSize * 2]; // just making sure it is big enough
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        packet.setData(new ACK(messageID).encode());
        try {
            sock.send(packet);
        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    /**
     * Deregister process
     * @param message the deregister message received
     */
    private void deRegister(Deregister message){
        // if it does not contain the element
        if(!addressesSet.remove(message.getSocketAddress())){
            sendErrorMessage("Unknown client", message.getMsgId());
            return;
        }
        // correct case
        sendACK(message.getMsgId());
    }
    /**
     * Send a error message to the client
     * @param message the error message
     * @param msgID messageID you want to set
     */
    private void sendErrorMessage(String message, int msgID){
        byte[] buffer = new byte[TeFubClient.MAX_MESSAGE_SIZE];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        packet.setData(new tefub.serialization.Error(msgID, message).encode());
        try {
            sock.send(packet);
        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    /**
     * Receive data, and transfer the data into TeFubMessage if something bad happen,
     * it throws. (i.e. parsing exception, wrong code, unexpected code)
     * @return the message you want to return
     */
    private TeFubMessage receiveData(){
        byte[] buffer = new byte[TeFubClient.MAX_MESSAGE_SIZE];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        try {
            sock.receive(packet);
        }catch(IOException e){
            logger.log(Level.WARNING, e.getMessage());
            return null;
        }
        byte[] encodeMessage = Arrays.copyOfRange(packet.getData(), 0, packet.getLength());
        TeFubMessage receivedMessage = null;
        try {
            receivedMessage = TeFubMessage.decode(encodeMessage);
        }catch(IOException e){
            sendErrorMessage("Unable to parse message", 0);
            return null;
        }catch(IllegalArgumentException e){
            if(e.getMessage().startsWith("Unknown")){
                sendErrorMessage(e.getMessage(), 0);
            }else{
                sendErrorMessage("Unable to parse message", 0);
            }
            return null;
        }
        return receivedMessage;
    }

    /**
     *The method it inherits from thread. Basically the main for the class
     */
    public void run(){
        try {
            constructSocket();
        } catch (SocketException e) {
            logger.log(Level.SEVERE, e.getMessage());
            System.exit(1);
        }
        receiveAndRespond();
    }

    /**
     * Receive update from the FoodNetwork message protocol.
     * Here it just sends an addition message to all the addresses registered
     * @param o the protocol object this server is observing
     * @param arg the AddFood Message
     */
    @Override
    public void update(Observable o, Object arg) {
        FoodItem foodToNotice = ((AddFood)arg).getFoodItem();
        byte[] buffer = new byte[TeFubClient.MAX_MESSAGE_SIZE];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        Addition add = new Addition(0, foodToNotice.getName(), foodToNotice.getMealType(),
                foodToNotice.getCalories());
        for(SocketAddress address : addressesSet){
            add.setMsgId(TeFubClient.genRandomMessageID());
            packet.setData(add.encode());
            try {
                sock.connect(address);
            } catch (SocketException e) {
                logger.log(Level.WARNING, e.getMessage());
            }
            try {
                sock.send(packet);
            } catch (IOException e) {
                logger.log(Level.WARNING, e.getMessage());
            }
        }
    }
}
