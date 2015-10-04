package win32.clipboard.windows;

import static com.sun.jna.platform.win32.Kernel32Util.formatMessage;

public class SystemCallError extends Exception {

    private static final long serialVersionUID = 4556739850560483503L;
    
    private int code;
    
    public SystemCallError(int code) {
        super(formatMessage(code));
        this.code = code;
    }
    
    public SystemCallError(int code, Throwable cause) {
        super( formatMessage(code), cause);
        this.code = code;
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
    
    @Override
    public String getMessage() {
        return code + " '" + super.getMessage() + "'";
    }
    
    public int getCode() {
        return code;
    }
}
