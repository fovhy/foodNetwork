/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 0
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.InputStream;

import static org.junit.Assert.*;
/*
proper format
Bytes count indicator + <sp> + (1+ ASCII character) + <sp> + (char MealType) +
("^[0-9]+$") + <sp> + ("^[0-9]*\\.?[0-9]+$") + <sp>
*/

/**
 * Test for MessageInput class
 * @version 1.1 9/14/2016
 * @author Dean He
 */
public class MessageInputTest {
    private InputStream nullStream;

    /**
     * Set up an empty MessageInput that wraps around an empty inputstream
     */
    @Before
    public void setUp() {
        nullStream = new ByteArrayInputStream("".getBytes());
    }

    /**
     * Test for getName function with the right name string.
     * @throws FoodNetworkException if name string is invalid
     * @throws EOFException if InputStream closes prematurely
     */
    @Test
    public void getNameWithRightName() throws FoodNetworkException, EOFException {
        InputStream goodStream = new ByteArrayInputStream("5 Apple".getBytes());
        MessageInput messageInput = new MessageInput(goodStream);
        assertEquals("Apple", messageInput.getNextName());
    }

    /**
     * Test getName with a wrong name. It should throw a foodNetworkException.
     * @throws FoodNetworkException if the name is invalid
     * @throws EOFException if InputStream closes prematurely
     */
    @Test(expected = EOFException.class)
    public void getNameWithIncompleteName() throws FoodNetworkException, EOFException {
        InputStream badStream = new ByteArrayInputStream("5".getBytes());
        MessageInput messageInput = new MessageInput(badStream);
        messageInput.getNextName();
        fail("Should throw non valid MealType");
    }

    /**
     * Test getMealType with correct mealType string(char)
     * @throws FoodNetworkException if the string cannot turn to a MealType
     * @throws EOFException InputStream closes prematurely
     */
    @Test
    public void getMealTypeWithRightType() throws FoodNetworkException, EOFException {
        InputStream goodStream1 = new ByteArrayInputStream("B".getBytes());
        InputStream goodStream2 = new ByteArrayInputStream("L".getBytes());
        InputStream goodStream3 = new ByteArrayInputStream("D".getBytes());
        InputStream goodStream4 = new ByteArrayInputStream("S".getBytes());

        MessageInput messageInput1 = new MessageInput(goodStream1);
        MessageInput messageInput2 = new MessageInput(goodStream2);
        MessageInput messageInput3 = new MessageInput(goodStream3);
        MessageInput messageInput4 = new MessageInput(goodStream4);

        assertEquals(MealType.Breakfast, messageInput1.getNextMealType());
        assertEquals(MealType.Lunch, messageInput2.getNextMealType());
        assertEquals(MealType.Dinner, messageInput3.getNextMealType());
        assertEquals(MealType.Snack, messageInput4.getNextMealType());
    }

    /**
     * Test getMealyType with invalid character
     * @throws FoodNetworkException if MessageInput cannot construct a mealType out of the character
     * @throws EOFException if InputStream prematurely closes.
     */
    @Test(expected = FoodNetworkException.class)
    public void getMealTypeWithInvalidChar() throws FoodNetworkException, EOFException {
        InputStream badStream = new ByteArrayInputStream("K".getBytes());
        MessageInput messageInput = new MessageInput(badStream);
        messageInput.getNextMealType();
        fail("Invalid MealType should be thrown");
    }

    /**
     * Test getCalories with invalid calories string
     * @throws FoodNetworkException if calories string is invalid
     * @throws EOFException if InputStream prematurely closes.
     */
    @Test
    public void getCaloriesWithRightCaloriesString() throws FoodNetworkException, EOFException {
        InputStream goodStream1 = new ByteArrayInputStream("132 ".getBytes());
        InputStream goodStream2 = new ByteArrayInputStream("0 ".getBytes());
        InputStream goodStream3 = new ByteArrayInputStream("0123 ".getBytes());

        MessageInput messageInput1 = new MessageInput(goodStream1);
        MessageInput messageInput2 = new MessageInput(goodStream2);
        MessageInput messageInput3 = new MessageInput(goodStream3);

        assertEquals(132L, messageInput1.getNextCalories());
        assertEquals(0L, messageInput2.getNextCalories());
        assertEquals(123L, messageInput3.getNextCalories());
    }

