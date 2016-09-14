/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 0
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;


/**
 * Test for MessageOutputTest class. Basically the encode function from foodItem
 * @version 1.1 9/14/2016
 * @author Dean He
 */
public class MessageOutputTest {
    /**
     * Test encoding using a messageOutput object
     * @throws FoodNetworkException if the foodItem is incomplete
     * @throws IOException outputStream prematurally closes
     */
    @Test(expected = FoodNetworkException.class)
    public void testOutputIncompleteObject() throws FoodNetworkException, IOException {
        FoodItem incompleteObject = new FoodItem("", MealType.Breakfast, 123L, "");
        MessageOutput messageOutput = new MessageOutput(new ByteArrayOutputStream(1024));
        messageOutput.writeObject(incompleteObject);
        fail("Should throw wrong FoodItem");
    }
}