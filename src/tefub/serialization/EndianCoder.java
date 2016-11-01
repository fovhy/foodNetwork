/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 4
 * Class: CSI 4321
 *
 ************************************************/
package tefub.serialization;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * A coder for encode and decode little endian byte array.
 */
public class EndianCoder {
    private final static int SSIZE = Short.SIZE / Byte.SIZE; // 16 bit
    private final static int ISIZE = Integer.SIZE / Byte.SIZE; // 32 bit
    private final static int BYTEMASK = 0xFF;

    /**
     * Encode the data in small endian
     * @param dst where to store the data
     * @param val the value you want to transform
     * @param offset how much you are into the array
     * @param size size of the array
     * @return new offset
     */
    public static int encodeBytes(byte[] dst, int val, int offset, int size){
        for(int i = 0; i < size; i++){
            dst[offset++] = (byte) (val >> (size - i - 1) * Byte.SIZE);
        }
        EndianCoder.reverse(dst);
        return offset;
    }

    /**
     * Decode the array in small endian
     * @param val array to decode
     * @param offset offset
     * @param size how much you want to decode
     * @return a long that holds the data
     */
    public static long decodeBytes(byte[] val, int offset, int size){
        long toReturn = 0;
        for(int i = size - 1; i >= 0; i--){
            toReturn = (toReturn << Byte.SIZE) | ((long)val[offset + i] & BYTEMASK);
        }
        return toReturn;
    }

    /**
     * Encode things in 4 bytes
     * @param dst destination array
     * @param val value to transform
     * @param offset how deep you are in the array
     * @return new offset
     */
    public static int encode4Bytes(byte[] dst, int val, int offset){
        return encodeBytes(dst, val, offset, ISIZE);
    }

    /**
     * Check if a String is purely in ASCII
     * @param s the String to be checked
     * @return whether it is completely in ascii or not
     */
    public static boolean checkForAscii(String s){
        return Charset.forName("ASCII").newEncoder().canEncode(s);
    }

    /**
     * Get the unsigned short, because it is unsigned short we need a int to hold it
     * @param val the byte array to decode
     * @param offset the offset
     * @return  unsigned short
     */
    public static int decodeShort(byte[] val, int offset){
        return (int)decodeBytes(val, offset, SSIZE);
    }

    /**
     * @param val decode an unsigned int
     * @param offset offset of the array
     * @return unsigned int in long form
     */
    public static long decodeUnsignedInt(byte[] val, int offset){
        return decodeBytes(val, offset, ISIZE);
    }

    /**
     * Reverse the order of a bytes array, can turn little endian to big endian and vice versa
     * @param val the byte array you wish to reverse
     */
    public static void reverse(byte[] val){
        if (val == null) {
            return;
        }
        int i = 0;
        int j = val.length - 1;
        byte tmp;
        while (j > i) {
            tmp = val[j];
            val[j] = val[i];
            val[i] = tmp;
            j--;
            i++;
        }
    }
    /**
     * Concat two byte stream
     * @param first the first byte array to combine
     * @param second the second byte array to combine
     * @return the combined stream
     * @throws IOException if the write to byteStream fails
     */
    public static byte[] concat(byte[] first, byte[] second) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        outputStream.write(first);
        outputStream.write(second);
        return outputStream.toByteArray( );
    }
}
