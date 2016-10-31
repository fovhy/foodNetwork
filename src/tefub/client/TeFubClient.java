/************************************************
 *
 * Author: Nicholas Hopper
 * Assignment: Program 4
 * Class: CSI 4321
 *
 ************************************************/
package tefub.client;

import tefub.serialization.ACK;
import tefub.serialization.Addition;
import tefub.serialization.Register;
import tefub.serialization.TeFubMessage;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * The simple TeFubMessage UDP client. It simply serve as a reminder
 */
public class TeFubClient {
    private static DatagramSocket sock;
    private static InetAddress destAddress;
    private static int desPort;
    private static boolean quit = false;
    private static InetAddress localAddress;
    private static int localPort;


    private static int MAX_MSGID = 255;
    public static int genRandomMessageID(){
        return new Random().nextInt() % MAX_MSGID;
    }

    private static void sendMessage(byte[] data) throws IOException {
   }

    /**
     * Arbitrary UDP packet size from reading the server protocol. It should be big enough.
     */
    private static final int MAX_MESSAGE_SIZE = 4098;
    private static final int TIMER = 3000; // 3s timeout

    /**
     * The implementation of the two way handshake of the UDP packet, sort of..
     * @param data the data you want to send out
     * @param terminateMessage termination message when it fails to shake the hand
     * @return the data received from the server
     * @throws IOException if the data communication channel closes unexpectedly
     */
    private static byte[] handShake(byte[] data, String terminateMessage) throws IOException {
        int failCounter = 0;
        DatagramPacket messageSent = new DatagramPacket(data, data.length);
        sock.send(messageSent);

        DatagramPacket messageReceived = new DatagramPacket(new byte[MAX_MESSAGE_SIZE], MAX_MESSAGE_SIZE);
        sock.setSoTimeout(TIMER);
        while(true){
            try{
                sock.receive(messageReceived);
                return Arrays.copyOfRange(messageReceived.getData(), 0, messageReceived.getLength());
            }catch(SocketTimeoutException e) {
                failCounter++;
                switch (failCounter) {
                    case 1:
                        sock.send(messageSent); // resend message
                        break;
                    case 2:
                        terminate(terminateMessage);
                        return null;         // time out twice leave the program
                }
            }
        }
    }

    /**
     * Deduct a teFubMessage from given bytes
     * @param data given bytes
     * @return a teFubFoodMessage
     * @throws IOException if the data is too long or too short
     * @throws IllegalArgumentException invalid field
     */
    public static TeFubMessage getMessage(byte[] data) throws IOException , IllegalArgumentException{
        return TeFubMessage.decode(data);
    }
    public static TeFubMessage getMessage(DatagramPacket message) throws IOException , IllegalArgumentException{
        if(message != null) {
            return getMessage(Arrays.copyOfRange(message.getData(), 0, message.getLength()));
        }else{
            throw new IllegalArgumentException("Null data packet");
        }
    }

    /**
     *
     */
    //TODO: implement shutdown
    public static void shutDown(){

    }

    /**
     * Start up the connection between client and server
     * @throws IOException if the communication channel closes unexpectedly
     */
    public static void startup() throws IOException {
        int MsgId = genRandomMessageID();
        Register registerMessage = new Register(MsgId, (Inet4Address) localAddress, localPort);
        byte[] data = registerMessage.encode();
        byte[] receivedData = handShake(data, "Unable to register");
        TeFubMessage receivedMessage;
        //TODO: here it might throw an expcetion
        receivedMessage = getMessage(receivedData);
        processACKTeFubMessage(receivedMessage, MsgId);
        //TODO: might want loop back to processACKTeFubMessage again
    }

    /**
     * Process the teFubMessage normally, does not care about ACK
     * @param message the message you want to process
     */
    public static void processTeFubMessage(TeFubMessage message) {
        int code = message.getCode();
        switch(code){
            case TeFubMessage.ADDITION:                              // addition and error
                System.out.println("addition");
                System.out.println(message);
                break;
            case TeFubMessage.ERROR:                             // addition and error
                System.out.println(message);
                break;
            default:
                System.err.println("Unexpected message type");
        }
    }

    /**
     * Process the ACK message in the startup and shutdown process
     * @param message the message you want to check
     * @param expectedID the expected messageID for the ACK message
     */
    public static void processACKTeFubMessage(TeFubMessage message, int expectedID){
        if(message.getCode() != TeFubMessage.ACK){
            processTeFubMessage(message);
        }else{
            if(message.getMsgId() != expectedID){
                System.err.println("Unexpected MSG ID");
                //TODO: do something
            }
        }
    }

    /**
     * Terminate the connection, no hand shake
     * @param terminateMessage the message you want to print out
     */
    public static void terminate(String terminateMessage){
        sock.close();
        quit = true;
        if(terminateMessage.length() > 0){
            System.err.println(terminateMessage);
        }
    }

    /**
     * THe main method for the client
     * @param args destination address, local IP, local port
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        if(args.length != 2){
            throw new IllegalArgumentException(
                    "Parameters(s) : <Destination> " +
                    "<Client local IP>" +
                    "<Port>"
            );
        }
        destAddress = InetAddress.getByName(args[0]);
        desPort = Integer.parseInt(args[1]);
        sock = new DatagramSocket();  // construct UDP socket
        sock.connect(destAddress, desPort);

        localAddress = InetAddress.getByName(args[2]);
        localPort = sock.getLocalPort();
        new InputWatcher().run();
        startup();
        while(!quit){
            DatagramPacket messageReceived = new DatagramPacket(new byte[MAX_MESSAGE_SIZE], MAX_MESSAGE_SIZE);
            sock.receive(messageReceived);
            processTeFubMessage(getMessage(messageReceived));
        }
        shutDown();
    }
    public static class InputWatcher extends Thread{
        public void run(){
            Scanner scanner = new Scanner(System.in) ;
            String temp;
            while(!TeFubClient.quit){
                temp = scanner.nextLine();
                if("quit".equals(temp)){
                    TeFubClient.quit = true;
                }else{
                    System.out.println("Enter quit to end the connection");
                }
            }
        }
    }
}
