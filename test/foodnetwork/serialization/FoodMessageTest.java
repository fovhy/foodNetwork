/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 1
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FoodMessageTest {
    private GetFood getFood;
    @Before
    public void SetUp() throws FoodNetworkException {
        getFood = new GetFood(20L);
    }
    @Test
    public void testSetTimestamp() throws FoodNetworkException {
        long timestamp = 0L;
        getFood.setMessageTimestamp(timestamp);
        assertEquals(timestamp, getFood.getMessageTimestamp());
    }

    @Test(expected = FoodNetworkException.class)
    public void testSetTimestampWithNegativeValue() throws FoodNetworkException {
        getFood.setMessageTimestamp(-1L);
    }

}