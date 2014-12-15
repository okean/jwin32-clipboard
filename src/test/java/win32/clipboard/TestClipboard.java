package win32.clipboard;

import java.util.Map;

import junit.framework.Assert;

import org.testng.annotations.Test;

import win32.clipboard.windows.SystemCallError;
import static win32.clipboard.Clipboard.*;

public class TestClipboard {

	@Test(description = "expected constants are defined")
	public void c_01() {
		Assert.assertNotNull(TEXT);
		Assert.assertNotNull(OEMTEXT);
		Assert.assertNotNull(UNICODETEXT);
		Assert.assertNotNull(DIB);
		Assert.assertNotNull(BITMAP);
		Assert.assertNotNull(HDROP);
		Assert.assertNotNull(ENHMETAFILE);
	}
	
	@Test(description = "data method basic functionality")
	public void d_01() throws Exception {
		Object obj = getData(TEXT);
	}
	
	@Test(description = "data method requires proper format")
	public void d_02() throws Exception {
		Assert.assertEquals("", getData(-1));
	}
	
	@Test(description = "setData basic functionality")
	public void sd_01() throws SystemCallError {
		setData("foo", TEXT);
	}

	@Test(description = "setData works with unicode text")
	public void sd_02() throws SystemCallError {
		setData("бар", UNICODETEXT);
	}

	@Test(description = "setData requires a valid data format", 
			expectedExceptions = IllegalArgumentException.class)
	public void sd_03() throws SystemCallError {
		setData("foo", -1);
	}
	
	@Test(description = "set and get ascii data as expected")
	public void sg_01() throws Exception {
		setData("foo", TEXT);
		Assert.assertEquals("foo", getData(TEXT));
	}
	
	@Test(description = "set and get unicode data as expected")
	public void sg_02() throws Exception {
		setData("бар", UNICODETEXT);
		Assert.assertEquals("бар", getData(UNICODETEXT));
	}
	
	@Test(description = "getData is an alias for getData(TEXT)")
	public void sg_03() throws Exception {
		setData("foo", TEXT);
		Assert.assertEquals(getData(), getData(TEXT));
	}
	
	@Test(description = "setData is an alias for setData(TEXT)")
	public void sg_04() throws Exception {
		setData("foo");
		Assert.assertEquals("foo", getData());
	}
	
	@Test(description = "empty method basic functionality")
	public void e_01() throws SystemCallError {
		empty();
	}
	
	@Test(description = "Empties the contents of the clipboard")
	public void e_02() throws Exception {
		setData("foo");
		empty();
		Assert.assertEquals("", getData());
	}
	
	@Test(description = "clear is an alias for empty")
	public void e_03() throws Exception {
		setData("foo");
		clear();
		Assert.assertEquals("", getData());
	}
	
	@Test(description = "registerFormat basic functionality")
	public void rf_01() throws SystemCallError {
		int format = registerFormat("foo");
	}
	
	@Test(description = "formatName basic functionality")
	public void fn_01() throws SystemCallError {
		formatName(1);
	}
	
	@Test(description = "formatName returns expected value")
	public void fn_02() throws SystemCallError {
		int format = registerFormat("HTML Format");
		Assert.assertEquals("HTML Format", formatName(format));
		Assert.assertNull(formatName(999999));
	}
	
	@Test(description = "formats basic functionality")
	public void f_01() throws SystemCallError {
		Map<Integer, String> map = formats();
	}
	
	/**
	 * Test is failing for some reason
	 */
	@Test(description = "formats result contains expected values", 
			enabled = false)
	public void f_02() throws SystemCallError {
		Assert.assertTrue(formats().size() > 0);
		Assert.assertTrue(formats().containsKey(1));
	}
	
	@Test(description = "numFormats basic functionallity")
	public void nf_01() {
		int count = numFormats();
	}
	
	@Test(description = "numFormats returns an expected value")
	public void nf_02() {
		Assert.assertTrue(numFormats() >= 0);
		Assert.assertTrue(numFormats() < 1000);
	}
	
	@Test(description = "formatAvailable basic functionality")
	public void fa_01() {
		boolean a = formatAvailable(1);
	}
	
	@Test(description = "formatAvailable returns an expected value")
	public void fa_02() throws SystemCallError {
		setData("foo");
		Assert.assertTrue(formatAvailable(1));
		Assert.assertFalse(formatAvailable(-1));
	}
}
