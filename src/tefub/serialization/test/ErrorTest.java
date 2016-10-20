/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 4
 * Class: CSI 4321
 *
 ************************************************/
package tefub.serialization.test;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import tefub.serialization.TeFubMessage;

import java.io.IOException;
import java.util.Collection;

/**
 * The parameterized test for error message
 */
@RunWith(Parameterized.class)
public class ErrorTest extends TeFubMessageTest {

    /**
     * Set up the data for running test on Error message
     * @return the test data
     */
    @Parameterized.Parameters
    public static Collection<Object[]> data(){return null;}
    public ErrorTest(byte[] expSerialization, int expMsgId, int expCode) {
        super(expSerialization, expMsgId, expCode);
    }

    /**
     * Verify if this TeFubMessage is as expected
     * @param teFubMessage message to be tested
     */
    @Override
    public void verifyExpectedMessage(TeFubMessage teFubMessage) {

    }

    /**
     * Get the deserialized byte array of the Error message
     * @return deserialized byte array
     * @throws IllegalArgumentException if the encode fails
     * @throws IOException null message or invalid field
     */
    @Override
    public TeFubMessage getDeserializeMessage() throws IllegalArgumentException, IOException {
        return null;
    }
}