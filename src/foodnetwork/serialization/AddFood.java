/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 1
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization;

public class AddFood extends FoodMessage{
    public AddFood(long messageTimestamp, FoodItem foodItem) throws FoodNetworkException{

    }
    public String getRequest(){
        return null;
    }

    public final void setFoodItem(FoodItem foodItem) throws FoodNetworkException{

    }

    public final FoodItem getFoodItem(){
        return null;
    }
    @Override
    public int hashCode (){
        return 0;
    }
    @Override
    public boolean equals(Object obj){
        return false;
    }
    @Override
    public String toString(){
        return null;
    }


}
