package win32.clipboard;

import static win32.clipboard.Clipboard.TEXT;
import static win32.clipboard.Clipboard.setData;
import static win32.clipboard.windows.Functions.getLastError;
import junit.framework.Assert;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32Util;

import win32.clipboard.monitor.ClipboardMonitor;
import win32.clipboard.windows.SystemCallError;
import win32.clipboard.windows.libraries.User32;

public class Demo {
	
	public Demo() {
		new Thread("Test") {
			
			public void run() {
				int counter = 0;
				while(true) {
					try {
						setData("foo " + counter++, TEXT);
						sleep(1000);
					} catch (Exception e) {
					}
				}
			}
		}.start();
	}
	
	public static void main(String[] args) throws InterruptedException, SystemCallError {
		Demo d = new Demo();
		ClipboardMonitor m = new ClipboardMonitor();
		m.start();
		while(true) {
		System.out.println(m.getCounter());	
		Thread.sleep(1000);
		}
		/*ClipboardMonitor m = new ClipboardMonitor();
		
		Assert.assertEquals(1, m.getCounter());
		m.stop();
		
		Clipboard.setData("Hello", TEXT);*/
	}

}
