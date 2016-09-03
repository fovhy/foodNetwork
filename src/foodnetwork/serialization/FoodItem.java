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
     * @throws FoodNetWorkException
     */
    public FoodItem(String name, MealType mealType, long calories, String fat)
            throws FoodNetWorkException {
        this.name = name;
        this.mealType = mealType;
        this.calories = calories;
        this.fat = fat;
    }

    /**
     * Construct a food item from an MessageInput object.
     *
     * @param in MessageInput object that should wraps around a InputStream
     * @throws FoodNetWorkException FoodItem fail to construct due do incomplete data stream
     */
    public FoodItem(MessageInput in) throws FoodNetWorkException {

    }

    /**
     * encode the FoodItem object into a string, and hook the string to an MessageOutput object.
     *
     * @param out an MessageOutput object that should wraps around a data outputStream
     * @throws FoodNetWorkException FoodItem has incomplete field
     */
    public void encode(MessageOutput out) throws FoodNetWorkException {

    }

    /**
     * encode the object into a String follow the grammar.
     * @return the encoded String
     */
    @Override
    public String toString(){
        return null;
    }

    /**
     * Get the name of the FoodItem
     *
     * @return name of the foodItem which should look like 5_apple
     */
    public String getName(){
        return null;
    }

    /**
     * Set the name of FoodITem to some String
     *
     * @param name a string with the pattern of (an unsigned int)count + (space) + (count amount >= 1)ASCII character
     * @throws FoodNetWorkException throws if the String name does not follow the pattern
     */
    public void setName(String name) throws FoodNetWorkException {

    }

    /**
     * Get the MealType of the FoodItem
     * @return MealType of the food item. Should be one of {Breakfast, Lunch, Dinner, Snack}
     */
    public final MealType getMealType(){
        return null;
    }

    /**
     * Set the mealType of some FoodItem into certain type
     *
     * @param mealType an enum mealType that represents the mealType going to be set
     * @throws FoodNetWorkException invalid MealType
     */
    public final void setMealType(MealType mealType) throws FoodNetWorkException {

    }

    /**
     * Get how many calories is in the FoodItem
     *
     * @return a long that represents the calories
     */
    public final long getCalories() {
        return 0L;
    }

    /**
     * Set the calories for this FoodItem
     *
     * @param calories a long that should be non-negative
     * @throws FoodNetWorkException if the calories is negative
     */
    public final void setCalories(long calories) throws FoodNetWorkException {

    }

    /**
     * Get how much fat is in the FoodItem
     *
     * @return a String that has the pattern of (unsigned double) + (sp)
     */
    public final String getFat() {
        return null;
    }

    /**
     * Set the fat of the FoodItem to specific amount
     *
     * @param fat a String that has the pattern of (unsigned double) + (sp)
     * @throws FoodNetWorkException if the String does not follow the pattern
     */
    public final void setFat(String fat) throws FoodNetWorkException {

    }

    /**
     * a hash function that returns a unique code
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return 0;
    }


    /**
     * Compare if 2 object is the same
     *
     * @param obj another FoodItem object
     * @return if the 2 object equals
     */
    @Override
    public boolean equals(Object obj) {
        return false;
    }
}

