/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 1
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization;
public class FoodItem {
    /**
     * name is represented by a String with pattern "^[0-9]+$" followed by a space then followed by a 1+ ASCII
     * characters. For example 12 fatKingdom
     */
    private String name;
    /*  mealType represents the meal type for the food item, it could be four types*/
    private MealType mealType;
    /* Calories is a String that matches the pattern of "[0-9]*\\.?[0-9]+$" followed by a space.*/
    private String calories;
    /* fat is a String that matches the pattern of "[0-9]*\\.?[0-9]+$" followed by a space.*/
    private String fat;

    /**
     * Constructs food item with set values. The String it takes must follow certain patterns, or else it will throw
     * FoodNetWorkExceptions.
     * @param name the name for the food item
     * @param mealType the type of the food item.
     * @param calories how much the food item contains
     * @param fat how much fat the food item contains
     * @throws FoodNetWorkException
     */
    public FoodItem (String name, MealType mealType, long calories, String fat)
            throws FoodNetWorkException{
        this.name = name;
        this.mealType = mealType;
        this.calories = Long.toString(calories) + " ";
        this.fat = fat;
    }
    public FoodItem(MessageInput in) throws FoodNetWorkException{

    }
    public void encode(MessageOutput out) throws FoodNetWorkException{

    }
    @Override
    public boolean equals(Object obj){

        return false;
    }
    @Override
    public String toString(){
        return null;
    }
    public final String getName(){
        return null;
    }
    public final MealType getMealType(){
        return null;
    }



}
