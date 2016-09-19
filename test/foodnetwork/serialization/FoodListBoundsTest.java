/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 1
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

/**
 * Test for bounding cases in FoodList
 */
public class FoodListBoundsTest {
    private static final String CHARSET = "ASCII";
    private List<FoodItem> foodItemList;
    private long expTimestamp = 1000L;
    private long expModifiedTimestamp = 100L;
    private long expListCount = 3;

    private FoodList foodList1;
    private FoodList foodList2;
    private FoodList foodList3;
    public FoodListBoundsTest() throws FoodNetworkException {
        try{
            foodItemList = new ArrayList<FoodItem>();
            FoodItem item1 = new FoodItem("Apple", MealType.Breakfast, 120L, "0");
            FoodItem item2 = new FoodItem("Orange", MealType.Lunch, 10L, "1.2");
            FoodItem item3 = new FoodItem("Pickle", MealType.Snack, 123L, "0.42");
            foodItemList.add(item1);
            foodItemList.add(item2);
            foodItemList.add(item3);
            foodList1 = new FoodList(expTimestamp, expModifiedTimestamp);
            foodList2 = new FoodList(expTimestamp, expModifiedTimestamp);
            foodList3 = new FoodList(expTimestamp + 12, expModifiedTimestamp + 12);
        }catch (FoodNetworkException e){
            throw new FoodNetworkException("Failed to set up Bounding test for FoodList", e);
        }
    }

    /**
     * Return a serialized String based on the expected value of the test
     * @return
     */
    private String getCodeString(){
        String temp = "FN1.0 " + expTimestamp + " LIST " + expModifiedTimestamp + " " + expListCount + " ";
        for(FoodItem foodItem : foodItemList){
            temp += foodItem.toCodeString();
        }
        temp += "\n";
        return temp;
    }

    /**
     * Test if the foodList contains all the same attributes as it should
     * @param foodList the FoodList object to be examined
     * @param timestamp expected messageTimestamp
     * @param modifiedTimestamp expected modifiedMessageTimestamp
     * @param list expected FoodItem list
     */
    private void testValues(FoodList foodList, long timestamp, long modifiedTimestamp, List<FoodItem> list){
        assertEquals(timestamp, foodList.getMessageTimestamp());
        assertEquals(modifiedTimestamp, foodList.getModifiedTimestamp());
        assertEquals(list, foodList.getFoodItemList());
    }

    /**
     * Make a FoodMessage out of the expected serialization String
     * @return a FoodMessage(FoodList) that is build on the information of the expected serialization String
     * @throws UnsupportedEncodingException wrong encoding
     * @throws FoodNetworkException if it fails construct a FoodMessage out of the encoding
     * @throws EOFException the stream ends prematurally
     */
    private FoodMessage decode() throws UnsupportedEncodingException, FoodNetworkException, EOFException {
        return FoodList.decode(new MessageInput(new ByteArrayInputStream(this.getCodeString().getBytes(CHARSET))));
    }

    /**
     *Test the constructor of the FoodList
     */
    @Test
    public void testConstructor(){
        testValues(foodList1, expTimestamp, expModifiedTimestamp, null);
    }

    /**
     * Test the constructor with negative modifiedTimestamp
     * @throws FoodNetworkException expected
     */
    @Test(expected = FoodNetworkException.class)
    public void testConstructorWithNegativeTimestamp() throws FoodNetworkException {
        new FoodList(10L, -1L);
    }

    /**
     * Test the setter of modifiedTimestamp with negative value
     * @throws FoodNetworkException expected
     */
    @Test(expected = FoodNetworkException.class)
    public void testModifiedTimestampSetterWithNegativeValue() throws FoodNetworkException {
        foodList3.setModifiedTimestamp(-1L);
    }

    /**
     * Test the decode function when the timestamp is negative
     * @throws UnsupportedEncodingException wrong encoding
     * @throws FoodNetworkException expected
     * @throws EOFException stream ends prematurally
     */
    @Test(expected = FoodNetworkException.class)
    public void testNegativeTimestampDecode() throws UnsupportedEncodingException, FoodNetworkException, EOFException {
        expTimestamp = -1;
        decode();
    }

    /**
     * Test decode function with negative modifiedTimestamp
     * @throws UnsupportedEncodingException wrong encoding
     * @throws FoodNetworkException expected
     * @throws EOFException stream ends prematurally
     */
    @Test(expected = FoodNetworkException.class)
    public void testNegativeModifiedTimeDecode() throws UnsupportedEncodingException, FoodNetworkException, EOFException {
        expModifiedTimestamp = -12;
        decode();
    }

    /**
     *Test hash and equals function with 2 AddFood that should be equals to one another
     */
    @Test
    public void testHashAndEqualsWithEquals(){
        assertEquals(foodList1.hashCode(), foodList2.hashCode());
        assertTrue(foodList1.equals(foodList2) && foodList2.equals(foodList1));
    }

    /**
     * Test equals will return false if two items are not the same
     */
    @Test
    public void testNotEquals(){
        assertNotEquals(foodList2, foodList3);
    }

}

