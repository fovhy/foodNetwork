/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 0
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization.test;

import foodnetwork.serialization.FoodNetworkException;
import foodnetwork.serialization.MealType;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * MealTypeTest test if user can get correct mealType from right code and vice versa.
 * @version 1.1
 * @author Dean He
 */
public class MealTypeTest {
    /**
     * Test for getMealType with correct food codes
     * @throws FoodNetworkException if the food code is invalid
     */
    @Test
    public void testGetRightMealType() throws FoodNetworkException {
            Assert.assertEquals(MealType.Breakfast, MealType.getMealType('B'));
            assertEquals(MealType.Lunch, MealType.getMealType('L'));
            assertEquals(MealType.Dinner, MealType.getMealType('D'));
            assertEquals(MealType.Snack, MealType.getMealType('S'));
    }

    @Test(expected = FoodNetworkException.class)
    /**
     * Test for getMealType with wrong food codes
     * @throws FoodNetworkException if the food code is invalid
     */
    public void invalidCharType() throws FoodNetworkException {
        MealType.getMealType('T');
        MealType.getMealType('K');
        MealType.getMealType('G');
        fail("Get invalid char type did not throw");
    }

    /**
     * Test for mealCode actually matches with correct mealType
     * @throws FoodNetworkException if the food code is invalid
     */
    @Test
    public void testMealTypeCode() throws FoodNetworkException {
        assertEquals('B', MealType.getMealType('B').getMealTypeCode());
        assertEquals('L', MealType.getMealType('L').getMealTypeCode());
        assertEquals('D', MealType.getMealType('D').getMealTypeCode());
        assertEquals('S', MealType.getMealType('S').getMealTypeCode());
    }
}
