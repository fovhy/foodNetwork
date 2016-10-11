/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 3
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization;

import java.io.EOFException;

/**
 * Interval is a message class that stores the foodItems added within a certain interval.
 */
public class Interval extends FoodMessage{
    private final static String type = "INTERVAL"; // type of FoodMessage
    private int intervalTime;

    /**
     * Construct a Interval message with a messageTimestamp, and MessageInput
     * @param messageTimestamp timestamp for message itself
     * @param in the MessageInput to use
     * @throws FoodNetworkException if it fails to construct the class
     * @throws EOFException the stream ends prematurely
     */
    public Interval(long messageTimestamp, MessageInput in) throws FoodNetworkException, EOFException{
        setMessageTimestamp(messageTimestamp);
        in.getNextSpace();
        setIntervalTime(in.getNextUnsignedInt());
    }

    /**
     * @param messageTimestamp the message timestamp
     * @param intervalTime how long of time frame
     * @throws FoodNetworkException fails to construct the Interval
     */
    public Interval(long messageTimestamp, int intervalTime) throws FoodNetworkException {
        setMessageTimestamp(messageTimestamp);
        setIntervalTime(intervalTime);
    }

    /**
     * Return the interval time
     * @return interval time
     */
    public int getIntervalTime(){
        return intervalTime;
    }

    /**
     * Set interval time
     * @param intervalTime time to be set
     * @throws FoodNetworkException if time is smaller than 0
     */
    public void setIntervalTime(int intervalTime) throws FoodNetworkException {
        if(intervalTime < 0) {
            throw new FoodNetworkException("Interval time not greater than 0");
        }
        this.intervalTime = intervalTime;
    }

    /**
     * Get the interval message
     * @return interval message
     */
    @Override
    public String getFullRequest() {
        return type + " " + intervalTime + " ";
    }

    /**
     * Get the request for interval message type
     * @return message type
     */
    @Override
    public String getRequest() {
        return type;
    }

    /**
     * Hash code function for this class
     * @return a hashcode
     */
    public int hashCode(){
        return super.hashCode() + new Integer(intervalTime).hashCode() * 13;
    }

    /**
     * Check if this interval object equals to another object
     * @param obj another object
     * @return equal or not
     */
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }
        if(obj == this){
            return true;
        }
        if(!(obj instanceof Interval)){
            return false;
        }
        Interval testObj = (Interval) obj;
        if(this.hashCode()== testObj.hashCode() &&
                this.timestamp == testObj.timestamp &&
                this.intervalTime == testObj.intervalTime) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * Return interval message to a human readable format
     * @return message in human readable format
     */
    public String toString(){
        String temp = super.toString();
        temp = temp + "Type: " + type + "\n" ;
        temp =  temp + "Interval time: " + intervalTime + "\n";
        return temp;
    }
}
