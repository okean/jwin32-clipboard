package win32.clipboard.windows.structures;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class BITMAPINFO extends Structure {
    public BITMAPINFOHEADER bmiHeader;
	public RGBQUAD[] bmiColors = new RGBQUAD[1];
    
    protected List<String> getFieldOrder() {
        return Arrays.asList(new String[] { "bmiHeader", "bmiColors" });
    }
    
    public BITMAPINFO(Pointer p) {
    	super(p);
    	read();
    }
}
