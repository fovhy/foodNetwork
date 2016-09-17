/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 1
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization;

/**
 * AddFood is a message class that stores what will be needed for adding a FoodItem into the server.
 */
public class AddFood extends FoodMessage{
    /**
     * constructor for AddFood, that stores a timestamp and foodItem
     * @param messageTimestamp timestamp when the message is created
     * @param foodItem  the foodItem to be added
     * @throws FoodNetworkException if the message is empty or null. Or if the foodItem is null.
     */
    public AddFood(long messageTimestamp, FoodItem foodItem) throws FoodNetworkException{

    }

    /**
     * Get the request message for adding a foodItem. ADD + sp +  FoodItem
     * @return add foodItem message
     */
    public String getRequest(){
        return null;
    }

    /**
     * Set the FoodItem inside the message
     * @param foodItem FoodItem to be stored
     * @throws FoodNetworkException if the foodItem is null
     */
    public final void setFoodItem(FoodItem foodItem) throws FoodNetworkException{

    }

    /**
     * Get what FoodItem is stored
     * @return FoodItem stored in this class
     */
    public final FoodItem getFoodItem(){
        return null;
    }

    /**
     * A hash code function for AddFood
     * @return a hash code
     */
    @Override
    public int hashCode (){
        return 0;
    }

    /**
     * Check if this AddFood object equals another object
     * @param obj another object
     * @return equals or not
     */
    @Override
    public boolean equals(Object obj){
        return false;
    }

    /**
     * Human readable presentation of AddFood class
     * @return the String that represents a AddFood object
     */
    @Override
    public String toString(){
        return null;
    }


}
