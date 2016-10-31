import tefub.serialization.EndianCoder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ByteArrayInputTest {
    public static void main(String args[]) {
        byte[] test = new byte[]{1, 12};
        int a = EndianCoder.decodeShort(test, 0);
        System.out.println(a);
    }
}
