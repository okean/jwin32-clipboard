package win32.clipboard.windows.structures;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class RGBQUAD extends Structure {
	public int rgbBlue;
	public int rgbGreen;
	public int rgbRed;
	public int rgbReserved = 0;
	
	public RGBQUAD(Pointer p) {
		super(p);
		read();
	}

	protected List<String> getFieldOrder() {
		return Arrays.asList(new String[] { "rgbBlue", "rgbGreen", "rgbRed",
				"rgbReserved" });
	}
}
