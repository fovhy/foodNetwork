/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 1
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization.test;

import foodnetwork.serialization.*;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

/**
 * Test ErrorMessage class
 */
public class ErrorMessageTest {
    private ErrorMessage message1;
    private ErrorMessage message2;
    private long timestamp1;
    private long timestamp2;
    private String error1;
    private String error2;
    private String message1Decode;
    private String message2Decode;

    /**
     * Set up 2 ErrorMessage object for tests
     * @throws FoodNetworkException if fails to construct a ErrorMessage object
     */
    @Before
    public void setUp() throws FoodNetworkException {
        error1 = "This is Sparta";
        error2 = "Kappa123";
        timestamp1 = 12L;
        timestamp2 = 123L;
        message1 = new ErrorMessage(timestamp1, error1);
        message2 = new ErrorMessage(timestamp2, error2);
        message1Decode = "FN1.0 " + timestamp1 + " ERROR " + error1.length() + " " +  error1 + "\n";
        message2Decode = "FN1.0 " + timestamp2 + " ERROR " + error2.length() + " " + error2+ "\n";
    }

    /**
     * Test constructor, make sure ErrorMessage are set to the exact values of the parameters
     */
    @Test
    public void testConstructor(){
        assertEquals(error1, message1.getErrorMessage());
        assertEquals(error2, message2.getErrorMessage());
        assertEquals(timestamp1, message1.getMessageTimestamp());
        assertEquals(timestamp2, message2.getMessageTimestamp());
    }

    /**
     * Set ErorrMessage with null String
     * @throws FoodNetworkException expected
     */
    @Test(expected = FoodNetworkException.class)
    public void testSetErrorMessageWithNull() throws FoodNetworkException {
        message1.setErrorMessage(null);
    }

    /**
     * Test setErrorMessage with empty String
     * @throws FoodNetworkException expected
     */
    @Test(expected = FoodNetworkException.class)
    public void testSetErrorMessageWithEmptyString() throws FoodNetworkException {
        message1.setErrorMessage("");
    }

    /**
     * Test for getErrorMessage
     * @throws FoodNetworkException if errorMessage is illegal
     */
    @Test
    public void testGetMessage() throws FoodNetworkException{
        String temp = "rumble";
        message1.setErrorMessage(temp);
        assertEquals(temp, message1.getErrorMessage());
    }

    /**
     * Test encode function for ErrorMessage. (Test getFullRequest)
     * @throws FoodNetworkException if ErrorMessage is null
     * @throws UnsupportedEncodingException wrong encoding
     */
    @Test
    public void testEncode() throws FoodNetworkException, UnsupportedEncodingException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        MessageOutput out = new MessageOutput(outStream);
        message1.encode(out);
        assertArrayEquals(message1Decode.getBytes("ASCII"), outStream.toByteArray());
    }

    /**
     * Test Decode of the ErrorMessage
     * @throws FoodNetworkException if fails to construct a new ErrorMessage object
     * @throws UnsupportedEncodingException wrong encoding
     * @throws EOFException if the stream ends prematurally
     */
    @Test
    public void testDecode() throws FoodNetworkException, UnsupportedEncodingException, EOFException {
        MessageInput in = new MessageInput(new ByteArrayInputStream(message2Decode.getBytes("ASCII")));
        ErrorMessage temp = (ErrorMessage) FoodMessage.decode(in);
        assertNotNull(temp);
        assertEquals(error2, temp.getErrorMessage());
        assertEquals(timestamp2, temp.getMessageTimestamp());
    }

    /**
     * Test for hash and equals function
     * @throws FoodNetworkException fails to construct a ErrorMessage object
     */
    @Test
    public void testEqualsAndHash() throws FoodNetworkException {
        ErrorMessage first = new ErrorMessage(12L, "nine");
        ErrorMessage second = new ErrorMessage(12L, "nine");
        assertTrue(first.equals(second) && second.equals(first));
        assertEquals(first.hashCode(), second.hashCode());
    }
}