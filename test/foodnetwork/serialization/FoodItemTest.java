/************************************************
 *
 * Author: Dean He, Nicholas Hopper
 * Assignment: Assignment 1
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;
/*
proper format
Bytes count indicator + <sp> + (1+ ASCII character) + <sp> + (char MealType) +
    + ("^[0-9]+$") + <sp> + ("^[0-9]*\\.?[0-9]+$") + <sp>
 */

public class FoodItemTest {
    private FoodItem testItem;
    @Before
    public final void setUp() throws FoodNetworkException {
        testItem = new FoodItem("apple", MealType.Breakfast, 12L, "0.42");
    }
    @Test
    public void testForFieldConstructor() throws FoodNetworkException {
        new FoodItem("orange", MealType.Lunch, 12L, "0.42");
    }
    @Test
    public void testForInputStreamConstructor() throws FoodNetworkException, EOFException {
        InputStream in = new ByteArrayInputStream("4 pearS23 24.5 ".getBytes());
        MessageInput messageInput = new MessageInput(in);
        new FoodItem(messageInput);
    }
    @Test
    public void testForEncode() throws FoodNetworkException {
        FoodItem foodItem = new FoodItem("orange", MealType.Lunch, 12L, "0.42" );
        MessageOutput messageOutput = new MessageOutput(new ByteArrayOutputStream());
        foodItem.encode(messageOutput);
        assertEquals("6 orangeL12 0.42 ", messageOutput.toString());
    }

    @Test
    public void testForSetNameWithRightName() throws FoodNetworkException {
        testItem.setName("5 Kappa");
        assertEquals("5 Kappa", testItem.getName());
        testItem.setName("5 _1 2_");
        assertEquals("5 _1 2_", testItem.getName());
        testItem.setName("5      ");
        assertEquals("5      ", testItem.getName());
        testItem.setName("5 _ _-1");
        assertEquals("5 _ _-1", testItem.getName());
    }
    @Test(expected = FoodNetworkException.class)
    public void testNameWithNUllString() throws FoodNetworkException {
        testItem.setName(null);
        fail("Should throw invalid name");
    }
    @Test(expected = FoodNetworkException.class)
    public void testForSetNameWithEmptyString() throws FoodNetworkException{
        testItem.setName("");
    }
    @Test
    public void testForSetMealTypeWithRightType() throws FoodNetworkException {
        testItem.setMealType(MealType.Breakfast);
        assertEquals(MealType.Breakfast, testItem.getMealType());
    }

    @Test(expected = FoodNetworkException.class)
    public void testForSetMealTypeWithWrongType() throws FoodNetworkException {
        testItem.setMealType(null);
        fail("Should throw invalid MealType");
    }

    @Test
    public void testForSetCaloriesWithRightLong() throws FoodNetworkException {
        testItem.setCalories(100L);
        assertEquals(100L, testItem.getCalories());
    }
    @Test(expected = FoodNetworkException.class)
    public void testForSetCaloriesWithWrongLong() throws FoodNetworkException {
        testItem.setCalories(-100L);
        fail("Should throw invalid Calories");
    }

    @Test
    public void testForSetFatWithRightString() throws FoodNetworkException {
        testItem.setFat("23.4");
        assertEquals("23.4", testItem.getFat());
        testItem.setFat("023.4");
        assertEquals("023.4", testItem.getFat());
        testItem.setFat(".4");
        assertEquals(".4", testItem.getFat());
    }
    @Test(expected = FoodNetworkException.class)
    public void testForSetFatWithWrongString() throws FoodNetworkException {
        testItem.setFat("-2.4");
        fail("Should print invalid Fat String");
    }

    @Test
    public void testForEqualAndHashWithSameItem() throws FoodNetworkException {
        FoodItem foodItem1 = new FoodItem("orange", MealType.Lunch, 12L, "0.42");
        FoodItem foodItem2 = new FoodItem("orange", MealType.Lunch, 12L, "0.42");
        assertEquals(foodItem1, foodItem2);
        assertEquals(foodItem1.hashCode(), foodItem2.hashCode());
    }

    @Test
    public void testForEqualAndHashWithDifferentItem() throws FoodNetworkException {
        FoodItem foodItem1 = new FoodItem("orange", MealType.Lunch, 12L, "0.42");
        FoodItem foodItem2 = new FoodItem("apple", MealType.Dinner, 12L, "0.42");
        assertNotEquals(foodItem1, foodItem2);
        assertNotEquals(foodItem1.hashCode(), foodItem2.hashCode());
    }
    @Test
    public void testForHexFFCharacterAtEnd() throws  FoodNetworkException, IOException {
        byte[] part1 = ("5 Tofu").getBytes();
        byte part2 = (byte) 0xFF;
        byte[] part3 = ("B100 5.5 ").getBytes();
        byte[] input = new byte[part1.length + 1 + part3.length];

        System.arraycopy(part1, 0, input, 0, part1.length);
        input[part1.length] = part2;
        System.arraycopy(part3, 0, input, part1.length + 1, part3.length);

        testItem = new FoodItem(new MessageInput(new ByteArrayInputStream(input)));
    }
    @Test(expected = EOFException.class)
    public void testForEOF() throws FoodNetworkException, EOFException {
        byte [] input = "5 PlumsB".getBytes();
        testItem = new FoodItem(new MessageInput(new ByteArrayInputStream(input)));
    }
    @Test (expected=FoodNetworkException.class)
    public void testSetName() throws FoodNetworkException{
        testItem.setName("");
    }

    @Test (expected=FoodNetworkException.class)
    public void testSetCalories() throws FoodNetworkException{
        testItem.setCalories(-1L);
    }

    @Test (expected=FoodNetworkException.class)
    public void testSetMealType() throws FoodNetworkException{
        testItem.setMealType(MealType.getMealType('W'));
    }

    @Test (expected=FoodNetworkException.class)
    public void testSetFat() throws FoodNetworkException{
        testItem.setFat("2.");
    }
}