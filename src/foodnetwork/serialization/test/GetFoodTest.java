/************************************************
 *
 * Author: Dean He
 * Assignment: Assignment 1
 * Class: CSI 4321
 *
 ************************************************/
package foodnetwork.serialization.test;

import foodnetwork.serialization.FoodNetworkException;
import foodnetwork.serialization.GetFood;
import foodnetwork.serialization.MessageInput;
import foodnetwork.serialization.MessageOutput;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *  Parameterized test on GetFood class
 */
@RunWith(Parameterized.class)
public class GetFoodTest {
    private final String CHARSET = "ASCII";

    private String expDecode;
    private Long expTimestamp;
    private GetFood expGetFood;

    /**
     * Set up parameters
     * @return parameters array
     * @throws FoodNetworkException fails to construct a GetFood object
     */
    @Parameters
    public static Collection<Object[]> data() throws FoodNetworkException {
        ArrayList list = new ArrayList();
        list.add(new Object[]{"FN1.0 23 GET \n", 23L});
        list.add(new Object[]{"FN1.0 10000 GET \n", 10000L});
        list.add(new Object[]{"FN1.0 0 GET \n", 0L});
        return list;
    }

    /**
     * Set up a GetFood item with expected values from parameters
     * @param code expected Decoded String
     * @param timestamp expected timestamp
     * @throws FoodNetworkException Fails to construct a GetFoodItem
     */
    public GetFoodTest(String code, long timestamp) throws FoodNetworkException {
        this.expDecode = code;
        this.expTimestamp = timestamp;
        this.expGetFood = new GetFood(timestamp);
    }

    /**
     * Test if GetFood attributes is consistent with expected values
     * @param getFood a GetFood object to be tested
     */
    public void checkGetFood(GetFood getFood){
        assertEquals(expTimestamp, new Long(getFood.getMessageTimestamp()));
    }

    /**
     * Test over GetFood constructor
     */
    @Test
    public void TestConstructor(){
        checkGetFood(expGetFood);
    }

    /**
     * Test decode function of GetFood
     * @throws FoodNetworkException fails to construct a GetFood object
     * @throws UnsupportedEncodingException wrong encoding
     * @throws EOFException if stream ends prematurally
     */
    @Test
    public void testDecode() throws FoodNetworkException, UnsupportedEncodingException, EOFException {
        MessageInput in = new MessageInput(new ByteArrayInputStream(expDecode.getBytes(CHARSET)));
        GetFood temp = (GetFood) GetFood.decode(in);
        checkGetFood(temp);
    }

    /**
     * Test encode function of GetFood (test getRequest)
     * @throws FoodNetworkException if it fails to encode
     * @throws UnsupportedEncodingException for wrong encoding
     */
    @Test
    public void TestEncode() throws FoodNetworkException, UnsupportedEncodingException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        MessageOutput out = new MessageOutput(outStream);
        expGetFood.encode(out);
        assertArrayEquals(expDecode.getBytes(CHARSET), outStream.toByteArray());
    }
    /**
     * Test if toString function throws out a String
     */
    @Test
    public void testToString(){
        assertNotNull(expGetFood.toString());
    }
}