/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 4
 * Class: CSI 4321
 *
 ************************************************/
package tefub.serialization.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import tefub.serialization.TeFubMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * The base class test for all the TeFubMessage
 */
@RunWith(Parameterized.class)
public abstract class TeFubMessageTest {
    protected byte[] expSerialization; // expected serialization for TeFubMessage
    protected int expMsgId;          // expected message ID (can only be 8 bit)
    protected int expCode;           // expected messageCode (can only be 4 bit)
    public static final String CHARSET = "ASCII"; // default encoding

    /**
     * The data for parameterized test
     * @return the data that will be used to run the test
     * @throws IOException if it fails to copy data in sub classes
     */
    @Parameterized.Parameters
    public static Collection<Object[]> data() throws IOException {
        ArrayList testList = new ArrayList();
        // current version number
        String versionNumber = "0011";
        // 5 codes
        ArrayList<String> codeNumbers = new ArrayList<String>();
        codeNumbers.add("0000");

        String MsgId = "00000000";
        for(String codeNumber : codeNumbers){
            testList.add(new Object[]{
                    binaryStringToByteArray(versionNumber + codeNumber + MsgId),
                    Integer.parseInt(codeNumber, 2),
                    Integer.parseInt(MsgId, 2)
            });
        }

        return testList;
    }

    /**
     * The constructor for the TeFubMessage test. Equals to setup
     * @param expSerialization expected byte array
     * @param expMsgId expected message ID
     * @param expCode expected code
     */
    public TeFubMessageTest(byte[] expSerialization, int expMsgId, int expCode){
        this.expSerialization = expSerialization;
        this.expMsgId = expMsgId;
        this.expCode = expCode;
    }

    /**
     * The method for sub classes to very their expected message, or byte array
     * in this case
     * @param teFubMessage the message to examine
     */
    public abstract void verifyExpectedMessage(TeFubMessage teFubMessage);
    public abstract TeFubMessage getDeserializeMessage()throws IllegalArgumentException, IOException;
    protected static byte[] binaryStringToByteArray(String binaryString){
        return new BigInteger(binaryString, 2).toByteArray();
    }

    /**
     * Concat two byte stream
     * @param first the first byte array to combine
     * @param second the second byte array to combine
     * @return the combined stream
     * @throws IOException if the write to byteStream fails
     */
    public static byte[] concat(byte[] first, byte[] second) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        outputStream.write(first);
        outputStream.write(second);
        return outputStream.toByteArray( );
    }

    /**
     * Generic function for combine two
     * @param first the first generic array
     * @param second the second generic array
     * @param <T> the data type of the array
     * @return the combined array
     */
    protected static <T> T[] concat(T[] first, T[] second){
        // concat two generic array
        T[] toReturn = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, toReturn, first.length, second.length);
        return toReturn;
    }

    /**
     * Combine two collection array, here it is used to combine the test data
     * of base class and derived classes
     * @param base the base class test data
     * @param sub the sub class test data
     * @return the combined test data
     * @throws IOException if the system fails to copy the array
     */
    protected static Object[] buildTest(Object[] base, Object[] sub) throws IOException {
        byte[] expBytes = concat((byte[])base[0],(byte[])sub[0]);
        return concat(new Object[]{expBytes},
                concat(Arrays.copyOfRange(base, 1, base.length),
                Arrays.copyOfRange(sub, 1, sub.length))
                );
    }


    /**
     * Verify the base class data of base class
     * @param teFubMessage the teFubMessage to be tested
     * @param expMsgId the expected message id
     * @param expCode the expected code
     * @param sub the sub class of the base class
     */
    public static void verifyBaseMessage(TeFubMessage teFubMessage, int expMsgId, int expCode, Class<?> sub){
        assertTrue(sub.isInstance(teFubMessage));
        assertEquals(expMsgId, teFubMessage.getMsgId());
        assertEquals(expMsgId, teFubMessage.getCode());
    }

    /**
     * Test the decode function for TeFubMessage
     * @throws IOException if it fails to decode
     */
    @Test
    public void testDecode() throws IOException {
        TeFubMessage message = TeFubMessage.decode(this.expSerialization);
        this.verifyExpectedMessage(message);
    }

    /**
     * Test the encode function for the TeFubMessage
     * @throws IOException if the encode fails
     */
    @Test
    public void testEncode() throws IOException {
        TeFubMessage message = this.getDeserializeMessage();
        assertEquals(expSerialization, message.encode());
    }

}