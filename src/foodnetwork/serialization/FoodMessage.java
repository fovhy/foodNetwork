/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 1
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization;

abstract public class FoodMessage {
    public void encode(MessageInput in){

    }
    public void decode(MessageOutput out){

    }
    public String getTimeStamp(){
        return null;
    }
    public long getMessageTimestamp(){
        return 0L;
    }
    abstract public String getRequest();
    public int hashCode(){
        return 0;
    }
    public String toString(){
        return null;
    }


}
