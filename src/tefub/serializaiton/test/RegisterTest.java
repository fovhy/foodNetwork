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
import tefub.serializaiton.LittleEndianCoder;
import tefub.serializaiton.TeFubMessage;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Parameterized testx for Register class, test for decode and encode. The test can be only done with internet
 * connection.
 */
@RunWith(Parameterized.class)
public class RegisterTest extends TeFubMessageTest{
    private Inet4Address expAddress;
    private int expPort;
    @Parameterized.Parameters
    public static Collection<Object[]> data() throws IOException {
        ArrayList testData = new ArrayList();
        InetAddress testAddress1 = Inet4Address.getByName("www.google.com");
        InetAddress testAddress2 = Inet4Address.getByName("www.youtube.com");
        int port = 123;
        byte[] portBytes = new byte[]{123 & 0xFF, 0}; // in little Endian order
        byte[] testAddressLittleEndian1 = testAddress1.getAddress();  // get the big endian address byte array
        byte[] testAddressLittleEndian2 = testAddress2.getAddress();
        LittleEndianCoder.reverse(testAddressLittleEndian1);      // turn them into little endian
        LittleEndianCoder.reverse(testAddressLittleEndian2);      // turn them into little endian
        Object[][] subData = new Object[][]{
                {concat(testAddressLittleEndian1, portBytes), testAddress1, port},
                {concat(testAddressLittleEndian2, portBytes), testAddress2, port},
        };
        Iterator i = TeFubMessageTest.data().iterator();
        while(i.hasNext()){
            Object[] baseData = (Object[]) i.next();
            int subDataLength = subData.length;
            for(int j = 0; j < subDataLength; j++){
                testData.add(buildTest(baseData, subData[j]));
            }
        }
        return testData;
    }
    public RegisterTest(byte[] expSerialization, int expMsgId, int expCode,
                        Inet4Address expAddress, int expPort) {
        super(expSerialization, expMsgId, expCode);
        this.expAddress = expAddress;
        this.expPort = expPort;
    }

    @Override
    public void verifyExpectedMessage(TeFubMessage teFubMessage) {
        verifyExpectedMessage(teFubMessage, expAddress, expPort);
    }

    public void verifyExpectedMessage(TeFubMessage teFubMessage, Inet4Address address, int port){
        TeFubMessageTest.verifyBaseMessage(teFubMessage, this.expMsgId, expCode, TeFubMessage.class);
        assertEquals(expAddress, address);
        assertEquals(expPort, port);
    }

    @Override
    public TeFubMessage getDeserializeMessage() throws IllegalArgumentException, IOException {
        return null;
    }
}