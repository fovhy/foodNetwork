/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 4
 * Class: CSI 4321
 *
 ************************************************/
package tefub.serializaiton.test;

import tefub.serializaiton.TeFubMessage;

import java.io.IOException;

import static org.junit.Assert.*;

public class AdditionTest extends TeFubMessageTest{

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