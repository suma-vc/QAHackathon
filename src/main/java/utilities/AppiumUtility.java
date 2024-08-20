package utilities;

import java.util.HashMap;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;



public class AppiumUtility {
	private static final Logger logger = LoggerFactory.getLogger(FileUtility.class);
	public void scrollToAnElement(WebElement ele, WebDriver driver) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
		} catch (Exception e) {
			logger.error( e.toString());
		}

	}

	public boolean swipeFromUpToBottom(WebDriver driver) {

		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			HashMap<String, String> scrollObject = new HashMap<String, String>();
			scrollObject.put("direction", "up");
			js.executeScript("mobile: scroll", scrollObject);
			System.out.println("Swipe up was Successfully done.");
		} catch (Exception e) {
			System.out.println("swipe up was not successfull" + e);
		}
		return false;
	}
	/**
	 * Double Tap At given Location on screen
	 * 
	 * @param driver
	 *            - AndroidDriver<WebElement>
	 * @param x
	 *            - x axis coordinate
	 * @param y
	 *            - y axis coordinate
	 */
	public void doubleTapAtLocation(AndroidDriver<WebElement> driver, int x, int y) {
		TouchAction touchAction = new TouchAction(driver);
		touchAction.tap(PointOption.point(x, y)).perform();
		touchAction.tap(PointOption.point(x, y)).perform();

	}
}
