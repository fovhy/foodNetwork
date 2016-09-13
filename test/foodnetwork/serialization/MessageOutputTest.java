/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 1
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;


public class MessageOutputTest {
    @Test(expected = FoodNetworkException.class)
    public void testOutputIncompleteObject() throws FoodNetworkException, IOException {
        FoodItem incompleteObject = new FoodItem("", MealType.Breakfast, 123L, "");
        MessageOutput messageOutput = new MessageOutput(new ByteArrayOutputStream(1024));
        messageOutput.writeObject(incompleteObject);
        fail("Should throw wrong FoodItem");
    }
}