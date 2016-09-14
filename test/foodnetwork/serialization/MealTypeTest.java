/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 0
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization;

import org.junit.Test;

import static org.junit.Assert.*;

public class MealTypeTest {
    @Test
    public void testGetRightMealType() throws FoodNetworkException {
            assertEquals(MealType.Breakfast, MealType.getMealType('B'));
            assertEquals(MealType.Lunch, MealType.getMealType('L'));
            assertEquals(MealType.Dinner, MealType.getMealType('D'));
            assertEquals(MealType.Snack, MealType.getMealType('S'));
    }

    @Test(expected = FoodNetworkException.class)
    public void invalidCharType() throws FoodNetworkException {
        MealType.getMealType('T');
        MealType.getMealType('K');
        MealType.getMealType('G');
        fail("Get invalid char type did not throw");
    }

    @Test
    public void testMealTypeCode() throws FoodNetworkException {
        assertEquals('B', MealType.getMealType('B').getMealTypeCode());
        assertEquals('L', MealType.getMealType('L').getMealTypeCode());
        assertEquals('D', MealType.getMealType('D').getMealTypeCode());
        assertEquals('S', MealType.getMealType('S').getMealTypeCode());
    }
}
