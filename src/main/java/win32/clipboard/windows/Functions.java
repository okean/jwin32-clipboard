package win32.clipboard.windows;

import win32.clipboard.windows.libraries.Gdi32;
import win32.clipboard.windows.libraries.Kernel32;
import win32.clipboard.windows.libraries.Shell32;
import win32.clipboard.windows.libraries.User32;

import com.sun.jna.Pointer;

public class Functions {

	private static final User32 user32 = User32.INSTANCE;
	private static final Kernel32 kernel32 = Kernel32.INSTANCE;
	private static final Shell32 shell32 = Shell32.INSTANCE;
	private static final Gdi32 gdi32 = Gdi32.INSTANCE;
	
	public static boolean openClipboard(Pointer hWndNewOwner) {
		return user32.OpenClipboard(hWndNewOwner);
	}
	
	public static boolean closeClipboard() {
		return user32.CloseClipboard();
	}
	
	public static boolean emptyClipboard() {
		return user32.EmptyClipboard();
	}
	
	public static Pointer setClipboardData(int uFormat, Pointer hMem) {
		return user32.SetClipboardData(uFormat, hMem);
	}
	
	public static boolean isClipboardFormatAvailable(int format) {
		return user32.IsClipboardFormatAvailable(format);
	}
	
	public static Pointer getClipboardData(int uFormat) {
		return user32.GetClipboardData(uFormat);
	}
	
	public static int getClipboardFormatName(int format, char[] lpszFormatName, int cchMaxCount) {
		return user32.GetClipboardFormatName(format, lpszFormatName, cchMaxCount);
	}
	
	public static int registerClipboardFormat(String lpszFormat) {
		return user32.RegisterClipboardFormat(lpszFormat);
	}
	
	public static int enumClipboardFormats(int format) {
		return user32.EnumClipboardFormats(format);
	}
	
	public static int countClipboardFormats() {
		return user32.CountClipboardFormats();
	}
	
	public static int getLastError() {
		return kernel32.GetLastError();
	}
	
	public static Pointer globalAlloc(int uFlags, int dwBytes) {
		return kernel32.GlobalAlloc(uFlags, dwBytes);
	}
	
	public static Pointer globalLock(Pointer hMem) {
		return kernel32.GlobalLock(hMem);
	}
	
	public static Pointer globalFree(Pointer hMem) {
		return kernel32.GlobalFree(hMem);
	}
	
	public static int globalSize(Pointer hMem) {
		return kernel32.GlobalSize(hMem);
	}
	
	public static boolean globalUnlock(Pointer hMem) {
		return kernel32.GlobalUnlock(hMem);
	}
	
	public static int dragQueryFile(Pointer hDrop, int iFile, char[] lpszFile, int cch) {
		return shell32.DragQueryFile(hDrop, iFile, lpszFile, cch);
	}
	
	public static int getEnhMetaFileBits (Pointer hemf, int cbBuffer, byte[] lpbBuffer) {
		return gdi32.GetEnhMetaFileBits(hemf, cbBuffer, lpbBuffer);
	}
}
