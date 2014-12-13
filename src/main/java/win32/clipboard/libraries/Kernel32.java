package win32.clipboard.libraries;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

public interface Kernel32 extends StdCallLibrary {
	Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("kernel32",
			Kernel32.class, W32APIOptions.UNICODE_OPTIONS);
	
	int GetLastError();
	
	Pointer GlobalAlloc(int uFlags, int dwBytes);
	Pointer GlobalLock(Pointer hMem);
	Pointer GlobalFree(Pointer hMem);
	int GlobalSize(Pointer hMem);
	boolean GlobalUnlock(Pointer hMem);
}
