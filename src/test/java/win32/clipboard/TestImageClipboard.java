package win32.clipboard;

import java.io.File;
import java.io.FileInputStream;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestImageClipboard {

	@DataProvider(name = "bmp")
	public Object[][] getBitmap() {
		return new Object[][] { { "1bit.bmp" }, { "4bit.bmp" },
				{ "8bit.bmp" }, { "24bit.bmp" } };
	}

	@Test(description = "set and get bitmap image as expected", dataProvider = "bmp")
	public void i_01(String bmp) throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();
		File f = new File(classLoader.getResource(bmp).getFile());
		byte[] img = new byte[(int) f.length()];
		FileInputStream fis = new FileInputStream(f);
		fis.read(img);
		fis.close();
		
		Clipboard.setData(img, Clipboard.DIB);
		byte[] clipImg = (byte[]) Clipboard.getData(Clipboard.DIB);
		Assert.assertEquals(img, clipImg);
	}
}
