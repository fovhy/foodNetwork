/************************************************
 *
 * Author: Dean He, Nicholas Hopper
 * Assignment: Assignment 0
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

/**
 * FoodItemTest test the ability of foodItem to encode and decode from a byte stream of data.
 * @version 1.1
 * @author Dean he
 */
public class FoodItemTest {
    private FoodItem testItem;

    /**
     * Set a normal foodItem for later use in the test.
     * @throws FoodNetworkException if it fails to construct a complete correct foodItem
     */
    @Before
    public final void setUp() throws FoodNetworkException {
        testItem = new FoodItem("apple", MealType.Breakfast, 12L, "0.42");
    }

    /**
     * Test the constructor for foodItem
     * @throws FoodNetworkException throw if a attribute is not valid
     */
    @Test
    public void testForFieldConstructor() throws FoodNetworkException {
        new FoodItem("orange", MealType.Lunch, 12L, "0.42");
    }

    /**
     * Test for foodItem inputStreamConstructor
     * @throws FoodNetworkException if the bytes it reads cannot construct a complete foodItem
     * @throws EOFException premature closing of inpustream
     */
    @Test
    public void testForInputStreamConstructor() throws FoodNetworkException, EOFException {
        InputStream in = new ByteArrayInputStream("4 pearS23 24.5 ".getBytes());
        MessageInput messageInput = new MessageInput(in);
        new FoodItem(messageInput);
    }

    /**
     * Test for encoding a foodItem into a byte stream
     * @throws FoodNetworkException if there are incomplete fields inside of the foodItem
     */
    @Test
    public void testForEncode() throws FoodNetworkException {
        FoodItem foodItem = new FoodItem("orange", MealType.Lunch, 12L, "0.42" );
        MessageOutput messageOutput = new MessageOutput(new ByteArrayOutputStream());
        foodItem.encode(messageOutput);
        assertEquals("6 orangeL12 0.42 ", messageOutput.toString());
    }

    /**
     * test for setName with a valid name
     * @throws FoodNetworkException if the name is not valid
     */
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

    /**
     * Test setName with null string
     * @throws FoodNetworkException if the name is invalid
     */
    @Test(expected = FoodNetworkException.class)
    public void testNameWithNUllString() throws FoodNetworkException {
        testItem.setName(null);
        fail("Should throw invalid name");
    }

    /**
     * test setName using a empty string
     * @throws FoodNetworkException if the name is invalid
     */
    @Test(expected = FoodNetworkException.class)
    public void testForSetNameWithEmptyString() throws FoodNetworkException{
        testItem.setName("");
    }

    /**
     * test for setMealType with right type of meal
     * @throws FoodNetworkException if the mealType does not match
     */
    @Test
    public void testForSetMealTypeWithRightType() throws FoodNetworkException {
        testItem.setMealType(MealType.Breakfast);
        assertEquals(MealType.Breakfast, testItem.getMealType());
    }

    /**
     * test for setMealType with null
     * @throws FoodNetworkException invalid type of meal
     */
    @Test(expected = FoodNetworkException.class)
    public void testForSetMealTypeWithWrongType() throws FoodNetworkException {
        testItem.setMealType(null);
        fail("Should throw invalid MealType");
    }

    /**
     * set calories with correct calorie number
     * @throws FoodNetworkException if the calories is negative long
     */
    @Test
    public void testForSetCaloriesWithRightLong() throws FoodNetworkException {
        testItem.setCalories(100L);
        assertEquals(100L, testItem.getCalories());
    }

    /**
     * set calories with wrong calorie number
     * @throws FoodNetworkException if the calorie account is negatvie
     */
    @Test(expected = FoodNetworkException.class)
    public void testForSetCaloriesWithWrongLong() throws FoodNetworkException {
        testItem.setCalories(-100L);
        fail("Should throw invalid Calories");
    }

    /**
     * Test setFat with the right string
     * @throws FoodNetworkException if the string does not matches the fat regex pattern
     */
    @Test
    public void testForSetFatWithRightString() throws FoodNetworkException {
        testItem.setFat("23.4");
        assertEquals("23.4", testItem.getFat());
        testItem.setFat("023.4");
        assertEquals("023.4", testItem.getFat());
        testItem.setFat(".4");
        assertEquals(".4", testItem.getFat());
    }

    /**
     * Test setFat with invalid string
     * @throws FoodNetworkException
     */
    @Test(expected = FoodNetworkException.class)
    public void testForSetFatWithWrongString() throws FoodNetworkException {
        testItem.setFat("-2.4");
        fail("Should print invalid Fat String");
    }

    /**
     * Test for hash code using the same foodItem
     * @throws FoodNetworkException if they do not equals to one another
     */
    @Test
    public void testForEqualAndHashWithSameItem() throws FoodNetworkException {
        FoodItem foodItem1 = new FoodItem("orange", MealType.Lunch, 12L, "0.42");
        FoodItem foodItem2 = new FoodItem("orange", MealType.Lunch, 12L, "0.42");
        assertEquals(foodItem1, foodItem2);
        assertEquals(foodItem1.hashCode(), foodItem2.hashCode());
    }

    /**
     * Test for hash code using the different foodItem
     * @throws FoodNetworkException if they do not equals to one another
     */
    @Test
    public void testForEqualAndHashWithDifferentItem() throws FoodNetworkException {
        FoodItem foodItem1 = new FoodItem("orange", MealType.Lunch, 12L, "0.42");
        FoodItem foodItem2 = new FoodItem("apple", MealType.Dinner, 12L, "0.42");
        assertNotEquals(foodItem1, foodItem2);
        assertNotEquals(foodItem1.hashCode(), foodItem2.hashCode());
    }

    /**
     * Test for unexpected end for byte stream
     * @throws FoodNetworkException if the foodItem fails to construct
     * @throws IOException prematurely runs into the end of the stream
     */
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

    /**
     * Test for messageInput with insufficient amount of data
     * @throws FoodNetworkException fails to construct a foodItem from the messageInput stream
     * @throws EOFException if the stream of byte prematurelly reaches the end
     */
    @Test(expected = EOFException.class)
    public void testForEOF() throws FoodNetworkException, EOFException {
        byte [] input = "5 PlumsB".getBytes();
        testItem = new FoodItem(new MessageInput(new ByteArrayInputStream(input)));
    }


    /**
     * Test for setMealType with a valid mealType
     * @throws FoodNetworkException if the mealType is invalid
     */
    @Test (expected=FoodNetworkException.class)
    public void testSetMealType() throws FoodNetworkException{
        testItem.setMealType(MealType.getMealType('W'));
    }

    /**
     * Test for setFat with a invalid fat string
     * @throws FoodNetworkException the fat string is invalid
     */
    @Test (expected=FoodNetworkException.class)
    public void testSetFat() throws FoodNetworkException{
        testItem.setFat("2.");
    }
}