package foodnetwork.serialization;

import java.io.ObjectOutputStream;
import java.io.OutputStream;

/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 1
 * Class: CSI 4321
 *
 ************************************************/
public class MessageOutput {
    private ObjectOutputStream outStream;

    /**
     * Construct a MessageOutput object with an OutputStream object. The OutputStream will be used to
     * encode the FoodItem
     * @param out
     */
    public MessageOutput(OutputStream out) {
    }


    /**
     * Turns a foodItem object into a String and write it to the OutputStream
     * @param foodItem
     * @throws FoodNetWorkException
     */
    public void writeObject(FoodItem foodItem) throws FoodNetWorkException {

    }
}
