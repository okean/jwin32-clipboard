package win32.clipboard.monitor;

public interface IClipboardMonitor {

	/**
	 * Starts the monitoring of the clipboard.
	 */
	void start();
	
	/**
	 * Stops the monitoring of the clipboard. 
	 */
	void stop();
}
