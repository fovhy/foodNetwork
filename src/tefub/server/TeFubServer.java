/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 4
 * Class: CSI 4321
 *
 ************************************************/
package tefub.server;

import tefub.serialization.ACK;
import tefub.serialization.TeFubMessage;
import tefub.serialization.Error;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

/**
 * For now it is just an echo server sort of
 */
public class TeFubServer {
    private static DatagramSocket sock;
    public static void main(String[] args) throws IOException {
        if(args.length != 1){
            throw new IllegalArgumentException("Parameter(s) : <Port>");
        }
        int port = Integer.parseInt(args[0]);
        sock = new DatagramSocket(port);
        System.out.println(sock.getLocalSocketAddress());
        while(true){
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            System.out.println("locked");
            sock.receive(packet);
            System.out.println("got something");
            TeFubMessage message = TeFubMessage.decode(Arrays.copyOfRange(buffer, 0, packet.getLength()));
            TeFubMessage response;
            DatagramPacket responsePacket;
            switch(message.getCode()){
                case TeFubMessage.REGISTER:
                    response = new ACK(message.getMsgId());
                    responsePacket = new DatagramPacket(response.encode(), response.encode().length);
                    sock.send(responsePacket);
                    break;
                default:
                    response = new Error(message.getMsgId(), "Hola Hola");
                    responsePacket = new DatagramPacket(response.encode(), response.encode().length);
                    sock.send(responsePacket);
            }
        }

    }
}
