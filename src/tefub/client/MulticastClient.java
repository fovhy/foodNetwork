/************************************************
 *
 * Author: Dean He
 * Assignment: Program 8
 * Class: CSI 4321
 *
 ************************************************/
package tefub.client;

import tefub.serialization.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

/**
 * The TeFubMessage UDP multicast client.
 */
public class MulticastClient {
    private static MulticastSocket sock;
    private static InetAddress destAddress;
    private static int desPort;
    private static boolean quit = false;
    /**
     * Absolute biggest datagram size could be without minus the header
     */
    private static final int MAX_MESSAGE_SIZE = 1024;
    private static final int TIMER = 1000; // 1s timeout


    /**
     * Deduct a teFubMessage from given bytes
     * @param data given bytes
     * @return a teFubFoodMessage
     * @throws IOException              if the data is too long or too short
     * @throws IllegalArgumentException invalid field
     */
    public static TeFubMessage getMessage(byte[] data) throws IOException, IllegalArgumentException {
        return TeFubMessage.decode(data);
    }

    /**
     * Get a TeFub Message from a datagram packet
     * @param message datagram packet
     * @return decoded TeFubMessage
     * @throws UnsupportedEncodingException wrong encoding
     */
    public static TeFubMessage getMessage(DatagramPacket message) throws UnsupportedEncodingException {
        if (message != null) {
            try {
                return getMessage(Arrays.copyOfRange(message.getData(), 0, message.getLength()));
            } catch (IOException | IllegalArgumentException e) {
                System.err.println("Unable to parse message: " +
                        new String(Arrays.copyOfRange(message.getData(), 0, message.getLength()), "ASCII"));
                return null;
            }
        } else {
            throw new IllegalArgumentException("Null data packet");
        }
    }
    /**
     * Shut down the client. Will conduct handshake in the process
     * @throws IOException if the socket closes unexpectedly
     */
    public static void shutDown() throws IOException {
        quit = true;
        sock.leaveGroup(destAddress);
        sock.close();
    }


    /**
     * Start up the connection between client and server
     * @throws IOException if the communication channel closes unexpectedly
     */
    public static void startup() throws IOException {
        sock.joinGroup(destAddress);
        sock.setSoTimeout(TIMER);
    }




    /**
     * Process the teFubMessage normally, does not care about ACK
     * @param message the message you want to process
     */
    public static void processTeFubMessage(TeFubMessage message) {
        int code = message.getCode();
        switch (code) {
            case TeFubMessage.ADDITION:                              // addition and error
                System.out.println("addition");
                System.out.println(message);
                break;
           default:
                System.err.println("Unexpected message type");
        }
    }


    /**
     * THe main method for the client
     * @param args destination address, local IP, local port
     * @throws IOException if socket closes unexpectedly
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            throw new IllegalArgumentException(
                    "Parameters(s) : <Multicast Address> " +
                            "<Port>"
            );
        }
        destAddress = InetAddress.getByName(args[0]);
        desPort = Integer.parseInt(args[1]);
        sock = new MulticastSocket(desPort);
        if(!destAddress.isMulticastAddress()){
            throw new IllegalArgumentException(
                    "Has to be Multicast Address \n" +
                            "Parameters(s) : <Multicast Address> " +
                            "<Port>"
            );
        }
        new InputWatcher().start(); // start a thread that watches the thing
        startup();
        while (!quit) {
            DatagramPacket messageReceived = new DatagramPacket(new byte[MAX_MESSAGE_SIZE], MAX_MESSAGE_SIZE);
            try {
                sock.receive(messageReceived);
            } catch (SocketTimeoutException e) {
                // make sure it is not blocking, if nothing received do not try to process the message
                continue;
            }
            TeFubMessage temp = getMessage(messageReceived);
            if(temp != null) {
                processTeFubMessage(temp);
            }
        }
        if (!sock.isClosed()) {
            shutDown();
        }
    }

    /**
     * A simple class that monitors user input
     */
    public static class InputWatcher extends Thread {
        /**
         * Simple run interface for the thread
         */
        public void run() {
            Scanner scanner = new Scanner(System.in);
            String temp;
            while (!MulticastClient.quit) {
                temp = scanner.nextLine();
                if ("quit".equals(temp)) {
                    MulticastClient.quit = true;
                } else {
                    // I don't know if I should print this out to the console, I think it makes senses to print it
                    System.out.println("Enter quit to end the connection");
                }
            }
        }
    }
}