    /**
     * Test getCalories with a calories that has no space in the end
     * @throws FoodNetworkException if calories string is invalid
     * @throws EOFException if InputStream prematurely closes.
     */
    @Test(expected = EOFException.class)
    public void getCaloriesWithNoSpace() throws FoodNetworkException, EOFException {
        InputStream badStream = new ByteArrayInputStream("132".getBytes());
        MessageInput messageInput = new MessageInput(badStream);
        messageInput.getNextCalories();
        fail("Should throw invalid calories String");
    }
    /**
     * Test getCalories with invalid string pattern
     * @throws FoodNetworkException if calories string pattern is invalid
     * @throws EOFException if InputStream prematurely closes.
     */

    @Test(expected = FoodNetworkException.class)
    public void getCaloriesWithWrongPattern() throws FoodNetworkException, EOFException {
        InputStream badStream = new ByteArrayInputStream("132. ".getBytes());
        MessageInput messageInput = new MessageInput(badStream);
        messageInput.getNextCalories();
        fail("Should throw invalid calories String");
    }
    /**
     * Test getFat with correct fat string
     * @throws FoodNetworkException if fat string pattern does not match with correct fat pattern
     * @throws EOFException if InputStream prematurely closes.
     */

    @Test
    public void getNextFatWithRightFatString() throws FoodNetworkException, EOFException {
        InputStream goodStream1 = new ByteArrayInputStream("132 ".getBytes());
        InputStream goodStream2 = new ByteArrayInputStream("0.2 ".getBytes());
        InputStream goodStream3 = new ByteArrayInputStream("00123.451 ".getBytes());

        MessageInput messageInput1 = new MessageInput(goodStream1);
        MessageInput messageInput2 = new MessageInput(goodStream2);
        MessageInput messageInput3 = new MessageInput(goodStream3);

        assertEquals("132", messageInput1.getNextFat());
        assertEquals("0.2", messageInput2.getNextFat());
        assertEquals("00123.451", messageInput3.getNextFat());
    }
    /**
     * Test getFat with invalid character
     * @throws FoodNetworkException if fat string pattern does not match with correct fat pattern
     * @throws EOFException if InputStream prematurely closes.
     */

    @Test(expected = EOFException.class)
    public void getNextFatWithNoSpace() throws FoodNetworkException, EOFException {
        InputStream badStream = new ByteArrayInputStream("23.0".getBytes());
        MessageInput messageInput = new MessageInput(badStream);
        messageInput.getNextFat();
        fail("should throw invalid double");
    }

    /**
     * Test getNextFat with invalid fat String
     * @throws FoodNetworkException if fat string pattern does not match with correct fat pattern
     * @throws EOFException if InputStream prematurely closes.
     */
    @Test(expected = FoodNetworkException.class)
    public void getNextFatWithWrongPattern() throws FoodNetworkException, EOFException {
        InputStream badStream = new ByteArrayInputStream("23.45.0 ".getBytes());
        MessageInput messageInput = new MessageInput(badStream);
        messageInput.getNextFat();
        fail("should throw invalid double");
    }

    /**
     * Construct a full foodItem with correct foodItem string
     * @throws FoodNetworkException if foodItem fails to construct
     * @throws EOFException if InputStream prematurely closes.
     */
    @Test
    public void takeOneWholeFoodItem() throws FoodNetworkException, EOFException {
        InputStream goodStream = new ByteArrayInputStream("2 YwL002 011.2 ".getBytes());
        MessageInput messageInput = new MessageInput(goodStream);
        assertEquals("Yw", messageInput.getNextName());
        assertEquals(MealType.Lunch, messageInput.getNextMealType());
        assertEquals(2L, messageInput.getNextCalories());
        assertEquals("011.2", messageInput.getNextFat());
    }

    /**
     * Construct two full foodItem with correct foodItem string
     * @throws FoodNetworkException if foodItem fails to construct
     * @throws EOFException if InputStream prematurely closes.
     */
    @Test
    public void takeTwoWholeFoodItem() throws FoodNetworkException, EOFException {
        InputStream goodStream1 = new ByteArrayInputStream("2 YwL002 011.2 5 appleB123 23.4 ".getBytes());
        MessageInput messageInput1 = new MessageInput(goodStream1);

        assertEquals("Yw", messageInput1.getNextName());
        assertEquals(MealType.Lunch, messageInput1.getNextMealType());
        assertEquals(2L, messageInput1.getNextCalories());
        assertEquals("011.2", messageInput1.getNextFat());

        assertEquals("apple", messageInput1.getNextName());
        assertEquals(MealType.Breakfast, messageInput1.getNextMealType());
        assertEquals(123L, messageInput1.getNextCalories());
        assertEquals("23.4", messageInput1.getNextFat());
    }

}

