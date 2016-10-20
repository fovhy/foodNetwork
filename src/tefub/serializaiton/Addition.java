/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 4
 * Class: CSI 4321
 *
 ************************************************/
package tefub.serializaiton;

import foodnetwork.serialization.FoodItem;
import foodnetwork.serialization.FoodNetworkException;
import foodnetwork.serialization.MealType;

import java.io.DataInput;
import java.io.DataInputStream;

public class Addition extends TeFubMessage{
    private FoodItem myFoodItem;             // the food item for addition methods
    /**
     * Construct an Addition TeFubMessage
     * @param msgId message ID of the message
     * @param name name of the FoodItem
     * @param mealType mealType of the foodItem
     * @param calories calories of the foodItem
     * @throws IllegalArgumentException if the Message ID is out of range
     */
    public Addition(int msgId,
                    String name,
                    MealType mealType,
                    long calories)
            throws IllegalArgumentException{
        super(msgId);
        try {
            myFoodItem = new FoodItem(name, mealType, calories, "0");
        } catch (FoodNetworkException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        code = 1;
    }
    public Addition(int msgId, DataInputStream in){
        super(msgId);
        code = 1;
    }
    /**
     * Return the Addition in a human readable form
     * @return human readable String
     */
    @Override
    public String toString(){
        return super.toString() + myFoodItem.toString();
    }

    /**
     * Get the name of the FoodItem
     * @return name
     */
    public final String getName(){
        return myFoodItem.getName();
    }

    /**
     * Set name of the FoodItem
     * @param name new name
     * @throws IllegalArgumentException if validation fails
     */
    public void setName(String name) throws IllegalArgumentException{
        try {
            myFoodItem.setName(name);
        } catch (FoodNetworkException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Return the MealType of the foodItem
     * @return meal type
     */
    public final MealType getMealType(){
        return null;
    }

    /**
     * Set meal type
     * @param mealType meal type
     * @throws IllegalArgumentException if validation fails
     */
    public void setMealType(MealType mealType) throws IllegalArgumentException{
        try {
            myFoodItem.setMealType(mealType);
        } catch (FoodNetworkException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Get the calories of the foodItem
     * @return calories
     */
    public final long getCaloires(){
        return myFoodItem.getCalories();
    }

    /**
     * Set the calories of the FoodItem
     * @param calories calories
     * @throws IllegalArgumentException if validation fails
     */
    public final void setCalories(long calories) throws IllegalArgumentException{
        try {
            myFoodItem.setCalories(calories);
        } catch (FoodNetworkException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    /**
     * The hash function for Addition class
     * @return hash code
     */
    @Override
    public int hashCode(){
        return 0;
    }

    /**
     * Check if this Addition object equals to another object or not
     * @param obj another object
     * @return equals or not
     */
    @Override
    public boolean equals(Object obj){
        return false;
    }
    /**
     * Get the data of message
     * @return the data of the TeFub Message
     */
    public byte[] getData(){
        return null;
    }

}
