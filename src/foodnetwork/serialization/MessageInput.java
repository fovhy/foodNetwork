/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 1
 * Class: CSI 4321
 *
 ************************************************/

package foodnetwork.serialization;

import java.io.InputStream;
import java.util.Scanner;

/**
 * MessageInput is a wrapper class around an Scanner. It functions like a scanner with the ability to deserialize
 * data in to a FoodItem object.
 * Warning: the design of this class is flawed. One InputStream should only associate with one MessageInput object.
 * If other object also uses the same InputStream will results in undefined behavior. It is also not thread safe.
 *
 * @author Dean He
 * @version 1.1 09/03/2016
 */
class MessageInput {
    /**
     * The scanner that will do the scanning and buffer handling
     */
    private Scanner MessageScanner;
    /**
     * The marker that marks how much data is being read already
     */
    private InputStream in = null;

    /**
     * Public constructor that associate one InputStream with this MessageInput object
     *
     * @param in The InputStream MessageInput has
     */
    public MessageInput(InputStream in) {
    }

    /**
     * Read a name from the InputStream.
     * @return the name String which should be in pattern of
     * @throws FoodNetWorkException the name String format is wrong
     */
    String getNextName() throws FoodNetWorkException {
        return null;
    }

    /**
     * Read a MealType from the InputStream
     * @return a MealType based on the char
     * @throws FoodNetWorkException if the char does not relate to any MealType
     */
    MealType getNextMealType() throws FoodNetWorkException {
        return null;
    }

    /**
     * Get the calories of the FoodItem from the InputStream
     * @return a String that represents calories amount
     * @throws FoodNetWorkException if the String format is not the format of calories
     */
    String getNextCalories() throws FoodNetWorkException {
        return null;
    }

    /**
     * Get the fat stat from the InputStream
     * @return a String that represents amount of fat in the FoodItem
     * @throws FoodNetWorkException if the String format is not the format of fat
     */
    String getNextFat() throws FoodNetWorkException {
        return null;
    }
}

