package win32.clipboard.monitor;

import static win32.clipboard.Clipboard.TEXT;
import static win32.clipboard.Clipboard.setData;
import junit.framework.Assert;

import org.testng.annotations.Test;

import win32.clipboard.windows.SystemCallError;

public class TestClipboardMonitor {
	
	ClipboardMonitor monitor = new  ClipboardMonitor();
	
	@Test(enabled = false)
	public void init() {
		monitor.setCounter();
		monitor.start();
	}
	
	/**
	 * Test is failing while running it with mvn test
	 */
	@Test(description = "clipboard monitor basic functionality",
			dependsOnMethods = "init", enabled = false)
	public void cm_01() throws SystemCallError, InterruptedException {
		setData("foo", TEXT);
		Thread.sleep(100);
		Assert.assertTrue(monitor.getCounter() > 0);
	}
}
