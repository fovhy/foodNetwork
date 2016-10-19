/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 4
 * Class: CSI 4321
 *
 ************************************************/
package tefub.serializaiton.test;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import tefub.serializaiton.TeFubMessage;

import java.io.IOException;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * The test class for ACK
 */
@RunWith(Parameterized.class)
public class ACKTest extends TeFubMessageTest{
    public ACKTest(byte[] expSerialization, int expMsgId, int expCode) {
        super(expSerialization, expMsgId, expCode);
    }

    /**
     * Get the test data for ACK
     * @return data for testing
     */
    @Parameterized.Parameters
    public static Collection<Object[]> data(){return null;}

    /**
     * Verify the message is expected or not
     * @param teFubMessage
     */
    @Override
    public void verifyExpectedMessage(TeFubMessage teFubMessage) {

    }

    /**
     * Get a TeFubMessage from the expected message
     * @return expected TeFubMesage
     * @throws IllegalArgumentException if validation fails
     * @throws IOException if the TeFubMelange is null or invalid
     */
    @Override
    public TeFubMessage getDeserializeMessage() throws IllegalArgumentException, IOException {
        return null;
    }

}