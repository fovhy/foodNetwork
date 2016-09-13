/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 1
 * Class: CSI 4321
 *
 ************************************************/

package foodnetwork.serialization;

import java.io.EOFException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * MessageInput is a wrapper class around an Scanner. It functions like a scanner with the ability to deserialize
 * data in to a FoodItem object.
 * Warning: the design of this class is flawed. One InputStream should only associate with one MessageInput object.
 * If other object also uses the same InputStream will results in undefined behavior. It is also not thread safe. It
 *  also will not take things too big. Like a double or Long with too many digits.
 *
 * @author Dean He
 * @version 1.1 09/03/2016
 */
public class MessageInput {
    /**
     * The scanner that will do the scanning and buffer handling
     */
    private Scanner messageScanner;

    /**
     * Public constructor that associate one InputStream with this MessageInput object
     *
     * @param in The InputStream MessageInput has
     */
    public MessageInput(InputStream in) {
        messageScanner = new Scanner(in);
    }

    /**
     * Read a name from the InputStream.
     * @return the name String which should be in pattern of
     * @throws FoodNetworkException the name String format is wrong
     * @throws EOFException if stream ends prematurely
     */
    String getNextName() throws FoodNetworkException, EOFException {
        String UN = "[0-9]+";
        int count;
        if(messageScanner.hasNext(UN)){
            count = Integer.parseUnsignedInt(messageScanner.next(UN));
        }else{
            throw new FoodNetworkException("Invalid Count");
        }
        char space;
        space = getNextSpace();
        messageScanner.useDelimiter("");
        char[] characterList = new char[count];
        for(int i = 0; i < count; i++){
            if(messageScanner.hasNext(".")){
                characterList[i] = messageScanner.next(".").charAt(0);
            }else{
                throw new FoodNetworkException("Invalid Name");
            }
        }
        messageScanner.useDelimiter(" ");
        return String.valueOf(characterList);
    }

    /**
     * Read a MealType from the InputStream
     * @return a MealType based on the char
     * @throws FoodNetworkException if the char does not relate to any MealType
     * @throws EOFException stream ends prematurely
     */
    MealType getNextMealType() throws FoodNetworkException, EOFException {
        char type;
        messageScanner.useDelimiter("");
        if(messageScanner.hasNext(".")){
            type = messageScanner.next(".").charAt(0);
        }else{
            throw new EOFException("premature stream ending when reading for MealType");
        }
        messageScanner.useDelimiter(" ");
        return MealType.getMealType(type);
    }

    /**
     * Get the calories of the FoodItem from the InputStream
     * Warning: Do not put more digits than what a Java long can handle. It will throw an parse exception and
     * it is not handled here.
     * @return a String that represents calories amount
     * @throws FoodNetworkException if the String format is not the format of calories
     * @throws EOFException if stream prematurely ends
     */
    long getNextCalories() throws FoodNetworkException, EOFException {
        String unsignedInt = "[0-9]+";
        long calories = -1L;
        if(!messageScanner.hasNext()){
            throw new EOFException("Premature stream ending when trying to read for Calories");
        }
        if(messageScanner.hasNext(unsignedInt)){
            try {
                calories = Long.parseLong(messageScanner.next(unsignedInt));
            }catch(NumberFormatException e){
                throw new FoodNetworkException("Too long of stream for long", e);
            }
        }else{
            throw new FoodNetworkException("Invalid unsigned int pattern");
        }
        getNextSpace();
        return calories;
    }

    /**
     * Get the fat stat from the InputStream
     * @return a String that represents amount of fat in the FoodItem
     * @throws FoodNetworkException if the String format is not the format of fat
     * @throws EOFException if stream ends prematurely
     */
    String getNextFat() throws FoodNetworkException, EOFException {
        if(!messageScanner.hasNext()){
            throw new EOFException("Stream prematurely ends when reading for next fat");
        }
        String unsignedDouble = "[0-9]*\\.?[0-9]+";
        String fat;
        if(messageScanner.hasNext(unsignedDouble)){
            fat = messageScanner.next(unsignedDouble);
        }else{
            throw new FoodNetworkException("Invalid fat pattern");
        }
        getNextSpace();
        return fat;
    }

    /**
     * Check if there is one space after the reading position
     * @return a space char
     * @throws FoodNetworkException throws if the byte next is not a space or there is no next byte
     * @throws EOFException if the stream ends up without a space
     */
    char getNextSpace() throws FoodNetworkException, EOFException {
        char space;
        messageScanner.useDelimiter("");
        if(messageScanner.hasNext(".")){
            space = messageScanner.next(".").charAt(0);
            if(space != ' '){
                throw new FoodNetworkException("Expecting a space");
            }
        }else{
            throw new EOFException("Premature string termination, expecting a space");
        }
        messageScanner.useDelimiter(" ");
        return space;
    }
}

