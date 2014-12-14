package win32.clipboard;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import win32.clipboard.structures.BITMAPINFO;

import com.sun.jna.Pointer;

import static java.util.Arrays.copyOfRange;
import static win32.clipboard.Functions.*;
import static win32.clipboard.Constants.*;
import static win32.clipboard.Util.*;

public class Clipboard {

	public static final int TEXT = 1;
	public static final int OEMTEXT = 7;
	public static final int UNICODETEXT = 13;
	public static final int DIB = 8;
	public static final int BITMAP = 2;
	public static final int HDROP = 15;
	public static final int ENHMETAFILE = 14;

	private static final int BITMAP_FILE_HEADER_SIZE = 14;
	private static final int BITMAP_INFO_HEADER_SIZE = 40;

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
	 * Empties the contents of the clipboard.
	 * @throws SystemCallErrorf failing to call win api functions
	 */
	public static void empty() throws SystemCallError {
		try {
			open();
			clearIfOpened();
		} finally {
			close();
		}
	}
	
	/**
	 * Alias for Clipboard.empty()
	 */
	public static void clear() throws SystemCallError {
		empty();
	}
	
	/**
	 * Return data from clipboard.
	 * 
	 * If there is no data in the clipboard, or data is available but the 
	 * format doesn't match the data, then an empty string is returned.
	 * 
	 * Example:
	 * 	* Get some plain text
	 * 	Clipboard.getData(Clipboard.TEXT);
	 * 
	 * 	* Get a list of files copied from the Windows Explorer window.
	 *  Clipboard.getData(Clipboard.HDROP);
	 *  
	 *  * Get a bitmap image
	 *  Clipboard.getData(Clipboard.DIB);
	 * 
	 * @param format to attempt to retrieve the data in
	 * @return the data currently in the clipboard
	 * @throws Exception in case of invalid format, api call or bit count error
	 */
	public static Object getData(int format) throws Exception {
		Object clipdata = null;
		try {
			open();

			if (isClipboardFormatAvailable(format)) {
				Pointer handle = getClipboardData(format);
				
				switch(format) {
				case TEXT:
				case OEMTEXT:
				case UNICODETEXT:
					Charset enc = (format == UNICODETEXT) ? UTF16LE : FILE_ENCODING;
					clipdata = getTextData(handle, enc);
					break;
				case DIB:
				case BITMAP:
					clipdata = getImageData(handle);
					break;
				case HDROP:
					clipdata = getFileList(handle);
					break;
				case ENHMETAFILE:
					clipdata = getMetafileData(handle);
					break;
				default:
					throw new IllegalArgumentException("Format '" + format
							+ "'  not supported");
				}
			} else {
				clipdata = "";
			}
		} finally {
			close();
		}
		return clipdata;
	}
	
	public static Object getData() throws Exception {
		return getData(TEXT);
	}
	
	/**
	 * Get text data.
	 * 
	 * @param handle to a clipboard object in the specified format
	 * @param encoding used to construct a string given the charset
	 * @return text from clipboard
	 */
	private static String getTextData(Pointer handle, Charset encoding) {
		int size = globalSize(handle);
		Pointer ptr = globalLock(handle);
		
		byte[] buf = new byte[size];
		ptr.read(0, buf, 0, size);
		return new String(buf, encoding).trim();
	}
	
	/**
	 * Get data for bitmap files.
	 * @param handle to a clipboard object in the specified format.
	 * @return an array of bytes representing bitmap file.
	 * @throws Exception thrown if invalid bit.
	 */
	private static byte[] getImageData(Pointer handle) throws Exception {
		byte[] clipdata = null;
		int tableSize = 0;

		try {
			Pointer ptr = globalLock(handle);
			int size = globalSize(handle);

			BITMAPINFO bmi = new BITMAPINFO(ptr);

			int fileSize = size + BITMAP_FILE_HEADER_SIZE;

			switch (bmi.bmiHeader.biBitCount) {
			case 1:
				tableSize = 2;
				break;
			case 4:
				tableSize = 16;
				break;
			case 8:
				tableSize = 256;
				break;
			case 16:
			case 32:
				if (bmi.bmiHeader.biCompression == BI_RGB) {
					tableSize = bmi.bmiHeader.biClrUsed;
				} else if (bmi.bmiHeader.biCompression == BI_BITFIELDS) {
					tableSize = bmi.bmiHeader.biClrUsed;
					if (bmi.bmiHeader.biSize == BITMAP_INFO_HEADER_SIZE)
						tableSize += 3;
				} else {
					throw new Exception("Invalid bit/compression combination");
				}
				break;
			case 24:
				tableSize = bmi.bmiHeader.biClrUsed;
				break;
			default:
				throw new Exception("Invalid bit count");
			}
			
			int offset = BITMAP_FILE_HEADER_SIZE + bmi.bmiHeader.biSize + (tableSize * 4);
			
			byte[] buf = new byte[size];
			ptr.read(0, buf, 0, size);
			
			clipdata = concatBytes(getByte("BM"), toByte(fileSize), toByte(0000),
					toByte(offset), buf);
		} finally {
			if (handle != null)
				globalUnlock(handle);
		}
		return clipdata;
	}
	
	/**
	 * Get and return a list of file names that have been copied.
	 * @param handle to a clipboard object in the specified format.
	 * @return 
	 */
	private static List<String> getFileList(Pointer handle) {
		List<String> fileList = new ArrayList<String>();
		int count = dragQueryFile(handle, 0xFFFFFFFF, null, 0);

		for (int i = 0; i < count; i++) {
			int size = dragQueryFile(handle, i, null, 0) + 1;
			char[] buf = new char[size];
			dragQueryFile(handle, i, buf, buf.length);
			fileList.add(new String(buf));
		}

		return fileList;
	}
	
	/**
	 * Get data for enhanced metadata files.
	 * @param handle to a clipboard object in the specified format.
	 * @return  buffer that receives the metafile data
	 */
	private static byte[] getMetafileData(Pointer handle) {
		int size = getEnhMetaFileBits(handle, 0, null);
		byte[] buf = new byte[size];
		getEnhMetaFileBits(handle, buf.length, buf);
		return buf;
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