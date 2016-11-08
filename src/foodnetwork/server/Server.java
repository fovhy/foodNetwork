/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 3
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.server;
import foodnetwork.serialization.*;
import foodnetwork.client.Client;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import edu.baylor.googlefit.FoodManager;
import edu.baylor.googlefit.GFitFoodManager;
import tefub.server.TeFubServer;

import static java.util.logging.Level.SEVERE;

/**
 * The server for FoodNetwork. It creates a fixed amount of threads based on command line arguments
 */
public class Server {
    public static void main(String[] args) throws IOException{
        if(args.length != 2){
            throw new IllegalArgumentException("Parameters(s): <Port> <Thread pool count>");
        }
        int echoServPort = Integer.parseInt(args[0]); // get server port
        int threadPoolSize = Integer.parseInt(args[1]); // how big the thread pool is
        // create a socket to accept client connection requests
        ServerSocket servSock = new ServerSocket(echoServPort);
        servSock.setReuseAddress(true);
        final Logger logger = Logger.getLogger("connections");
        Handler handler = new FileHandler("connection.log");  // store a local log file
        logger.addHandler(handler);                          // log to the log file

        Executor service = Executors.newFixedThreadPool(threadPoolSize); // Dispatch service
        FoodManager manager = null;                          // create a manager that connects to google server
        // run forever, accepting and spawning a thread for each connection and log them as well
        TeFubServer teFubServer = new TeFubServer(echoServPort, logger);
        // start the TeFubServer
        teFubServer.start();
        try {
            // for some reason it cannot find the path
            manager = new GFitFoodManager("628103645757", "./cred.json");
        } catch (FoodNetworkException e) {
            logger.log(SEVERE, e.getMessage());
            System.exit(-1);
        }
        while (true){
            Socket clntSock = servSock.accept();   // block waiting for connection
            service.execute(new FoodMessageProtocol(clntSock, logger, manager, teFubServer)); // run threads on clients
        }
    }
}
