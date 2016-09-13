/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 1
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

public class MessageInputTest {
    private InputStream nullStream;

    @Before
    public void setUp() {
        nullStream = new ByteArrayInputStream("".getBytes());
    }

    @Test
    public void getNameWithRightName() throws FoodNetworkException, EOFException {
        InputStream goodStream = new ByteArrayInputStream("5 Apple".getBytes());
        MessageInput messageInput = new MessageInput(goodStream);
        assertEquals("5 Apple", messageInput.getNextName());
    }

    @Test(expected = EOFException.class)
    public void getNameWithIncompleteName() throws FoodNetworkException, EOFException {
        InputStream badStream = new ByteArrayInputStream("5".getBytes());
        MessageInput messageInput = new MessageInput(badStream);
        messageInput.getNextName();
        fail("Should throw non valid MealType");
    }

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

    @Test(expected = FoodNetworkException.class)
    public void getMealTypeWithInvalidChar() throws FoodNetworkException, EOFException {
        InputStream badStream = new ByteArrayInputStream("K".getBytes());
        MessageInput messageInput = new MessageInput(badStream);
        messageInput.getNextMealType();
        fail("Invalid MealType should be thrown");
    }

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

    @Test(expected = EOFException.class)
    public void getCaloriesWithNoSpace() throws FoodNetworkException, EOFException {
        InputStream badStream = new ByteArrayInputStream("132".getBytes());
        MessageInput messageInput = new MessageInput(badStream);
        messageInput.getNextCalories();
        fail("Should throw invalid calories String");
    }

    @Test(expected = FoodNetworkException.class)
    public void getCaloriesWithWrongPattern() throws FoodNetworkException, EOFException {
        InputStream badStream = new ByteArrayInputStream("132. ".getBytes());
        MessageInput messageInput = new MessageInput(badStream);
        messageInput.getNextCalories();
        fail("Should throw invalid calories String");
    }

    @Test
    public void getNextFatWithRightFatString() throws FoodNetworkException, EOFException {
        InputStream goodStream1 = new ByteArrayInputStream("132 ".getBytes());
        InputStream goodStream2 = new ByteArrayInputStream("0.2 ".getBytes());
        InputStream goodStream3 = new ByteArrayInputStream("00123.451 ".getBytes());

        MessageInput messageInput1 = new MessageInput(goodStream1);
        MessageInput messageInput2 = new MessageInput(goodStream2);
        MessageInput messageInput3 = new MessageInput(goodStream3);

        assertEquals("132 ", messageInput1.getNextFat());
        assertEquals("0.2 ", messageInput2.getNextFat());
        assertEquals("00123.451 ", messageInput3.getNextFat());
    }

    @Test(expected = EOFException.class)
    public void getNextFatWithNoSpace() throws FoodNetworkException, EOFException {
        InputStream badStream = new ByteArrayInputStream("23.0".getBytes());
        MessageInput messageInput = new MessageInput(badStream);
        messageInput.getNextFat();
        fail("should throw invalid double");
    }

    @Test(expected = FoodNetworkException.class)
    public void getNextFatWithWrongPattern() throws FoodNetworkException, EOFException {
        InputStream badStream = new ByteArrayInputStream("23.45.0 ".getBytes());
        MessageInput messageInput = new MessageInput(badStream);
        messageInput.getNextFat();
        fail("should throw invalid double");
    }

    @Test
    public void takeOneWholeFoodItem() throws FoodNetworkException, EOFException {
        InputStream goodStream = new ByteArrayInputStream("2 YwL002 011.2 ".getBytes());
        MessageInput messageInput = new MessageInput(goodStream);
        assertEquals("2 Yw", messageInput.getNextName());
        assertEquals(MealType.Lunch, messageInput.getNextMealType());
        assertEquals(2L, messageInput.getNextCalories());
        assertEquals("011.2 ", messageInput.getNextFat());
    }

    @Test
    public void takeTwoWholeFoodItem() throws FoodNetworkException, EOFException {
        InputStream goodStream1 = new ByteArrayInputStream("2 YwL002 011.2 5 appleB123 23.4 ".getBytes());
        MessageInput messageInput1 = new MessageInput(goodStream1);

        assertEquals("2 Yw", messageInput1.getNextName());
        assertEquals(MealType.Lunch, messageInput1.getNextMealType());
        assertEquals(2L, messageInput1.getNextCalories());
        assertEquals("011.2 ", messageInput1.getNextFat());

        assertEquals("5 apple", messageInput1.getNextName());
        assertEquals(MealType.Breakfast, messageInput1.getNextMealType());
        assertEquals(123L, messageInput1.getNextCalories());
        assertEquals("23.4 ", messageInput1.getNextFat());
    }

}

