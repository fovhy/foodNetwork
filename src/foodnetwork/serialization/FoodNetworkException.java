package foodnetwork.serialization;
/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 1
 * Class: CSI 4321
 *
 ************************************************/

/**
 * FoodNetworkException is a custom exception class for the use of FoodItem related exceptions. It happens when
 * the FoodItem's fields does not follow the pattern or the whole FoodItem is not complete.
 * @version 1.0 9/1/2016
 * @author Dean He
 */
 public class FoodNetworkException extends Exception {
    /**
     * FoodNetworkException constructor with a message and a cause.
     * @param message what to print out when Exception happens
     * @param cause the throwable which will be retrieved by Throwable.getCause() methods. It could be null
     */
    public FoodNetworkException(String message, Throwable cause){
        super(message, cause);
    }

    /**
     * FoodNetworkException constructor with only a String message.
     * @param message what to print out when Exception happens
     */
     FoodNetworkException(String message){
        super(message);
    }

}
