import tefub.serialization.EndianCoder;

public class ByteArrayInputTest {
    public static void main(String args[]) {
        byte[] test = new byte[]{1, 12};
        int a = EndianCoder.decodeShort(test, 0);
        byte[] encode = new byte[test.length];
        EndianCoder.encodeBytes(encode, a, 0, test.length);

        long it = 4294967295L;
        int testIt = (int)it;
        System.out.println(testIt);

    }
}
