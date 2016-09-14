/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 1
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization;

abstract public class FoodMessage {
    abstract public void encode();
    abstract public void decode();
    public String getTimeStamp(){
        return null;
    }
}
