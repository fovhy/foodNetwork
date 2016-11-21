package foodnetwork.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * TCP echo server using asynchronous I/O
 * @version 0.1
 */
public class TCPEchoServerAIO {

	/**
	 * Logger for server
	 */
	protected static final Logger logger = Logger.getLogger("TCPEchoServerAIO");

	// Configure logger handler (connections.log) and format (simple)
	static {
		logger.setUseParentHandlers(false);
		try {
			Handler handler = new FileHandler("connections.log");
			handler.setFormatter(new SimpleFormatter());
			logger.addHandler(handler);
		} catch (Exception e) {
			System.err.println("Unable to initialized logger");
			System.exit(1);
		}
	}

	public static void main(final String[] args) {
		// Test for args correctness and process
		if (args.length != 1) {
			throw new IllegalArgumentException("Parameter(s): <Port>");
		}
		// Local server port
		int port = Integer.parseInt(args[0]);

		// Create listening socket channel
		AsynchronousServerSocketChannel listenChannel = null;
		try {
			// Bind local port
			listenChannel = AsynchronousServerSocketChannel.open().bind(
					new InetSocketAddress(port));
			// Create accept handler
			listenChannel.accept(null,
					makeAcceptCompletionHandler(listenChannel, logger));
		} catch (IOException ex) {
			System.err.println("Unable to create server socket channel: "
					+ ex.getMessage());
			System.exit(1);
		}
		// Block until current thread dies
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Create completion handler for accept
	 * 
	 * @param listenChannel channel listening for new clients
	 * @param logger server logger
	 * 
	 * @return completion handler
	 */
	public static CompletionHandler<AsynchronousSocketChannel, Void> makeAcceptCompletionHandler(
			final AsynchronousServerSocketChannel listenChannel, final Logger logger) {
		return new CompletionHandler<AsynchronousSocketChannel, Void>() {
			/* 
			 * Called when accept completes
			 * 
			 * @param clntChan channel for new client
			 * @param v void means no attachment
			 */
			@Override
			public void completed(AsynchronousSocketChannel clntChan, Void v) {
				logger.info("Handling accept for " + clntChan);
				listenChannel.accept(null, this);
				TCPAIODispatcher aioDispatcher = new TCPAIODispatcher(new EchoAIOHandler(), logger);
				aioDispatcher.handleAccept(clntChan);
			}

			/*
			 * Called if accept fails
			 * 
			 * @param ex exception triggered by accept failure
			 * @param v void means no attachment
			 */
			@Override
			public void failed(Throwable ex, Void v) {
				logger.log(Level.WARNING, "accept failed", ex);
			}
		};
	}
}
