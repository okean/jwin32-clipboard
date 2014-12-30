package win32.clipboard.monitor;

public interface IClipboardObserver {

	/**
	 * Should be implemented by all interested observers.
	 */
	void onUpdate();
}
