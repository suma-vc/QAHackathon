package qaHackathon;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.util.SystemOutLogger;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import utilities.SeleniumUtility;





public class AppiumAutomation {
public Map AppiumTest() {
	

	Map<String, String> map = new HashMap<>();
		SeleniumUtility util= new SeleniumUtility();
		AndroidDriver driver;
		final String PLATFORM_ANDROID = "Android";
		final String PLATFORM_IOS = "iOS";
	
	
		DesiredCapabilities caps = new DesiredCapabilities();
		
			try {
				URL appiumServerURL = new URL("http://127.0.0.1:4723/");
				
				
				if (PLATFORM_ANDROID.equalsIgnoreCase("Android")) {	
					caps.setCapability("device", "Android");
					caps.setCapability("platformVersion", "Android 15");
					caps.setCapability("deviceName", "emulator-5556");
					//caps.setCapability(CapabilityType.BROWSER_NAME, "Chrome");
					caps.setCapability("appPackage", "com.dharma.stepin");
					caps.setCapability("appActivity", "com.dharma.stepin.MainActivity");
					//caps.setCapability("chromedriver_autodownload", true);
					caps.setCapability("automationName",  "UiAutomator2");
					
					driver = new AndroidDriver(appiumServerURL, caps);
driver.findElement(By.xpath("//android.widget.Button")).click();
Thread.sleep(3000);
List<WebElement> products =driver.findElements(By.xpath("//android.view.View//android.widget.TextView"));

System.out.println(products.size());
			String productName=	products.get(0).getText();
			String description =	products.get(1).getText();
			String price=	products.get(2).getText();
		
			map.put("productName", productName);
			map.put("description", description);
			map.put("price", price);
				System.out.println(productName);
				System.out.println(description);
				System.out.println(price);
}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return map;

}
}