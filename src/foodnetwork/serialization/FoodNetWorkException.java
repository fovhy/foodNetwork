package foodnetwork.serialization;
/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 1
 * Class: CSI 4321
 *
 ************************************************/

public class FoodNetWorkException extends Exception {
    //TODO: constructs food network exception
    public FoodNetWorkException(String message, Throwable cause){
        super(message, cause);
    }
    //TODO: constructs food network excpetion with null case
    public FoodNetWorkException(String message){
        super(message);
    }

}
