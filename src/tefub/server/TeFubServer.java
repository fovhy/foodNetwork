/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 4
 * Class: CSI 4321
 *
 ************************************************/
package tefub.server;

import tefub.serialization.ACK;
import tefub.serialization.Register;
import tefub.serialization.TeFubMessage;
import tefub.serialization.Error;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;

import static java.lang.Thread.sleep;

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
            try {
                sock.receive(packet);
            }catch(SocketTimeoutException e){
                continue;
            }
            TeFubMessage message = TeFubMessage.decode(Arrays.copyOfRange(buffer, 0, packet.getLength()));
            TeFubMessage response;
            switch(message.getCode()){
                case TeFubMessage.REGISTER:
                    response = new ACK(message.getMsgId());
                    System.out.println(response.encode());
                    System.out.println(response.encode().length);
                    packet.setData(response.encode());
                    sock.send(packet);
                    break;
                default:
                    response = new Error(message.getMsgId(), "Hola Hola");
                    packet.setData(response.encode());
                    sock.send(packet);
            }
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            response = new Error(message.getMsgId(), "Hola");
            packet.setData(response.encode());
            sock.send(packet);

        }





    }
}
