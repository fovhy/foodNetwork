/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 4
 * Class: CSI 4321
 *
 ************************************************/
package tefub.serialization;

import foodnetwork.serialization.FoodItem;
import foodnetwork.serialization.FoodNetworkException;
import foodnetwork.serialization.MealType;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Addition extends TeFubMessage{
    private FoodItem myFoodItem;             // the food item for addition methods
    private final long unsignedIntLimit = 4294967295L;
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
        myFoodItem = new FoodItem();
        try {
            myFoodItem.setFat("0");
        } catch (FoodNetworkException e) {
            // won't happen
        }
        setName(name);
        setMealType(mealType);
        setCalories(calories);
        code = 1;
    }
    public Addition(int msgId, DataInputStream in) throws IOException {
        super(msgId);
        code = 1;
        int length = in.readUnsignedByte();
        myFoodItem = new FoodItem();
        String name = ""; // init a name place holder
        for(int i = 0; i < length; i++){
            if(in.available() > 0){
                name += (char)in.readByte();
            }else{
                throw new IOException("Not enough bytes for name");
            }
        }
        setName(name);
        byte mealTypeCode = in.readByte();
        try {
            setMealType(MealType.getMealType((char) mealTypeCode));
        } catch (FoodNetworkException e) {
            throw new IllegalArgumentException("Wrong MealType code");
        }
        if(in.readByte() != 0){
            throw new IllegalArgumentException("No padding 0");
        }
        int ISIZE = Integer.SIZE/Byte.SIZE;
        byte[] caloriesByteArray = new byte[ISIZE];    // 4 bytes of raw data
        if(in.read(caloriesByteArray, 0, ISIZE) < ISIZE){
            throw new IOException("Insufficient bytes for calories");
        }
        long calories = EndianCoder.decodeUnsignedInt(caloriesByteArray, 0);
        setCalories(calories);
        if(in.available() > 0){
            throw new IOException("More bytes than expected");
        }
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
        if(name != null) {
            try {
                if (EndianCoder.checkForAscii(name)) {
                    myFoodItem.setName(name);
                } else {
                    throw new IllegalArgumentException("Name not in ASCII format");
                }
            } catch (FoodNetworkException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }else{
            throw new IllegalArgumentException("Null name");
        }
    }

    /**
     * Return the MealType of the foodItem
     * @return meal type
     */
    public final MealType getMealType(){
        return myFoodItem.getMealType();
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
    public final long getCalories(){
        return myFoodItem.getCalories();
    }

    /**
     * Set the calories of the FoodItem
     * @param calories calories
     * @throws IllegalArgumentException if validation fails
     */
    public final void setCalories(long calories) throws IllegalArgumentException{
        if(calories > unsignedIntLimit){
            throw new IllegalArgumentException("Too big of calories");
        }
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
        return super.hashCode() * 7 + myFoodItem.hashCode() * 117;
    }

    /**
     * Check if this Addition object equals to another object or not
     * @param obj another object
     * @return equals or not
     */
    @Override
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }
        if(obj == this){
            return true;
        }
        if(!(obj instanceof Addition)){
            return false;
        }
        if(!super.equals(obj)){
            return false;
        }
        boolean results = false;
        Addition testObj = (Addition) obj;
        if(this.hashCode()== testObj.hashCode() &&
                this.myFoodItem.equals(testObj.myFoodItem)){
            results = true;
        }
        return results;
    }
    /**
     * Get the data of addition message
     * @return the data of the TeFub Message
     */
    @Override
    public byte[] getData() throws IOException {
        int length = getName().length();
        byte[] lengthArray = new byte[1]; // one byte for length
        lengthArray[0] = (byte)length;
        byte[] NameArray = new byte[length];
        try {
            NameArray = getName().getBytes("ASCII");
        } catch (UnsupportedEncodingException e) {
            // won't happen
        }
        byte[] mealTypeArray = new byte[1];
        mealTypeArray[0] = (byte)getMealType().getMealTypeCode();
        byte[] paddingZeros = new byte[]{0};
        byte[] caloriesBytes = new byte[4];
        EndianCoder.encode4Bytes(caloriesBytes, (int)this.getCalories(), 0);
        byte[] lengthAndName = EndianCoder.concat(lengthArray, NameArray);
        byte[] mealTypeAndPaddingZero = EndianCoder.concat(mealTypeArray, paddingZeros);
        return EndianCoder.concat(EndianCoder.concat(lengthAndName, mealTypeAndPaddingZero),
                caloriesBytes);
    }

}
