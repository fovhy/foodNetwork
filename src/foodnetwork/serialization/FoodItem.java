/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 0
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization;

import java.io.EOFException;
import java.io.IOException;

/**
 * FoodItem is a class that can encode and decode all the attributes of a foodItem. In short,
 * it is a class that wraps around some data about a food, and will be used to pass message over
 * the foodNetwork
 * @version 1.1 9/13/2016
 * @author Dean He
 */
public class FoodItem {
    /**
     * name is represented by a String with pattern "^[0-9]+$" followed by a space then followed by a 1+ ASCII
     * characters. For example 12 fatKingdom
     */
    private String name;
    /**
     * MealType represents the meal type for the food item, it could be four types
     */
    private MealType mealType;
    /**
     * Calories is a long that stores the caloires of the meal
     */
    private long calories;
    /**
     * fat is a String that matches the pattern of "[0-9]*\\.?[0-9]+$" followed by a space.
     */
    private String fat;

    /**
     * Constructs food item with set values. The String it takes must follow certain patterns, or else it will throw
     * FoodNetWorkExceptions.
     *
     * @param name     the name for the food item
     * @param mealType the type of the food item.
     * @param calories how much the food item contains
     * @param fat      how much fat the food item contains
     * @throws FoodNetworkException if one of the fields does not follow the FoodItem pattern
     */
    public FoodItem(String name, MealType mealType, long calories, String fat)
            throws FoodNetworkException {
        setName(name);
        setMealType(mealType);
        setCalories(calories);
        setFat(fat);
    }

    /**
     * Construct a food item from an MessageInput object.
     *
     *
     * @param in MessageInput object that should wraps around a InputStream
     * @throws FoodNetworkException FoodItem fail to construct due do incomplete data stream
     * @throws EOFException if the stream prematurely ends
     */
    public FoodItem(MessageInput in) throws FoodNetworkException, EOFException {
        int foodCount = in.getNextUnsignedInt();
        setName(in.getNextFixedBytes(foodCount));               // set name as fixed bytes of counts
        char mealTypeCode = in.getNextFixedBytes(1).charAt(0);  // get next char in the inputStream
        setMealType(MealType.getMealType(mealTypeCode));
        try{
            setCalories(in.getNextUnsignedLong());
        }catch(FoodNetworkException e){
            throw new FoodNetworkException("Invalid calories", e);
        }
        try {
            setFat(in.getNextUnsignedDouble());
        } catch(FoodNetworkException e){
            throw new FoodNetworkException("Invalid fat. " + e.getMessage());
        }
    }


    /**
     * encode the FoodItem object into a string, and hook the string to an MessageOutput object.
     *
     * @param out an MessageOutput object that should wraps around a data outputStream
     * @throws FoodNetworkException FoodItem has incomplete field, stream closes unexpected
     */
    public void encode(MessageOutput out) throws FoodNetworkException {
        try {
            out.writeAndStore(toCodeString());
        } catch (IOException e) {
            throw new FoodNetworkException("OutputStream prematurely closes", e);
        }
    }

    /**
     * encode the object into a String follow the grammar.
     * @return the encoded String
     */
    public String toCodeString(){
        return name.length() + " " + name + mealType.getMealTypeCode() + calories + " " + fat + " ";
    }

    public String toString(){
        return "Name: " + name + "\n" +
                "MealType: " + mealType.getMealTypeCode() + "\n" +
                "Calories: " + calories + "\n" +
                "Fat: " + fat + "\n";
    }

    /**
     * Get the name of the FoodItem
     *
     * @return name of the foodItem which should look like 5_apple
     */
    public String getName(){
        return name;
    }

    /**
     * Set the name of FoodITem to some String
     *
     * @param aName a string with the pattern of (an unsigned int)count + (space) + one or
     *              more than 1 ASCII character
     * @throws FoodNetworkException throws if the String name does not follow the pattern
     */
    public void setName(String aName) throws FoodNetworkException {
        if(validateName(aName)){
            name = aName;
        }else{
            throw new FoodNetworkException(aName + " is a invalid name");
        }
    }

    /**
     * Get the MealType of the FoodItem
     * @return MealType of the food item. Should be one of {Breakfast, Lunch, Dinner, Snack}
     */
    public final MealType getMealType(){
        return mealType;
    }

    /**
     * Set the mealType of some FoodItem into certain type
     *
     * @param aMealType an enum mealType that represents the mealType going to be set
     * @throws FoodNetworkException invalid MealType
     */
    public final void setMealType(MealType aMealType) throws FoodNetworkException {
        if(validateMealType(aMealType)){
            mealType = aMealType;
        }else{
            throw new FoodNetworkException("Null MealType");
        }
    }

    /**
     * Get how many calories is in the FoodItem
     *
     * @return a long that represents the calories
     */
    public final long getCalories() {
        return calories;
    }

    /**
     * Set the calories for this FoodItem
     *
     * @param aCalories a long that should be non-negative
     * @throws FoodNetworkException if the calories is negative
     */
    public final void setCalories(long aCalories) throws FoodNetworkException {
        if(validateCaloires(aCalories)){
            calories = aCalories;
        }else{
            throw new FoodNetworkException("Negative calories");
        }
    }

    /**
     * Get how much fat is in the FoodItem
     *
     * @return a String that has the pattern of (unsigned double) + (sp)
     */
    public final String getFat() {
        return fat;
    }

    /**
     * Set the fat of the FoodItem to specific amount
     *
     * @param aFat a String that has the pattern of (unsigned double) + (sp)
     * @throws FoodNetworkException if the String does not follow the pattern
     */
    public final void setFat(String aFat) throws FoodNetworkException {
        if(validateFat(aFat)){
            fat = aFat;
        }else{
            throw new FoodNetworkException(aFat + " is not a valid fat");
        }
    }

    /**
     * a hash function that returns a unique code
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return 23 * mealType.hashCode() * name.hashCode() +
                (int)calories *  fat.hashCode() + 13;
    }


    /**
     * Compare if 2 object is the same
     *
     * @param obj another FoodItem object
     * @return true if the 2 object equals
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(obj == this){
            return true;
        }
        if(!(obj instanceof FoodItem)){
            return false;
        }
        boolean results = false;
        FoodItem testObj = (FoodItem) obj;
        if(testObj.name.equals(name) &&
                testObj.mealType.equals(mealType) &&
                (testObj.calories == calories) &&
                testObj.fat.equals(fat)){
            results = true;
        }
        return results;
    }

    /**
     * Check if the name is valid based on the pattern of name
     * @param aName a String represents a name for FoodItem
     * @return whether the name is valid
     */
    private boolean validateName(String aName){
        if(aName == null || "".equals(aName)){
            return false;
        }
        return true;
    }

    /**
     * Check if MealType is valid based on the MealTypes out there
     * @param aMealType a MealType
     * @return whether the MealType is valid
     */
    private boolean validateMealType(MealType aMealType){
        return aMealType != null;
    }

    /**
     * Check if Calories is a non negative long number
     * @param aCalories a long represents calories
     * @return whether calories is negative or not
     */
    private boolean validateCaloires(long aCalories){
        return aCalories >= 0;
    }

    /**
     * Check if fat String follows the pattern of fat
     * @param aFat a String represents fat
     * @return whether fat follows the pattern or not
     */
    private boolean validateFat(String aFat){
        if(aFat == null) {
            return false;
        }
        return aFat.matches("^[0-9]*\\.?[0-9]+$");
    }
}

