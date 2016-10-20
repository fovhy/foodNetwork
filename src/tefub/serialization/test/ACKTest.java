/************************************************
 *
 * Author: Nicholas Hopper
 * Assignment: Program 4
 * Class: CSI 4321
 *
 ************************************************/

package tefub.serialization.test;

import static org.junit.Assert.*;

import org.junit.Test;

import tefub.serialization.ACK;

/**
 * @author hopper
 *
 */
public class ACKTest {

    /**
     * This test all of ACK's functionality
     */
    @Test
    public void constructorTest() {
        try {
            ACK a1 = new ACK(0);
            a1 = new ACK(255);
            a1.setMsgId(0);
            a1.setMsgId(255);
            a1.setMsgId(5);
        } catch (Exception e) {
            fail("Unexpected exception " + e.toString());
        }
        try {
            new ACK(-1);
            fail("Illegal argument exception should occur");
        } catch (IllegalArgumentException e) {
        }
        try {
            new ACK(256);
            fail("Illegal argument exception should occur");
        } catch (IllegalArgumentException e) {
        }
        try {
            new ACK(10000);
            fail("Illegal argument exception should occur");
        } catch (IllegalArgumentException e) {
        }
    }

}
