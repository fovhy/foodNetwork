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
     * The buffer that stores data from the InputStream
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

    String getNextName() throws FoodNetWorkException {
        return null;
    }

    String getNextMealType() throws FoodNetWorkException {
        return null;
    }

    String getNextCalories() throws FoodNetWorkException {
        return null;
    }

    String getNextFat() throws FoodNetWorkException {
        return null;
    }
}

