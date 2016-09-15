/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 0
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization;

import java.io.IOException;
import java.io.OutputStream;

public class MessageOutput {

    private OutputStream out;
    private String outputValue;
    /**
     * Construct a MessageOutput object with an OutputStream object. The OutputStream will be used to
     * encode the FoodItem
     * @param out hooks the output to an OutputStream
     */
    public MessageOutput(OutputStream out) {
        this.out = out;
    }


    /**
     * write byte array to output stream, in the same time store it outPutValue
     * @param output the byte array to write out
     * @throws IOException if outputStream closes unexpectedly
     */
    /*/**
     * Turns a foodItem object into a String and write it to the OutputStream
     * @param foodItem take a food item and encode it to a String of data
     * @throws FoodNetworkException if trying to write a null FoodItem
     * @throws IOException if outputStream closes
     */
    /*
    public void writeObject(FoodItem foodItem) throws FoodNetworkException {
        if(foodItem != null) {
            outputValue = foodItem.toString();
            try {
                out.write(outputValue.getBytes());
            } catch (IOException e) {
                throw new FoodNetworkException("OutputStream prematurely closes", e);
            }
        }else{
            throw new FoodNetworkException("Writing a null FoodItem");
        }
    }*/
    public void writeAndStore(String output) throws IOException {
        outputValue = output;
        out.write(output.getBytes("ASCII"));
    }

    /**
     * return what outputMessage just wrote out
     * @return String stored in the buffer
     */
    public String justWrote(){
        return outputValue;
    }
}
