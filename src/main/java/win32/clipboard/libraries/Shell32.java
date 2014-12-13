package win32.clipboard.libraries;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

public interface Shell32 extends StdCallLibrary {

	Shell32 INSTANCE = (Shell32) Native.loadLibrary("shell32", Shell32.class,
			W32APIOptions.DEFAULT_OPTIONS);
	
	int DragQueryFile(Pointer hDrop, int iFile, char[] lpszFile, int cch);
}
