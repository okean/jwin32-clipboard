package win32.clipboard;

import junit.framework.Assert;

import org.testng.annotations.Test;

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
		getData(TEXT);
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
}
