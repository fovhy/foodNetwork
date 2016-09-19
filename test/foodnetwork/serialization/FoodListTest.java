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
import org.testng.annotations.Parameters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Parameterized test on FoodList
 */
@RunWith(Parameterized.class)
public class FoodListTest {
    private final String CHARSET = "ASCII";

    private String expDecode;
    private long expTimestamp;
    private long expModifiedTimestamp;
    private List<FoodItem> expFoodItemList;
    private FoodList expFoodList;

    /**
     * Parameters set up
     * @return parameters
     * @throws FoodNetworkException fails to construct foodItem
     */
    @Parameterized.Parameters
    public static Collection<Object[]> data() throws FoodNetworkException {
        List<FoodItem> foodItemList1 = new ArrayList<>();
        List<FoodItem> foodItemList2 = new ArrayList<>();
        List<FoodItem> foodItemList3 = new ArrayList<>();
        FoodItem item1 = new FoodItem("Apple", MealType.Breakfast, 120L, "0");
        FoodItem item2 = new FoodItem("Orange", MealType.Lunch, 10L, "1.2");
        FoodItem item3 = new FoodItem("Pickle", MealType.Snack, 123L, "0.42");
        foodItemList1.add(item1);
        foodItemList1.add(item2);
        foodItemList1.add(item3);
        foodItemList2.add(item2);
        foodItemList2.add(item3);
        foodItemList3.add(item1);
        foodItemList3.add(item3);
        String itemCodestring1 = "FN1.0 123 LIST 342 3 ";
        String itemCodestring2 = "FN1.0 13 LIST 0 2 ";
        String itemCodestring3 = "FN1.0 8 LIST 122 2 ";
        for(FoodItem foodItem : foodItemList1){
            itemCodestring1 += foodItem.toCodeString();
        }
        itemCodestring1 += "\n";
        for(FoodItem foodItem : foodItemList2){
            itemCodestring2 += foodItem.toCodeString();
        }
        itemCodestring2 += "\n";
        for(FoodItem foodItem : foodItemList3){
            itemCodestring3 += foodItem.toCodeString();
        }
        itemCodestring3 += "\n";
        ArrayList list = new ArrayList();
        list.add(new Object[]{itemCodestring1, 123L, 342L, foodItemList1});
        list.add(new Object[]{itemCodestring2, 13L, 0L, foodItemList2});
        list.add(new Object[]{itemCodestring3, 8L, 122L, foodItemList3});
        return list;
    }
    public FoodListTest(String code, long timestamp,
                        long modifiedTimestamp, List<FoodItem> list) throws FoodNetworkException {
        this.expDecode = code;
        this.expTimestamp = timestamp;
        this.expModifiedTimestamp = modifiedTimestamp;
        this.expFoodItemList = list;
        this.expFoodList = new FoodList(expTimestamp, expModifiedTimestamp);
        for(FoodItem foodItem : expFoodItemList) {
            expFoodList.addFoodItem(foodItem);
        }
    }

    /**
     * Check if the FoodList attributes are as expected, here it only checks both timestamps
     * @param foodlist the foodList to be checked
     */
    public void checkFoodList(FoodList foodlist){
        assertEquals(expTimestamp, foodlist.getMessageTimestamp());
        assertEquals(expModifiedTimestamp, foodlist.getModifiedTimestamp());
    }

    /**
     * Check the foodItem list inside the class is exacted or not
     * @param foodList the FoodList to be checked
     */
    public void checkFoodItemList(FoodList foodList){
        assertEquals(expFoodItemList, foodList.getFoodItemList());
    }

    /**
     * Test the constructor of the FoodList
     * @throws FoodNetworkException if it fails to construct a FoodList object
     */
    @Test
    public void testConstructorAndAdd() throws FoodNetworkException {
        checkFoodList(expFoodList);
    }

    /**
     * Test the decoding function of FoodMessage when the String indicates a FoodList item
     * @throws FoodNetworkException fail to decode the String
     * @throws UnsupportedEncodingException wrong encoding
     * @throws EOFException inputStream ends prematurally
     */
    @Test
    public void testDecode() throws FoodNetworkException, UnsupportedEncodingException, EOFException {
        MessageInput in = new MessageInput(new ByteArrayInputStream(expDecode.getBytes(CHARSET)));
        FoodList temp = (FoodList) FoodMessage.decode(in);
        checkFoodItemList(temp);
        checkFoodList(temp);
    }

    /**
     * Test the encoding function of FoodList class
     * @throws FoodNetworkException if the encoding fails
     * @throws UnsupportedEncodingException if wrong encoding
     */
    @Test
    public void testEncode() throws FoodNetworkException, UnsupportedEncodingException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        MessageOutput out = new MessageOutput(outStream);
        expFoodList.encode(out);
        assertArrayEquals(expDecode.getBytes(CHARSET), outStream.toByteArray());
    }

    /**
     * test if add FoodItem function works properly
     */
    @Test
    public void testAddItem() {
        checkFoodItemList(expFoodList);
    }

    /**
     * Test if set modifiedTimestamp and get modifiedTimestamp works properly
     * @throws FoodNetworkException if the timestamp is negative
     */
    @Test
    public void testGetModifiedTimestamp() throws FoodNetworkException {
        long timestamp = 20L;
        expFoodList.setModifiedTimestamp(timestamp);
        assertEquals(timestamp, expFoodList.getModifiedTimestamp());
    }

    /**
     * Test if the toString function actually returns a String or not
     */
    @Test
    public void testToString(){
        assertNotNull(expFoodList.toString());
    }

}