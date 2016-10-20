/************************************************
 *
 * Author: Nicholas Hopper
 * Assignment: Program 4
 * Class: CSI 4321
 *
 ************************************************/

package tefub.serialization.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import org.junit.Test;

import tefub.serialization.Deregister;

/**
 * @author hopper
 *
 */
public class DeregisterTest {

    /**
     *
     */
    @Test
    public void constructorTest() {
        try {
            new Deregister(0, (Inet4Address) Inet4Address.getByName("google.com"), 0);
            new Deregister(0, (Inet4Address) Inet4Address.getByName("google.com"), 65535);
            new Deregister(0, (Inet4Address) Inet4Address.getByName("baylor.edu"), 8080);
        } catch (Exception e){
            fail("Unexpected exception" + e.toString());
        }
        try {
            new Deregister(0, null, 0);
            fail("Illegal argument exception should occur");
        } catch (IllegalArgumentException e) {
        } catch (IOException e) {
        }
        try {
            new Deregister(0, (Inet4Address) Inet4Address.getByName("google.com"), 0);
        } catch (IllegalArgumentException e) {
        } catch (UnknownHostException e) {
            fail("Unexpected exception" + e.toString());
        } catch (IOException e) {
        }
        try {
            new Deregister(0, (Inet4Address) Inet4Address.getByName("google.com"), 65536);
        } catch (IllegalArgumentException e) {
        } catch (UnknownHostException e) {
            fail("Unexpected exception" + e.toString());
        } catch (IOException e) {
        }
    }

}
