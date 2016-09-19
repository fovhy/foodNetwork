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
    List<FoodItem> foodItemList;
    private long modifiedTimestamp;
    /**
     * Construct a FoodList with a messageTimestamp, and modifiedTimestamp
     * @param messageTimestamp timestamp for message itself
     * @param modifiedTimestamp timestamp for when the list updated
     * @throws FoodNetworkException if long are null or negative
     */
    public FoodList(long messageTimestamp, long modifiedTimestamp) throws FoodNetworkException{
        setModifiedTimestamp(messageTimestamp);
        setModifiedTimestamp(modifiedTimestamp);
    }

    /**
     * Add a FoodItem to a foodList
     * @param foodItem the foodItem to be added
     */
    public void addFoodItem(FoodItem foodItem){
        foodItemList.add(foodItem);
    }

    /**
     * Return a list of foodItem being stored
     * @return a list of foodItem
     */
    public List<FoodItem> getFoodItemList(){
        return foodItemList;
    }

    /**
     * Record when the list is being changed
     * @param modifiedTimestamp timestamp of when the list gets modified
     * @throws FoodNetworkException modifiedTimestamp is negative
     */
    public final void setModifiedTimestamp(long modifiedTimestamp) throws FoodNetworkException{
        if(modifiedTimestamp < 0){
            throw new FoodNetworkException("Negative modifiedTimestamp");
        }
        this.modifiedTimestamp = modifiedTimestamp;
    }

    /**
     * Return when the foodITem list is being modified
     * @return modified timestamp
     */
    public long getModifiedTimestamp(){
        return modifiedTimestamp;
    }

    /**
     * Hash code function for this class
     * @return a hashcode
     */
    @Override
    public int hashCode(){
        int hash = super.hashCode() + new Long(modifiedTimestamp).hashCode() * 7;
        if(foodItemList != null){
            hash += foodItemList.hashCode() * 11;
        }
        return hash;
    }

    /**
     * Check if this foodList object equals to another object
     * @param obj another object
     * @return equal or not
     */
    @Override
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }
        if(obj == this){
            return true;
        }
        if(!(obj instanceof FoodList)){
            return false;
        }
        boolean results = false;
        FoodList testObj = (FoodList) obj;
        if(this.hashCode()== testObj.hashCode() &&
                this.timestamp == testObj.timestamp &&
                this.foodItemList.equals(testObj.foodItemList)){
            results = true;
        }
        return results;
    }

    /**
     * Return foodList item in a human readable fashion
     * @return a string that represents foodList
     */
    @Override
    public String toString(){
        String temp = super.toString();
        temp =  temp + "Modified time: " + modifiedTimestamp + "\n";
        for(int i = 0; i < foodItemList.size(); i++){
            temp = temp + "FoodItem " + i + "\n" + foodItemList.get(i).toString();
        }
        return temp;
    }

    /**
     * Get the request message, LIST CT + all the food items
     * @return request message
     */
    @Override
    public String getRequest() {
        String temp = "LIST " + modifiedTimestamp + foodItemList.size() + " ";
        for(FoodItem foodItem : foodItemList){
            temp += foodItem.toCodeString();
        }
        return temp;
    }
}
