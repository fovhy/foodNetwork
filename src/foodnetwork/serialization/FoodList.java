/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 0
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization;

import java.util.List;

public class FoodList extends FoodMessage{
    public FoodList(long messageTimestamp, long modifiedTimestamp) throws FoodNetworkException{

    }
    public void addFoodItem(FoodItem foodItem){

    }
    public List<FoodItem> getFoodItemList(){
        return null;
    }
    public final void setModifiedTimestamp(long modifiedTimestamp) throws FoodNetworkException{
    }
    public long getModifiedTimestamp(){
        return 0L;
    }
    @Override
    public int hashCode(){
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

    @Override
    public String getRequest() {
        return null;
    }
}
