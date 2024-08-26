package driverMethods;

import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;

import io.appium.java_client.android.AndroidDriver;

import io.appium.java_client.ios.IOSDriver;

public class MobileDriver {

	private static final String PLATFORM_ANDROID = "Android";
	private static final String PLATFORM_IOS = "iOS";

	private static AppiumDriver driver;

	public static AppiumDriver launchMobileNativeApp(String platform, String deviceName, String appPath) {

		DesiredCapabilities caps = new DesiredCapabilities();
		try {
			URL appiumServerURL = new URL("http://localhost:4723/wd/hub");

			if (PLATFORM_ANDROID.equalsIgnoreCase(platform)) {

				caps.setCapability("device", PLATFORM_ANDROID);
				caps.setCapability("deviceName", deviceName);
				caps.setCapability("app", appPath);
				caps.setCapability("automationName", "UiAutomator2");

				driver = new AppiumDriver(appiumServerURL, caps);

			} else if (PLATFORM_IOS.equalsIgnoreCase(platform)) {
				caps.setCapability("device", PLATFORM_IOS);
				caps.setCapability("deviceName", deviceName);
				caps.setCapability("app", appPath);
				caps.setCapability("automationName", "XCUITest");
				driver = new AppiumDriver(appiumServerURL, caps);

			} else {
				throw new IllegalArgumentException("Unsupported platform: " + platform);
			}
		} catch (Exception e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		return driver;
	}

	public static AppiumDriver launchMobileWebApp(String platform, String deviceName, String browser) {

		DesiredCapabilities caps = new DesiredCapabilities();
		try {
			URL appiumServerURL = new URL("http://127.0.0.1:4723/wd/hub");

			if (PLATFORM_ANDROID.equalsIgnoreCase(platform)) {
				caps.setCapability("device", PLATFORM_ANDROID);
				caps.setCapability("deviceName", deviceName);
				caps.setCapability("browser", browser);
				caps.setCapability("automationName", "UiAutomator2");

				driver = new AppiumDriver(appiumServerURL, caps);

			} else if (PLATFORM_IOS.equalsIgnoreCase(platform)) {

				caps.setCapability("device", PLATFORM_IOS);
				caps.setCapability("deviceName", deviceName);
				caps.setCapability("browser", browser);
				caps.setCapability("automationName", "XCUITest");
				driver = new AppiumDriver(appiumServerURL, caps);

			} else {
				throw new IllegalArgumentException("Unsupported platform: " + platform);
			}
		} catch (Exception e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		return driver;
	}

	public void quitDriver() {
		if (driver instanceof AndroidDriver) {
			((AndroidDriver) driver).quit();
		} else if (driver instanceof IOSDriver) {
			((IOSDriver) driver).quit();
		}
	}

}
