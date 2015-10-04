package win32.clipboard;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class Util {

    public static byte[] concatBytes(byte[]... array) throws IOException {
        byte buf[] = null;
        if (array.length > 0) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            for (int i = 0; i < array.length; i++)
                os.write(array[i]);
            buf = os.toByteArray();
        }
        return buf;
    }

    public static byte[] toByte(int v) {
        return new byte[] { (byte) v, (byte) (v >>> 8), (byte) (v >>> 16),
                (byte) (v >>> 24) };
    }
    
    public static byte[] getByte(String str) {
        return str.getBytes(Charset.forName("UTF-8"));
    }
}
