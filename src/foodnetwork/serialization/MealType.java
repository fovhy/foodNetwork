/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 0
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization;

/**
 * classname MealType
 * @version 1.0
 * 9/1/2016
 * @author Dean He
 */
public enum MealType {
    Breakfast('B'),
    Lunch('L'),
    Dinner('D'),
    Snack('S');

    private char type;    // type of the meal in char form

    /** A private constructor that store MealType as char.
     * @param type a MealType represented by a char
     */
    private MealType(char type){
        this.type = type;
    }
    /**
     * A static method that returns a MealType based on the type code(char). If the type code does not
     * match any of the meal type. It will throw a FoodNetworkException.
     * @param code a char that is supposed to be one of {'B', 'L', 'D', 'B'}
     * @return a type of Meal
     * @throws FoodNetworkException if code is null or fails validate
     */
    public static MealType getMealType(char code) throws FoodNetworkException {
        MealType mealTypeToReturn;
        switch(code){
            case 'B':
                mealTypeToReturn = Breakfast;
                break;
            case 'L':
                mealTypeToReturn = Lunch;
                break;
            case 'D':
                mealTypeToReturn = Dinner;
                break;
            case 'S':
                mealTypeToReturn = Snack;
                break;
            default:
                throw new FoodNetworkException(code + " is not a valid type code for meal.");
        }
        return mealTypeToReturn;
    }
    /**
     * This method returns a char that represents the MealType of this object.
     * @return a char that represents a MealType. One of {'B', 'L', 'D', 'S'}.
     */
    public char getMealTypeCode(){
        return type;
    }

}
