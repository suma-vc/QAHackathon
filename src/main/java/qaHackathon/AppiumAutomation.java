package qaHackathon;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;





public class AppiumAutomation {

	public static void main(String[] args) {
		AndroidDriver driver;
		final String PLATFORM_ANDROID = "Android";
		final String PLATFORM_IOS = "iOS";
	//	AppiumDriver driver;
		// TODO Auto-generated method stub
		// driver=	MobileDriver.launchMobileWebApp("Android", "emulator-5554", "Chrome");
	//	driver.get("https://www.youtube.com");
		
		DesiredCapabilities caps = new DesiredCapabilities();
		
			try {
				URL appiumServerURL = new URL("http://127.0.0.1:4723/");
				
				
				if (PLATFORM_ANDROID.equalsIgnoreCase("Android")) {	
					caps.setCapability("device", "Android");
					caps.setCapability("platformVersion", "Android 15");
					caps.setCapability("deviceName", "emulator-5556");
					//caps.setCapability(CapabilityType.BROWSER_NAME, "Chrome");
					caps.setCapability("appPackage", "com.android.chrome");
					caps.setCapability("appActivity", "com.google.android.apps.chrome.Main");
					//caps.setCapability("chromedriver_autodownload", true);
					caps.setCapability("automationName",  "UiAutomator2");
					 caps.setCapability("appium:chromeOptions", new HashMap<String, Object>() {{
				            put("w3c", true);
					    }});
					driver = new AndroidDriver(appiumServerURL, caps);

					driver.get("https://www.youtube.com");
}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

}
}