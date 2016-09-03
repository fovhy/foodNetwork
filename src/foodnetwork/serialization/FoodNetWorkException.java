package foodnetwork.serialization;
/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 1
 * Class: CSI 4321
 *
 ************************************************/

public class FoodNetWorkException extends Exception {
    public FoodNetWorkException(String message, Throwable cause){
        super(message, cause);
    }
    public FoodNetWorkException(String message){
        super(message);
    }

}
