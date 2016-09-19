/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 1
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization;

import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

/**
 * Test for bounding cases.
 */
public class AddFoodBoundsTest {
    private static final String CHARSET = "ASCII";
    private long expTimeStamp = 1000L;
    private FoodItem foodItem1;
    private AddFood addFood1;
    private AddFood addFood2;
    private AddFood addFood3;
    public AddFoodBoundsTest() throws FoodNetworkException {
        try{
            foodItem1 = new FoodItem("Apple", MealType.Breakfast, 20L, "3.8");
            addFood1 = new AddFood(expTimeStamp, foodItem1);
            addFood2 = new AddFood(expTimeStamp, foodItem1);
            addFood3 = new AddFood(expTimeStamp + 20, foodItem1);
        } catch(FoodNetworkException e){
            throw new FoodNetworkException("Failed to set up FoodBoundsTest");
        }
    }

    /**
     * Test if an AddFood object has following values
     * @param addFood object to be tested
     * @param timestamp a timestamp that supposed to equal to the AddFood's timestamp
     * @param foodItem a foodItem that should be equal to the AddFood's foodItem
     */
    private void testValues(AddFood addFood, long timestamp, FoodItem foodItem){
        assertEquals(timestamp, addFood.getMessageTimestamp());
        assertEquals(foodItem, addFood.getFoodItem());
    }

    /**
     * Get the expected decoding String
     * @return decoding String
     */
    private String getCodeString(){
        return "FN1.0 " + expTimeStamp + " " + "ADD " + foodItem1.toCodeString() + "\n";
    }

    /**
     * Decode an AddFood coding
     * @return a FoodMessage(AddFood)
     * @throws UnsupportedEncodingException encoding is not correct
     * @throws FoodNetworkException Fails to decode this String into a FoodItem
     */
    private FoodMessage decode() throws UnsupportedEncodingException, FoodNetworkException, EOFException {
        return AddFood.decode(new MessageInput(new ByteArrayInputStream(this.getCodeString().getBytes(CHARSET))));
    }

    /**
     * Test if constructor works properly
     */
    @Test
    public void properConstructor(){
        testValues(addFood1, expTimeStamp, foodItem1);
    }

    /**
     * Test constructor with negative timestamp
     * @throws FoodNetworkException expected
     */
    @Test(expected = FoodNetworkException.class)
    public void testConstructorWithNegativeTimestamp() throws FoodNetworkException {
        new AddFood(-1L, foodItem1);
    }

    /**
     * Test constructor with null FoodItem
     * @throws FoodNetworkException expected
     */
    @Test(expected = FoodNetworkException.class)
    public void testConstructorWithNullFoodItem() throws FoodNetworkException{
        new AddFood(expTimeStamp, null);
    }

    /**
     * Test settler with null FoodItem
     * @throws FoodNetworkException expected
     */
    @Test(expected = FoodNetworkException.class)
    public void testAddFoodSetterWithNull() throws FoodNetworkException {
        addFood3.setFoodItem(null);
    }

    /**
     * Test decode method with negative timestamp
     * @throws UnsupportedEncodingException encoding is wrong
     * @throws FoodNetworkException expected
     */
    @Test(expected = FoodNetworkException.class)
    public void testNegativeDecode() throws UnsupportedEncodingException, FoodNetworkException, EOFException {
        expTimeStamp = -1;
        this.decode();
    }

    /**
     *Test hash and equals function with 2 AddFood that should be equals to one another
     */
    @Test
    public void testHashAndEqualsWithEquals(){
        assertEquals(addFood1.hashCode(), addFood2.hashCode());
        assertTrue(addFood1.equals(addFood2) && addFood2.equals(addFood1));
    }

    /**
     * Test equals will return false if two items are not the same
     */
    @Test
    public void testNotEquals(){
        assertNotEquals(addFood2, addFood3);
    }

}
