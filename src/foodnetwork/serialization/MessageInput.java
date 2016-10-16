/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 0
 * Class: CSI 4321
 *
 ************************************************/

package foodnetwork.serialization;

import java.io.*;
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
     * The reader that will do the scanning and buffer handling
     */
    private InputStreamReader messageReader;
    private String delimiter; // the delimiter that it will skipp

    /**
     * Public constructor that associate one InputStream with this MessageInput object
     *
     * @param in The InputStream MessageInput has
     */
    public MessageInput(InputStream in) {
        try {
            messageReader = new InputStreamReader(in, "ASCII");
            delimiter = " ";
        } catch (UnsupportedEncodingException e) {
            System.err.println("Unsupported encode"); // will never happen
        }
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
        String temp = "";
        for(int i = 0; i < count ; i++){
            try {
                int tempChar = messageReader.read();
                if(tempChar != -1){
                    temp += (char)tempChar;
                }else{
                    throw new EOFException("Stream prematurely ended when reading fixed " + count +
                            " bytes");
                }
            } catch (IOException e) {
                throw new EOFException("Failed to read fixed " + count + " bytes, EOFException");
            }
        }
        return temp;
    }

    /**
     * get the next string from the stream that contains a certain pattern. It will consume delimiter
     * @param pattern the pattern you want to search in string format. A Java regex.
     * @return the String it found
     * @throws EOFException if stream ends prematurely
     * @throws FoodNetworkException Cannot find such pattern in the stream
     */
    public String getNextStringWithPattern(String pattern) throws EOFException, FoodNetworkException {
        String temp = "";      // template string that to be returned
        int tempChar;
        try {
            boolean loop = true;
            while(loop){
                if((tempChar = messageReader.read()) != -1){
                    if(delimiter.contains(String.valueOf((char) tempChar))){
                        loop = false;
                    }
                    else{
                        temp += (char)tempChar;
                    }
                }else{
                    throw new EOFException("Stream unexpectedly terminates when reading pattern: " +
                            pattern);
                }
            }
        } catch (IOException e) {
            throw new EOFException("Failed to read pattern " + pattern + " EOFException");
        }
        if(!temp.matches(pattern)){
            throw new FoodNetworkException("No such pattern as " + pattern);
        }
        return temp;
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
            throw new FoodNetworkException("Failed to parse a unsigned double." + e.getMessage());
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
     * Get's next line in inputStream
     * @return next line of String
     * @throws FoodNetworkException trouble happens when read in next line
     */
    public String getNextLine() throws FoodNetworkException {
        String temp = "";
        int tempChar;
        try {
            while((tempChar = messageReader.read()) != -1){
                temp += (char)tempChar;
            }
        } catch (IOException e) {
            throw new FoodNetworkException("Failed to get next line of input IOExcpeiton");
        }
        return temp;
    }

    /**
     * Set delimiters
     * @param delimiter the delimiter you want to set to
     */
    public void setDelimiter(String delimiter){
        delimiter = delimiter;
    }

    /**
     * Get delimiters
     * @return the current delimiter String
     */
    public String getDelimiter(){
        return delimiter;
    }
}

