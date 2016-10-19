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
 * The test for Deregister test, mainly for encode and decode
 */
@RunWith(Parameterized.class)
public class DeregisterTest extends TeFubMessageTest{
    /**
     * Set the data for the test, it's a combination of base class and this
     * class' test data
     * @return the data for the test
     */
    @Parameterized.Parameters
    public static Collection<Object[]> data(){return null;}

    /**
     * Construct the test, a set up
     * @param expSerialization expected byte array
     * @param expMsgId expected message id
     * @param expCode expected code
     */
    public DeregisterTest(byte[] expSerialization, int expMsgId, int expCode) {
        super(expSerialization, expMsgId, expCode);
    }

    /**
     * Verify if a teFubMessage is expected
     * @param teFubMessage the message to be tested
     */
    @Override
    public void verifyExpectedMessage(TeFubMessage teFubMessage) {

    }

    /**
     * Get the of the deserialization of Deregister
     * @return the byte array of the message
     * @throws IllegalArgumentException if the message field is invalid
     * @throws IOException if the message is null or it fails to validate
     */
    @Override
    public TeFubMessage getDeserializeMessage() throws IllegalArgumentException, IOException {
        return null;
    }
}