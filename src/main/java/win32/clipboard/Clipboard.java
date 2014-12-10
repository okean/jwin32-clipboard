package win32.clipboard;

import java.nio.charset.Charset;

import com.sun.jna.Pointer;

import static java.util.Arrays.copyOfRange;
import static win32.clipboard.Functions.*;
import static win32.clipboard.Constants.*;

public class Clipboard {

	public static final int TEXT = 1;
	public static final int OEMTEXT = 7;
	public static final int UNICODETEXT = 13;
	public static final int DIB = 8;
	public static final int BITMAP = 2;

	private static final int BITMAP_FILE_HEADER_SIZE = 14;

	private static final Charset UTF16LE = Charset.forName("UTF-16LE");
	private static final Charset FILE_ENCODING = Charset.forName(System
			.getProperty("file.encoding"));

	/**
	 * Copy some data to the clipboard.
	 * 
	 * Example:
	 * 	* Put the string 'hello' on the clipboard
	 * 	Clipboard.setData("Hello", Clipboard.TEXT);
	 * 
	 * 	* Put the 'test.bmp' image on the clipboard
	 * 	byte[] buf = Files.readAllBytes(Paths.get("test.bmp"));
	 *  Clipboard.setData(buf, Clipboard.DIB);
	 * 
	 * @param clipdata the data to paste to clipboard.
	 * @param format the clipboard format.
	 * @throws SystemCallError if failing to call win api functions.
	 */
	public static void setData(Object clipdata, int format)
			throws SystemCallError {
		Pointer hMem = null;
		byte[] buf = null;
		int extra = 0;
		try {
			open();
			clearIfOpened();

			switch (format) {
			case TEXT:
			case OEMTEXT:
			case UNICODETEXT:
				clipdata += "\0";
				Charset enc = (format == UNICODETEXT) ? UTF16LE : FILE_ENCODING;
				buf = ((String) clipdata).getBytes(enc);
				extra = 4;
				break;
			case DIB:
			case BITMAP:
				buf = copyOfRange((byte[]) clipdata, BITMAP_FILE_HEADER_SIZE,
						((byte[]) clipdata).length);
				break;
			default:
				throw new IllegalArgumentException("Format '" + format
						+ "'  not supported");
			}

			hMem = globalAlloc(GHND, buf.length + extra);
			Pointer ptr = globalLock(hMem);

			ptr.write(0, buf, 0, buf.length);

			if (setClipboardData(format, hMem) == null)
				throw new SystemCallError(getLastError());
		} finally {
			if (hMem != null)
				globalFree(hMem);
			close();
		}
	}
	
	public static void setData(Object clipdata) throws SystemCallError {
		setData(clipdata, TEXT);
	}

	/**
	 * Opens the clipboard for examination and prevents other applications from
	 * modifying the clipboard content.
	 * 
	 * @throws SystemCallError
	 *             if the function fails.
	 */
	private static void open() throws SystemCallError {
		if (!openClipboard(new Pointer(0)))
			throw new SystemCallError(getLastError());
	}

	/**
	 * Empties the clipboard and frees handles to data in the clipboard.
	 * 
	 * @throws SystemCallError
	 *             if the function fails.
	 */
	private static void clearIfOpened() throws SystemCallError {
		if (!emptyClipboard())
			throw new SystemCallError(getLastError());
	}

	/**
	 * Closes the clipboard.
	 * 
	 * @throws SystemCallError
	 *             if the function fails.
	 */
	private static void close() throws SystemCallError {
		if (!closeClipboard())
			throw new SystemCallError(getLastError());
	}
}