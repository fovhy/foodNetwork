/************************************************
 *
 * Author: Dean He
 * Assignment: Program 4
 * Class: CSI 4321
 *
 ************************************************/
package tefub.client;

import tefub.serialization.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import tefub.utility.AddressUtility;

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

    /**
     * Get and random unsigned ID from 0 to 255
     * @return messageID
     */
    public static int genRandomMessageID() {
        return (new Random().nextInt() % MAX_MSGID + MAX_MSGID) % MAX_MSGID;
    }

    /**
     * Absolute biggest datagram size could be without minus the header
     */
    public static final int MAX_MESSAGE_SIZE = 65355;
    private static final int TIMER = 3000; // 3s timeout

    /**
     * The implementation of the two way handshake of the UDP packet, sort of..
     * @param data             the data you want to send out
     * @param terminateMessage termination message when it fails to shake the hand
     * @return the data received from the server
     * @throws IOException if the data communication channel closes unexpectedly
     */
    private static byte[] firstStep(byte[] data, String terminateMessage,
                                    int expectedMsgID) throws IOException {
        DatagramPacket messageSent = new DatagramPacket(data, data.length);
        sock.send(messageSent);
        DatagramPacket messageReceived = new DatagramPacket(new byte[MAX_MESSAGE_SIZE], MAX_MESSAGE_SIZE);
        sock.setSoTimeout(TIMER);
        try {
            sock.receive(messageReceived);
            return Arrays.copyOfRange(messageReceived.getData(), 0, messageReceived.getLength());
        } catch (SocketTimeoutException e) {
            resendMessage(expectedMsgID, terminateMessage, data);
            return null;         // time out twice leave the program
        }
    }

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
     * Handshake when startup and shutdown
     * @param terminateMessage the message you want to print if handshake fails
     * @param messageCode      what kind of message you want to send to server
     * @throws IOException if the socket closes unexpectedly
     */
    public static void handShake(String terminateMessage, int messageCode) throws IOException {
        int MsgId = genRandomMessageID();
        TeFubMessage generalMessage = null;
        if (messageCode == TeFubMessage.REGISTER) {
            generalMessage = new Register(MsgId, (Inet4Address) localAddress, localPort);
        } else if (messageCode == TeFubMessage.DEREGISTER) {
            generalMessage = new Deregister(MsgId, (Inet4Address) localAddress, localPort);
        }
        if (generalMessage == null) {
            throw new IOException("Fail to generate TeFubMessage.");
        }
        byte[] data = generalMessage.encode();
        byte[] receivedData = firstStep(data, terminateMessage, MsgId);
        if (receivedData == null) {
            return;
        }
        TeFubMessage receivedMessage;
        try {
            receivedMessage = getMessage(receivedData);
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Unable to parse message: " + new String(receivedData, "ASCII"));
            resendMessage(MsgId, terminateMessage, data);
            return;
        }
        processACKTeFubMessage(receivedMessage, MsgId, terminateMessage, true);
    }

    private static String SHUTDOWN_FAIL = "Unable to deregister";

    /**
     * Shut down the client. Will conduct handshake in the process
     * @throws IOException if the socket closes unexpectedly
     */
    public static void shutDown() throws IOException {
        handShake(SHUTDOWN_FAIL, TeFubMessage.DEREGISTER);
        quit = true;
        sock.close();
    }

    private final static String STARTUP_FAIL = "Unable to register"; // console prompt if register fails

    /**
     * Start up the connection between client and server
     * @throws IOException if the communication channel closes unexpectedly
     */
    public static void startup() throws IOException {
        handShake(STARTUP_FAIL, TeFubMessage.REGISTER);
    }

    /**
     * Resend the message to the server
     * @param expectedMsgID expected message ID
     * @param terminate     the message to console when it did not receive anything from server
     * @param data          the data you want to send to server
     * @throws IOException if socket closes unexpectedly
     */
    public static void resendMessage(int expectedMsgID, String terminate, byte[] data) throws IOException {
        DatagramPacket packet = new DatagramPacket(data, data.length);
        sock.send(packet);
        sock.setSoTimeout(TIMER);
        byte[] receivedData = new byte[MAX_MESSAGE_SIZE];
        packet = new DatagramPacket(receivedData, MAX_MESSAGE_SIZE); // assign a new one to receive
        try {
            sock.receive(packet);
            processACKTeFubMessage(TeFubMessage.decode(Arrays.copyOfRange(receivedData, 0, packet.getLength())),
                    expectedMsgID, terminate, false);
        } catch (SocketTimeoutException e) {
            terminate(terminate);
        }
    }

    /**
     * Simply wait for another time out period, not resending anything back to server
     * @param expectedMsgID expected message ID
     * @param terminate     the terminate message from the server
     * @throws IOException if socket closes unexpectedly
     */
    public static void receiveAndTerminate(int expectedMsgID, String terminate) throws IOException {
        sock.setSoTimeout(TIMER);
        byte[] receivedData = new byte[MAX_MESSAGE_SIZE];
        DatagramPacket packet = new DatagramPacket(receivedData, MAX_MESSAGE_SIZE); // assign a new one to receive
        try {
            sock.receive(packet);
            processACKTeFubMessage(TeFubMessage.decode(Arrays.copyOfRange(receivedData, 0, packet.getLength())),
                    expectedMsgID,
                    terminate,
                    true);
        } catch (SocketTimeoutException e) {
            terminate(terminate);
        }
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
            case TeFubMessage.ERROR:                             // addition and error
                System.err.println(message);
                break;
            default:
                System.err.println("Unexpected message type");
        }
    }

    /**
     * Process the ACK message in the startup and shutdown process
     * @param message    the message you want to check
     * @param expectedID the expected messageID for the ACK message
     * @param terminate  the error message you want to print to the console
     * @param resend     whether you want to resend the TeFubMessage
     * @throws IOException if socket closes unexpectedly
     */
    public static void processACKTeFubMessage(TeFubMessage message, int expectedID,
                                              String terminate, boolean resend) throws IOException {
        boolean correctACK = false;
        if (message.getCode() != TeFubMessage.ACK) {
            processTeFubMessage(message);
        } else {
            if (message.getMsgId() != expectedID) {
                System.err.println("Unexpected MSG ID");
            } else {
                correctACK = true;
            }
        }
        if (!correctACK) {
            if (resend) {
                resendMessage(expectedID, terminate, message.encode());
            } else {
                receiveAndTerminate(expectedID, terminate);
            }
        }
    }

    /**
     * Terminate the connection, no hand shake
     * @param terminateMessage the message you want to print out
     */
    public static void terminate(String terminateMessage) {
        sock.close();
        quit = true;
        if (terminateMessage.length() > 0) {
            System.err.println(terminateMessage);
        }
        System.exit(1);
    }

    /**
     * THe main method for the client
     * @param args destination address, local IP, local port
     * @throws IOException if socket closes unexpectedly
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            throw new IllegalArgumentException(
                    "Parameters(s) : <Destination> " +
                            "<Port>"
            );
        }
        destAddress = InetAddress.getByName(args[0]);
        desPort = Integer.parseInt(args[1]);
        sock = new DatagramSocket();  // construct UDP socket
        sock.connect(destAddress, desPort);
        localAddress = AddressUtility.getAddress();
        System.out.println(destAddress);
        localPort = sock.getLocalPort();
        new InputWatcher().start();
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
            while (!TeFubClient.quit) {
                temp = scanner.nextLine();
                if ("quit".equals(temp)) {
                    TeFubClient.quit = true;
                } else {
                    // I don't know if I should print this out to the console, I think it makes senses to print it
                    System.out.println("Enter quit to end the connection");
                }
            }
        }
    }
}
