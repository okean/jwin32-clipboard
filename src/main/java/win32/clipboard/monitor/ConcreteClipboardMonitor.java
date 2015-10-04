package win32.clipboard.monitor;

import java.util.ArrayList;

public class ConcreteClipboardMonitor extends AbstractClipboardMonitor {
    
    private ArrayList<IClipboardObserver> observers;
    
    public ConcreteClipboardMonitor() {
        observers = new ArrayList<IClipboardObserver>();
    }
    
    /**
     * Register an observer.
     * @param observer
     */
    public void addObserver(IClipboardObserver observer) {
        observers.add(observer);
    }
    
    /**
     * Remove an observer from list.
     * @param observer
     */
    public void removeObserver(IClipboardObserver observer) {
        int i = observers.indexOf(observer);
        if (i >= 0)
            observers.remove(i);
    }
    
    /* (non-Javadoc)
     * @see win32.clipboard.monitor.AbstractClipboardMonitor#onChange()
     */
    @Override
    protected void onChange() {
        if (observers != null) {
            for(IClipboardObserver observer : observers) {
                observer.onUpdate();
            }
        }
    }
}
