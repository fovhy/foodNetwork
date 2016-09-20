/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 1
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization.test;

import foodnetwork.serialization.FoodNetworkException;
import foodnetwork.serialization.GetFood;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for FoodMessage class. The encoding and decoding are all tested in its subclass. Here
 * it only tests on the setters for messageTimestamp
 */
public class FoodMessageTest {
    private GetFood getFood;

    /**
     * Set up GetFood as the concrete class to test setters for messageTimestamp
     * @throws FoodNetworkException the construction of getFood fails
     */
    @Before
    public void SetUp() throws FoodNetworkException {
        getFood = new GetFood(20L);
    }

    /**
     * Test timestamp with a edge value
     * @throws FoodNetworkException if the timestamp is negative
     */
    @Test
    public void testSetTimestamp() throws FoodNetworkException {
        long timestamp = 0L;
        getFood.setMessageTimestamp(timestamp);
        assertEquals(timestamp, getFood.getMessageTimestamp());
    }

    /**
     * Test setters for messageTimestamp with a negative value
     * @throws FoodNetworkException expected
     */
    @Test(expected = FoodNetworkException.class)
    public void testSetTimestampWithNegativeValue() throws FoodNetworkException {
        getFood.setMessageTimestamp(-1L);
    }

}