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

@RunWith(Parameterized.class)
public class AdditionTest extends TeFubMessageTest{
    @Parameterized.Parameters
    public static Collection<Object[]> data(){return null;}

    public AdditionTest(byte[] expSerialization, int expMsgId, int expCode) {
        super(expSerialization, expMsgId, expCode);
    }

    @Override
    public void verifyExpectedMessage(TeFubMessage teFubMessage) {

    }

    @Override
    public TeFubMessage getDeserializeMessage() throws IllegalArgumentException, IOException {
        return null;
    }
}