/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 4
 * Class: CSI 4321
 *
 ************************************************/
package tefub.serializaiton.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import tefub.serializaiton.TeFubMessage;

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
    protected long expMsgId;          // expected message ID
    protected int expCode;           // expected messageCode
    public static final String CHARSET = "ASCII"; // default encoding
    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        ArrayList testList = new ArrayList();
        // current version number
        String versionNumber = "0011";
        // 5 codes
        ArrayList<String> codeNumbers = new ArrayList<String>();
        codeNumbers.add("0000");
        codeNumbers.add("0001");
        codeNumbers.add("0010");
        codeNumbers.add("0011");
        codeNumbers.add("0100");

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
    public TeFubMessageTest(byte[] expSerialization, int expMsgId, int expCode){
        this.expSerialization = expSerialization;
        this.expMsgId = expMsgId;
        this.expCode = expCode;
    }
    public abstract void verifyExpectedMessage(TeFubMessage teFubMessage);
    public abstract TeFubMessage getDeserializeMessage()throws IllegalArgumentException, IOException;
    protected static byte[] binaryStringToByteArray(String binaryString){
        return new BigInteger(binaryString, 2).toByteArray();
    }
    public static byte[] concat(byte[] first, byte[] second) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        outputStream.write(first);
        outputStream.write(second);
        return outputStream.toByteArray( );
    }
    protected static <T> T[] concat(T[] first, T[] second){
        // concat two generic array
        T[] toReturn = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, toReturn, first.length, second.length);
        return toReturn;
    }
    protected static Object[] buildTest(Object[] base, Object[] sub) throws IOException {
        byte[] expBytes = concat((byte[])base[0],(byte[])sub[0]);
        return concat(new Object[]{expBytes},
                concat(Arrays.copyOfRange(base, 1, base.length),
                Arrays.copyOfRange(sub, 1, sub.length))
                );
    }


    public void verifyBaseMessage(TeFubMessage teFubMessage, int expMsgId, int expCode){
        assertEquals(expMsgId, teFubMessage.getMsgId());
        assertEquals(expMsgId, teFubMessage.getCode());
    }
    @Test
    public void testDecode() throws IOException {
        TeFubMessage message = TeFubMessage.decode(this.expSerialization);
        this.verifyExpectedMessage(message);
    }
    @Test
    public void testEncode() throws IOException {
        TeFubMessage message = this.getDeserializeMessage();
        assertEquals(expSerialization, message.encode());
    }
    @Test(expected = IOException.class)
    public void testShortDecode() throws IOException {
        byte[] shortArray = Arrays.copyOfRange(this.expSerialization, 0, this.expSerialization.length - 1);
        TeFubMessage.decode(shortArray);
    }
}