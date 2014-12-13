package win32.clipboard;

import win32.clipboard.libraries.Gdi32;
import win32.clipboard.libraries.Kernel32;
import win32.clipboard.libraries.Shell32;
import win32.clipboard.libraries.User32;

import com.sun.jna.Pointer;

public class Functions {

	private static final User32 user32 = User32.INSTANCE;
	private static final Kernel32 kernel32 = Kernel32.INSTANCE;
	private static final Shell32 shell32 = Shell32.INSTANCE;
	private static final Gdi32 gdi32 = Gdi32.INSTANCE;
	
	static boolean openClipboard(Pointer hWndNewOwner) {
		return user32.OpenClipboard(hWndNewOwner);
	}
	
	static boolean closeClipboard() {
		return user32.CloseClipboard();
	}
	
	static boolean emptyClipboard() {
		return user32.EmptyClipboard();
	}
	
	static Pointer setClipboardData(int uFormat, Pointer hMem) {
		return user32.SetClipboardData(uFormat, hMem);
	}
	
	static boolean isClipboardFormatAvailable(int format) {
		return user32.IsClipboardFormatAvailable(format);
	}
	
	static Pointer getClipboardData(int uFormat) {
		return user32.GetClipboardData(uFormat);
	}
	
	static int getLastError() {
		return kernel32.GetLastError();
	}
	
	static Pointer globalAlloc(int uFlags, int dwBytes) {
		return kernel32.GlobalAlloc(uFlags, dwBytes);
	}
	
	static Pointer globalLock(Pointer hMem) {
		return kernel32.GlobalLock(hMem);
	}
	
	static Pointer globalFree(Pointer hMem) {
		return kernel32.GlobalFree(hMem);
	}
	
	static int globalSize(Pointer hMem) {
		return kernel32.GlobalSize(hMem);
	}
	
	static boolean globalUnlock(Pointer hMem) {
		return kernel32.GlobalUnlock(hMem);
	}
	
	static int dragQueryFile(Pointer hDrop, int iFile, char[] lpszFile, int cch) {
		return shell32.DragQueryFile(hDrop, iFile, lpszFile, cch);
	}
	
	static int getEnhMetaFileBits (Pointer hemf, int cbBuffer, byte[] lpbBuffer) {
		return gdi32.GetEnhMetaFileBits(hemf, cbBuffer, lpbBuffer);
	}
}
