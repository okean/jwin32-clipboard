package win32.clipboard.monitor;

import java.util.concurrent.CyclicBarrier;

import win32.clipboard.windows.structures.MSG;

import com.sun.jna.Pointer;

import static win32.clipboard.windows.Constants.*;
import static win32.clipboard.windows.Functions.*;
import static com.sun.jna.platform.win32.Kernel32Util.formatMessage;

public abstract class AbstractClipboardMonitor implements Runnable,
		IClipboardMonitor {
	private  Thread thread;
	private Pointer hwnd;
	private CyclicBarrier barrier;

	public void start() {
		if (thread == null) {
			barrier = new CyclicBarrier(2);
			thread = new Thread(this, "Clipboard");
			thread.setDaemon(true);
			thread.start();
			
			try {
				barrier.await();
			} catch (Exception e) {
			}		
		}
	}

	public void stop() {
		if (thread != null) {
			thread.interrupt();
			thread = null;
		}
	}

	@Override
	public void run() {
		hwnd = createWindowEx(0, "Static", null, 0, 0, 0, 0, 0, null, null,
				null, null);

		if (hwnd == null)
			throw new RuntimeException("Unable to create clipboard window. "
					+ formatMessage(getLastError()));

		if (!addClipboardFormatListener(hwnd)) {
			throw new RuntimeException("Unable to install clipboard listener. "
					+ formatMessage(getLastError()));
		}
		
		try {
			barrier.await();
		} catch (Exception e) {}

		MSG msg = new MSG();
		while(getMessage(msg, null, WM_CLIPBOARDUPDATE, WM_CLIPBOARDUPDATE)) {
			if (msg.message != WM_CLIPBOARDUPDATE) 
				continue;
			
			onChange();
		}
	}

	abstract protected void onChange();
}
