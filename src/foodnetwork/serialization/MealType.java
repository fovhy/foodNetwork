/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 1
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization;

/**
 * MealType
 * @version 1.0
 * 9/1/2016
 * @author Dean He
 */
public enum MealType {
    Breakfast,
    Lunch,
    Dinner,
    Snack;

    /**
     * A static method that returns a MealType based on the type code(char). If the type code does not
     * match any of the meal type. It will throw a FoodNetWorkException.
     * @param code a char that is supposed to be one of {'B', 'L', 'D', 'B'}
     * @return a type of Meal
     * @throws FoodNetWorkException if code is null or fails validate
     */
    public static MealType getMealType(char code) throws FoodNetWorkException{
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
                throw new FoodNetWorkException(code + " is not a valid type code for meal.");
        }
        return mealTypeToReturn;
    }
    /**
     * This method returns a char that represents the MealType of this object.
     * @return a char that represents a MealType. One of {'B', 'L', 'D', 'S'}.
     */
    public char getMealTypeCode(){
        /*Because the interface does not allow public constructor, null case is impossible*/
        char MealTypeCode = '\u0000';
        switch(this){
            case Breakfast:
                MealTypeCode = 'B';
                break;
            case Lunch:
                MealTypeCode = 'L';
                break;
            case Dinner:
                MealTypeCode = 'D';
                break;
            case Snack:
                MealTypeCode = 'S';
                break;
        }
        return MealTypeCode;
    }
}
