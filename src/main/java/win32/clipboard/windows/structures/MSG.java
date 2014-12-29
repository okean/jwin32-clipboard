package win32.clipboard.windows.structures;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class MSG extends Structure {

	public Pointer hWnd;
	public int message;
	public int wParam;
	public int lParam;
	public int time;
	public int x;
	public int y;

	protected List<String> getFieldOrder() {
		return Arrays.asList("hWnd", "message", "wParam", "lParam", "time",
				"x", "y");
	}
}