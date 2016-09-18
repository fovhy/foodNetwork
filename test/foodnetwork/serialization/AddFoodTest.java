/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 1
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.Parameter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Parametrized test for AddFood
 */
@RunWith(Parameterized.class)
public class AddFoodTest {
    private final String CHARSET = "ASCII";

    private String expDecode;
    private Long expTimeStamp;
    private FoodItem expFoodItem;
    private AddFood expAddFood;
    @Parameters
    public static Collection<Object[]> data() throws FoodNetworkException {
        ArrayList list = new ArrayList();
        list.add(new Object[]{"FN1.0 200 ADD 5 AppleB32 0.7", 200L,
                new FoodItem("Apple", MealType.Breakfast, 32L, "0.7")});
        list.add(new Object[]{"FN1.0 0 ADD 4 PearL23 0.12", 0L,
                new FoodItem("Pear", MealType.Lunch, 23L, "0.12")});
        list.add(new Object[]{"FN1.0 300000 ADD 3 AxeD12 0.45", 23L,
                new FoodItem("Axe", MealType.Dinner, 12L, "0.45")});
        return list;
    }

    /**
     * Construct a test
     * @param code serialization String
     * @param timeStamp when message is created
     * @param foodItem a FoodItem
     * @throws FoodNetworkException if it fails to construct an AddFood object
     */
    public AddFoodTest(String code, long timeStamp, FoodItem foodItem) throws FoodNetworkException {
        this.expDecode = code;
        this.expTimeStamp = timeStamp;
        this.expFoodItem = foodItem;
        this.expAddFood = new AddFood(expTimeStamp, expFoodItem);
    }

    /**
     * Check an AddFood works correctly
     * @param addFood
     */
    public void checkAddFood(AddFood addFood){
        assertEquals(expTimeStamp, new Long(addFood.getMessageTimestamp()));
        assertEquals(expFoodItem, addFood.getFoodItem());
    }

    /**
     * Test AddFood fields constructor
     * @throws FoodNetworkException if it fails to construct an AddFood
     */
    @Test
    public void testConstructor() throws FoodNetworkException{
        checkAddFood(expAddFood);
    }

    /**
     * Test decode function for AddFood
     * @throws UnsupportedEncodingException wrong encoding
     * @throws FoodNetworkException if decode fails
     */
    @Test
    public void testDecode() throws UnsupportedEncodingException, FoodNetworkException {
        MessageInput in = new MessageInput(new ByteArrayInputStream(expDecode.getBytes(CHARSET)));
        AddFood temp = (AddFood) FoodMessage.decode(in);
        checkAddFood(temp);
    }

    /**
     * Test ADDFood encoding. (test getRequest)
     * @throws FoodNetworkException
     * @throws UnsupportedEncodingException
     */
    @Test
    public void testEncode() throws FoodNetworkException, UnsupportedEncodingException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        MessageOutput out = new MessageOutput(outStream);
        expAddFood.encode(out);
        assertArrayEquals(expDecode.getBytes(CHARSET), outStream.toByteArray());
    }

    /**
     * Test getter and setter for FoodItem
     * @throws FoodNetworkException if FoodItem is null
     */
    @Test
    public void testGetFoodItem() throws FoodNetworkException {
        FoodItem temp = new FoodItem("beef", MealType.Breakfast, 122L, "12.3");
        expAddFood.setFoodItem(temp);
        assertEquals(temp, expAddFood.getFoodItem());
    }

    /**
     * Test if toString function throws out a String
     */
    @Test
    public void testToString(){
        assertNotNull(expAddFood.toString());
    }
}