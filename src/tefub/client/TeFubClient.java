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
    public static void shutDown(){

    }
    public static void startup() throws IOException {
        int MsgId = genRandomMessageID();
        Register registerMessage = new Register(MsgId, (Inet4Address) localAddress, localPort);
        byte[] data = registerMessage.encode();
        byte[] receivedData = handShake(data, "Unable to register");
        TeFubMessage receivedMessage = getMessage(receivedData);
        receivedMessage = getMessage(receivedData);
        processACKTeFubMessage(receivedMessage, MsgId);
        //TODO: might want loop back to processACKTeFubMessage again
    }
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
    public static void terminate(String terminateMessage){
        sock.close();
        quit = true;
        if(terminateMessage.length() > 0){
            System.err.println(terminateMessage);
        }
    }
    public static void main(String[] args) throws IOException {
        if(args.length != 2){
            throw new IllegalArgumentException("Parameters(s) : <Destination> + <Port>" +
                    " + <Client local IP>");
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
