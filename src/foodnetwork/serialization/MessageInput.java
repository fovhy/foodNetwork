/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 0
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
        messageScanner = new Scanner(in, "ASCII");
    }

    /**
     * Read a name from the InputStream.
     *
     * @return the name String which should be in pattern of
     * @throws FoodNetworkException the name String format is wrong
     * @throws EOFException         if stream ends prematurely
     */
    public char getNextSpace() throws FoodNetworkException, EOFException {
        char space = getNextFixedBytes(1).charAt(0); // read one char
        if (space != ' ') {
            throw new FoodNetworkException("Expecting a space");
        }
        return space;
    }

    /**
     * get fixed amount of bytes from a stream
     * @param count how many bytes you want
     * @return bytes in String
     * @throws FoodNetworkException if the count is not greater than 0
     * @throws EOFException if stream ends prematurely
     */
    public String getNextFixedBytes(int count) throws FoodNetworkException, EOFException {
        if(count <= 0){
            throw new FoodNetworkException("Invalid count");
        }
        messageScanner.useDelimiter("");
        char[] characterList = new char[count];
        for(int i = 0; i < count; i++){
            if(messageScanner.hasNext()){
                characterList[i] = messageScanner.next().charAt(0);
            }else{
                throw new EOFException("Expecting more bytes when reading fixed length token");
            }
        }
        messageScanner.useDelimiter(" ");
        return String.valueOf(characterList);
    }

    /**
     * get the next string from the stream that contains a certain pattern.
     * @param pattern the pattern you want to search in string format. A Java regex.
     * @return the String it found
     * @throws EOFException if stream ends prematurely
     * @throws FoodNetworkException Cannot find such pattern in the stream
     */
    public String getNextStringWithPattern(String pattern) throws EOFException, FoodNetworkException {
        if(!messageScanner.hasNext()){
            throw new EOFException("Expecting more bytes when trying to read next pattern");
        }
        if(messageScanner.hasNext(pattern)){
            return messageScanner.next(pattern);
        }else{
            throw new FoodNetworkException("Failed to find the pattern :" + pattern);
        }
    }

    /**
     * Get next unsigned int in the stream
     * @return a unsigned int
     * @throws EOFException if stream ends prematurely
     * @throws FoodNetworkException if the int is too long or negative
     */
    public int getNextUnsignedInt() throws EOFException, FoodNetworkException {
        String unsignedIntString = getNextStringWithPattern("[0-9]+");
        int unsignedInt;
        try{
            unsignedInt = Integer.parseInt(unsignedIntString);
        }catch(NumberFormatException e){
            throw new FoodNetworkException("Failed to parse the String as an int");
        }
        if(unsignedInt >= 0){
            return unsignedInt;
        }else{
            throw new FoodNetworkException("Negative unsigned integer");
        }
    }

    /**
     * Get next unsigned long
     * @return unsigned long
     * @throws EOFException if stream ends prematurely
     * @throws FoodNetworkException long string is negative or too long
     */
    public long getNextUnsignedLong() throws EOFException, FoodNetworkException {
        String unsignedLongString = getNextStringWithPattern("[0-9]+");
        long unsignedLong;
        try{
            unsignedLong = Long.parseUnsignedLong(unsignedLongString);
        }catch(NumberFormatException e){
            throw new FoodNetworkException("Failed to parse the String as a long");
        }
        if(unsignedLong >= 0){
            return unsignedLong;
        }else{
            throw new FoodNetworkException("Negative unsigned long");
        }
    }

    /**
     * Get next unsigned double string
     * @return a string that represents a unsigned double
     * @throws EOFException if stream ends prematurely
     * @throws FoodNetworkException if the pattern does not match
     */
    public String getNextUnsignedDouble() throws EOFException, FoodNetworkException {
        try {
            return getNextStringWithPattern("[0-9]*\\.?[0-9]+");
        } catch(FoodNetworkException e){
            throw new FoodNetworkException("Failed to parse a unsigned double", e);
        }
    }

    /**
     * Get next newLine character
     * @return "\n"
     * @throws FoodNetworkException If the next byte is not "\n"
     * @throws EOFException stream closes prematurely
     */
    public char getNextNewLine() throws FoodNetworkException, EOFException {
        char newLine;
        newLine = getNextFixedBytes(1).charAt(0); // get next byte
        if('\n' != newLine){
            throw new FoodNetworkException("Expecting a new line character");
        }
        return newLine;
    }

    /**
     * Get next String using \n as decimeter
     * @return next String
     * @throws FoodNetworkException if there is no data before \n
     */
    public String getNextString() throws FoodNetworkException {
        String string;
        messageScanner.useDelimiter("\n");
        if (messageScanner.hasNext()) {
            string = messageScanner.next();
        } else {
            throw new FoodNetworkException("No String before \\n");
        }
        messageScanner.useDelimiter(" ");
        return string;
    }
}

