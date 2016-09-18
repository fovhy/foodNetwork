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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class FoodListTest {
    private final String CHARSET = "ASCII";

    private String expDecode;
    private long expTimestamp;
    private long expModifiedTimestamp;
    private List<FoodItem> expFoodItemList;
    private FoodList expFoodList;

    @Parameters
    public static Collection<Object[]> data() throws FoodNetworkException {
        List<FoodItem> foodItemList1 = new ArrayList<FoodItem>();
        List<FoodItem> foodItemList2 = new ArrayList<FoodItem>();
        List<FoodItem> foodItemList3 = new ArrayList<FoodItem>();
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
        itemCodestring2 += "\n";
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
        list.add(new Object[]{itemCodestring2, 0L, 13L, foodItemList2});
        list.add(new Object[]{itemCodestring3, 8L, 122L, foodItemList3});
        return list;
    }
    public FoodListTest(String code, long timestamp,
                        long modifiedTimestamp, List<FoodItem> list) throws FoodNetworkException {
        this.expDecode = code;
        this.expTimestamp = timestamp;
        this.expModifiedTimestamp = modifiedTimestamp;
        this.expFoodItemList = list;
        expFoodList = new FoodList(expTimestamp, expModifiedTimestamp);
        for(FoodItem foodItem : expFoodItemList) {
            expFoodList.addFoodItem(foodItem);
        }
    }
    public void checkFoodList(FoodList foodlist){
        assertEquals(expTimestamp, foodlist.getMessageTimestamp());
        assertEquals(expModifiedTimestamp, foodlist.getModifiedTimestamp());
    }
    public void checkFoodItemList(FoodList foodList){
        assertEquals(expFoodItemList, foodList.getFoodItemList());
    }
    @Test
    public void testConstructorAndAdd() throws FoodNetworkException {
        checkFoodList(expFoodList);
    }
    @Test
    public void testDecode() throws FoodNetworkException, UnsupportedEncodingException {
        MessageInput in = new MessageInput(new ByteArrayInputStream(expDecode.getBytes(CHARSET)));
        FoodList temp = (FoodList) FoodMessage.decode(in);
        checkFoodItemList(temp);
        checkFoodList(temp);
    }
    @Test
    public void testEncode() throws FoodNetworkException, UnsupportedEncodingException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        MessageOutput out = new MessageOutput(outStream);
        expFoodList.encode(out);
        assertArrayEquals(expDecode.getBytes(CHARSET), outStream.toByteArray());
    }
    @Test
    public void testAddItem() {
        checkFoodItemList(expFoodList);
    }
    @Test
    public void testGetModifiedTimestamp() throws FoodNetworkException {
        long timestamp = 20L;
        expFoodList.setModifiedTimestamp(timestamp);
        assertEquals(timestamp, expFoodList.getModifiedTimestamp());
    }
    @Test
    public void testToString(){
        assertNotNull(expFoodList.toString());
    }

}