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
		return readBuff;
	}
}
