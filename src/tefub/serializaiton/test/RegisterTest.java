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
import java.net.Inet4Address;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class RegisterTest extends TeFubMessageTest{
    private Inet4Address expAddress;
    private int expPort;
    @Parameterized.Parameters
    public static Collection<Object[]> data(){return null;}
    public RegisterTest(byte[] expSerialization, int expMsgId, int expCode,
                        Inet4Address expAddress, int expPort) {
        super(expSerialization, expMsgId, expCode);
        this.expAddress = expAddress;
        this.expPort = expPort;
    }

    @Override
    public void verifyExpectedMessage(TeFubMessage teFubMessage) {
    }

    @Override
    public TeFubMessage getDeserializeMessage() throws IllegalArgumentException, IOException {
        return null;
    }
}