/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 1
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization;

public class ErrorMessage extends FoodMessage{
    @Override
    public boolean equals(Object obj){
        return false;
    }
    public final String getErrorMessage(){
        return null;
    }
    public final String getRequest(){
        return null;
    }
    @Override
    public int hashCode(){
        return 0;
    }
    void setErrorMessage(String errorMessage) throws FoodNetworkException{
    }
}
