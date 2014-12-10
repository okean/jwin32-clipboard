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
}
