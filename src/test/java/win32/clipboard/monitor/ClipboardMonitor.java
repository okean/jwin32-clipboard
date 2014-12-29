package win32.clipboard.monitor;


public class ClipboardMonitor extends AbstractClipboardMonitor {

	public static int counter = 0;
	
	public void setCounter() {
		counter = 0;
	}
	
	public int getCounter() {
		return counter;
	}
	
	@Override
	protected void onChange() {
		counter += 1;
	}
}
