package win32.clipboard.windows.libraries;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

public interface Gdi32 extends StdCallLibrary {

	Gdi32 INSTANCE = (Gdi32) Native.loadLibrary("gdi32", Gdi32.class,
			W32APIOptions.DEFAULT_OPTIONS);
	
	int GetEnhMetaFileBits (Pointer hemf, int cbBuffer, byte[] lpbBuffer);
}
