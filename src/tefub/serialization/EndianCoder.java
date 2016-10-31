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
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * A coder for encode and decode little endian byte array.
 */
public class EndianCoder {
    private final static int SSIZE = Short.SIZE / Byte.SIZE; // 16 bit
    private final static int ISIZE = Integer.SIZE / Byte.SIZE; // 32 bit
    private final static int BYTEMASK = 0xFF;

    public static int encodeBytes(byte[] dst, int val, int offset, int size){
        for(int i = 0; i < size; i++){
            dst[offset++] = (byte) (val << (size - i - 1) * Byte.SIZE);
        }
        return offset;
    }
    public static long decodeBytes(byte[] val, int offset, int size){
        long toReturn = 0;
        for(int i = size - 1; i >= 0; i--){
            toReturn = (toReturn << Byte.SIZE) | ((long)val[offset + i] & BYTEMASK);
        }
        return toReturn;
    }
    public static int encode2Bytes(byte[] dst, short val, int offset){
        return encodeBytes(dst, val, offset, SSIZE);
    }
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
     * Decode Inet4Address in little endian format. Since it is an internet address, unsigned does not really
     * mean anything. Just store it in a 32 bit int.
     * @param val the byte array to decode
     * @param offset the offset
     * @return the 32 bit value that contains a Inet4Address
     */
    public static int decodeAddress(byte[] val, int offset){
        return (int)decodeBytes(val, offset, ISIZE);
    }

    /**
     * @param val decode an unsigned int
     * @param offset offset of the array
     * @return unsigned int in long form
     */
    public long decodeUnsignedInt(byte[] val, int offset){
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
    public static byte[] intToBytesBigEndian(int i){
        ByteBuffer bb = ByteBuffer.allocate(Integer.SIZE);
        bb.putInt(i);
        return bb.array();
    }
    public static byte[] shortToBytesBigEndian(short i){
        ByteBuffer bb = ByteBuffer.allocate(Short.SIZE);
        bb.putShort(i);
        return bb.array();
    }

}
