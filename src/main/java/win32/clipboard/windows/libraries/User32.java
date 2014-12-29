package win32.clipboard.windows.libraries;

import win32.clipboard.windows.structures.MSG;

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

	int GetClipboardFormatName(int format, char[] lpszFormatName,
			int cchMaxCount);

	int RegisterClipboardFormat(String lpszFormat);

	int EnumClipboardFormats(int format);

	int CountClipboardFormats();

	Pointer CreateWindowEx(int exStyle, String className, String windowName,
			int style, int x, int y, int width, int height, Pointer parent,
			Pointer menu, Pointer instance, Object param);
	
	boolean AddClipboardFormatListener(Pointer hwnd);
	
	boolean RemoveClipboardFormatListener(Pointer hwnd);
	
	boolean GetMessage(MSG lpMsg, Pointer hWnd, int wMsgFilterMin, int wMsgFilterMax);

	Pointer GetClipboardOwner();
}
