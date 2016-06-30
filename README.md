### Description
jwin32-clipboard - a Java library for interacting with the Win32 clipboard

### Installation
```
mvn test (optional)
mvn clean install
```
   
### Synopsis
```java
import win32.clipboard.*;
   
System.out.println("Clearing clipboard");
Clipboard.empty();
   
System.out.println("Set the clipboard content");
Clipboard.setData("foo", Clipboard.TEXT);
   
System.out.println("Get the clipboard content")
String data = Clipboard.getData(Clipboard.TEXT);
```

### License
jwin32-clipboard project is covered by the GNU General Public License version 2
