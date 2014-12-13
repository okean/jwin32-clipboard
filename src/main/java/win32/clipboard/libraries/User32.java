package win32.clipboard.libraries;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

public interface User32 extends StdCallLibrary {

	User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class,
			W32APIOptions.DEFAULT_OPTIONS);
	
	boolean OpenClipboard(Pointer hWndNewOwner);
	boolean EmptyClipboard();
	Pointer SetClipboardData(int uFormat, Pointer hMem);
	boolean CloseClipboard();
	boolean IsClipboardFormatAvailable(int format);
	Pointer GetClipboardData(int uFormat);
}
