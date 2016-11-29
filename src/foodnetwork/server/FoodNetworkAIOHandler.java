/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 7
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.server;

/**
 * Asynchronous I/O implementation of echo protocol
 * @version 0.1
 */
public class FoodNetworkAIOHandler implements AIOHandler {
	/* (non-Javadoc)
	 * @see AIOHandler#handleRead(byte[])
	 */
	@Override
	public byte[] handleRead(byte[] readBuff) {
		// Simply repeat read bytes
		System.out.println(new String(readBuff));
        System.out.println("new line");
		return readBuff;
	}
}
