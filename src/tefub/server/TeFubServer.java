/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 6
 * Class: CSI 4321
 *
 ************************************************/
package tefub.server;

import tefub.serialization.TeFubMessage;

import java.io.IOException;
import java.net.*;

import static java.lang.Thread.sleep;

/**
 * For now it is just an echo server sort of
 */
public class TeFubServer extends Thread{
    private int portNumber;
    TeFubServer(int portNumber){
        this.portNumber = portNumber;
    }
    private void constructSocket(){

    }
    private void receiveAndResponse(){
        TeFubMessage receviedMessage = receiveData();
    }
    private void respond(TeFubMessage message){

    }

    /**
     * Send a certain TeFubMessage to the client
     * @param message
     */
    private void sendMessage(TeFubMessage message){

    }
    private TeFubMessage receiveData(){
        return null;
    }
    public void run(){
        constructSocket();
        receiveAndResponse();
    }
    private static DatagramSocket sock;
    public static void main(String[] args) throws IOException {

    }
}
