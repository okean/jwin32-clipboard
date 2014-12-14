package win32.clipboard.windows.structures;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class BITMAPINFOHEADER extends Structure {
	public int biSize;
	public int biWidth;
	public int biHeight;
	public short biPlanes;
	public short biBitCount;
	public int biCompression;
	public int biSizeImage;
	public int biXPelsPerMeter;
	public int biYPelsPerMeter;
	public int biClrUsed;
	public int biClrImportant;

	public BITMAPINFOHEADER(Pointer p) {
		super(p);
		read();
	}
	
	protected List<String> getFieldOrder() {
		return Arrays.asList(new String[] { "biSize", "biWidth", "biHeight",
				"biPlanes", "biBitCount", "biCompression", "biSizeImage",
				"biXPelsPerMeter", "biYPelsPerMeter", "biClrUsed",
				"biClrImportant" });
	}
}
