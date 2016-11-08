/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 6
 * Class: CSI 4321
 *
 ************************************************/
package tefub.server;

import tefub.serialization.TeFubMessage;

import java.net.*;

import static java.lang.Thread.sleep;

/**
 * For now it is just an echo server sort of
 */
public class TeFubServer extends Thread{
    private int portNumber; // the port number for UDP socket, should be the same as the foodNetwork one
    private static DatagramSocket sock; // the server data socket

    /**
     * Construct a teFub Server with a port
     * @param portNumber
     */
    TeFubServer(int portNumber){
        this.portNumber = portNumber;
    }

    /**
     * The setup of the connection of the UDP socket
     */
    private void constructSocket(){
        try {
            checkPort(this.portNumber);
        }catch(IllegalArgumentException e){
            //TODO: figure out whether to send a message or not
            // not even sure this is needed, it could be handled by the socket exception
        }
        try {
            sock = new DatagramSocket(portNumber);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if the port is out of range
     * @param portNumber the port number you wish to check
     */
    private void checkPort(int portNumber) throws IllegalArgumentException{
    }

    /**
     * The process of waiting to receive, and respond to the recevied message
     */
    private void receiveAndRespond(){
        TeFubMessage receviedMessage = receiveData();
    }
    private void respond(TeFubMessage message){

    }

    /**
     * Send a certain TeFubMessage to the client
     * @param message the message you want to send out to all the clients registered
     */
    private void sendMessage(TeFubMessage message){

    }

    /**
     * Receive data, and transfer the data into TeFubMessage if something bad happen,
     * it throws. (i.e. parsing exception, wrong code, unexpected code)
     * @return the message you want to return
     */
    private TeFubMessage receiveData(){
        return null;
    }

    /**
     *The method it inherits from thread. Basically the main for the class
     */
    public void run(){
        constructSocket();
        receiveAndRespond();
    }
}
