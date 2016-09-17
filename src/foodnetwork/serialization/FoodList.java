/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 0
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization;

import java.util.List;

/**
 * FoodList is a subclass of FoodMessage that stores when the list is being modified.
 * It can encode and decode it self, and return all the foodItems it contains.
 */
public class FoodList extends FoodMessage{
    /**
     * Construct a FoodList with a messageTimestamp, and modifiedTimestamp
     * @param messageTimestamp timestamp for message itself
     * @param modifiedTimestamp timestamp for when the list updated
     * @throws FoodNetworkException if long are null or negative
     */
    public FoodList(long messageTimestamp, long modifiedTimestamp) throws FoodNetworkException{

    }

    /**
     * Add a FoodItem to a foodList
     * @param foodItem the foodItem to be added
     */
    public void addFoodItem(FoodItem foodItem){

    }

    /**
     * Return a list of foodItem being stored
     * @return a list of foodItem
     */
    public List<FoodItem> getFoodItemList(){
        return null;
    }

    /**
     * Record when the list is being changed
     * @param modifiedTimestamp timestamp of when the list gets modified
     * @throws FoodNetworkException modifiedTimestamp is negative
     */
    public final void setModifiedTimestamp(long modifiedTimestamp) throws FoodNetworkException{
    }

    /**
     * Return when the foodITem list is being modified
     * @return modified timestamp
     */
    public long getModifiedTimestamp(){
        return 0L;
    }

    /**
     * Hash code function for this class
     * @return a hashcode
     */
    @Override
    public int hashCode(){
        return 0;
    }

    /**
     * Check if this foodList object equals to another object
     * @param obj another object
     * @return equal or not
     */
    @Override
    public boolean equals(Object obj){
        return false;
    }

    /**
     * Return foodList item in a human readable fashion
     * @return a string that represents foodList
     */
    @Override
    public String toString(){
        return null;
    }

    /**
     * Get the request message, LIST CT + all the food items
     * @return request message
     */
    @Override
    public String getRequest() {
        return null;
    }
}
