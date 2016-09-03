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

    public MessageOutput(OutputStream out) {
    }

    public void writeObject(FoodItem foodItem) throws FoodNetWorkException {

    }
}
