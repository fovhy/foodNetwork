/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 3
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization.test;

import foodnetwork.serialization.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *  Parameterized test on GetFood class
 */
@RunWith(Parameterized.class)
public class IntervalTest {
    private final String CHARSET = "ASCII";

    private String expDecode;
    private Long expTimestamp;
    private int expIntervalTime;
    private Interval expInterval;

    /**
     * Set up parameters
     * @return parameters array
     * @throws FoodNetworkException fails to construct a Interval object
     */
    @Parameters
    public static Collection<Object[]> data() throws FoodNetworkException {
        ArrayList list = new ArrayList();
        list.add(new Object[]{"FN1.0 23 INTERVAL 233 \n", 23L, 233});
        list.add(new Object[]{"FN1.0 10000 INTERVAL 12 \n", 10000L, 12});
        list.add(new Object[]{"FN1.0 0 INTERVAL 0 \n", 0L, 0});
        return list;
    }

    /**
     * Set up a Interval item with expected values from parameters
     * @param code expected Decoded String
     * @param timestamp expected timestamp
     * @param intervalTime the expected time interval
     * @throws FoodNetworkException Fails to construct a GetFoodItem
     */
    public IntervalTest(String code, long timestamp, int intervalTime) throws FoodNetworkException {
        this.expDecode = code;
        this.expTimestamp = timestamp;
        this.expIntervalTime =  intervalTime;
        this.expInterval = new Interval(timestamp, intervalTime);
    }

    /**
     * Test if Interval attributes is consistent with expected values
     * @param interval a interval object to be tested
     */
    public void checkInterval(Interval interval){
        assertEquals(expTimestamp, new Long(expInterval.getMessageTimestamp()));
        assertEquals(expIntervalTime, expInterval.getIntervalTime());
    }

    /**
     * Test over Interval constructor
     */
    @Test
    public void TestConstructor(){
        checkInterval(expInterval);
    }

    /**
     * Test decode function of Interval
     * @throws FoodNetworkException fails to construct a Interval object
     * @throws UnsupportedEncodingException wrong encoding
     * @throws EOFException if stream ends prematurely
     */
    @Test
    public void testDecode() throws FoodNetworkException, UnsupportedEncodingException, EOFException {
        MessageInput in = new MessageInput(new ByteArrayInputStream(expDecode.getBytes(CHARSET)));
        Interval temp = (Interval) Interval.decode(in);
        checkInterval(temp);
    }

    /**
     * Test encode function of Interval (test getFullRequest)
     * @throws FoodNetworkException if it fails to encode
     * @throws UnsupportedEncodingException for wrong encoding
     */
    @Test
    public void TestEncode() throws FoodNetworkException, UnsupportedEncodingException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        MessageOutput out = new MessageOutput(outStream);
        expInterval.encode(out);
        assertArrayEquals(expDecode.getBytes(CHARSET), outStream.toByteArray());
    }
    /**
     * Test if toString function throws out a String
     */
    @Test
    public void testToString(){
        assertNotNull(expInterval.toString());
    }
}
