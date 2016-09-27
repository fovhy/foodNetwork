package foodnetwork.client;
import foodnetwork.serialization.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * A simple client for user to communicate with a server using foodMessage. It will print out error
 * if the communication fails.
 */
public class Client {
    private final static String commuError = "Unable to communicate: ";
    private final static String invalidMessage = "Invalid message: ";
    private final static String unexpctedMessage = "Unexpected message: ";
    private final static String errorMessage = "Error: ";
    private final static String invalidInput = "Invalid user input: ";
    private final static String request = "Request(ADD|GET)> ";
    private final static String selName = "Name> ";
    private final static String selMealType = "Meal Type(B, L, D, S)> ";
    private final static String enterFat = "Fat> ";
    public static void main(String args[]) throws IOException {
        if(args.length != 2){
            throw new IllegalArgumentException("Parameters(s): <Server> <Port>");
        }

        String server = args[0]; // the server address
        int serPort = Integer.parseInt(args[1]); // server port
        Socket socket;
        try {
             socket = new Socket(server, serPort);
        } catch (IOException e) {
            throw new IOException(commuError + "Failed to create socket");
        }
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        System.out.println("Successfully connected to server...");
        String userInput;
        List<String> optionsForRequest = Arrays.asList("ADD", "GET");
        List<String> optionsForMealType = Arrays.asList("B", "L", "D", "S");
        int steps = 0;
        //TODO: implement steps
        switch(steps){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 6:
                break;
        }
    }
}
