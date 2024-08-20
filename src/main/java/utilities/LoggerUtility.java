package utilities;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LoggerUtility is a utility class that provides static methods for logging messages with different statuses.
 */
public class LoggerUtility {
	private static final Logger logger = LoggerFactory.getLogger(LoggerUtility.class);

	/**
	 * Logs a message with a PASS status.
	 *
	 * @param extentLogger The ExtentTest logger to use for logging.
	 * @param msg The message to log.
	 */
	public static void logPass(ExtentTest extentLogger, String msg) {
		try {
			extentLogger.log(LogStatus.PASS, msg);
		} catch (Exception e) {
			logger.error("Error logging PASS message: ", e);
		}
	}
	

	/**
	 * Logs a message with an INFO status.
	 *
	 * @param extentLogger The ExtentTest logger to use for logging.
	 * @param msg The message to log.
	 */
	public static void logInfo(ExtentTest extentLogger, String msg) {
		try {
			extentLogger.log(LogStatus.INFO, msg);
		} catch (Exception e) {
			logger.error("Error logging INFO message: ", e);
		}
	}

	/**
	 * Logs a message with a FAIL status.
	 *
	 * @param extentLogger The ExtentTest logger to use for logging.
	 * @param msg The message to log.
	 */
	public static void logFail(ExtentTest extentLogger, String msg) {
		try {
			extentLogger.log(LogStatus.FAIL, msg);
		} catch (Exception e) {
			logger.error("Error logging FAIL message: ", e);
		}
	}
	/**
	 * Prints the custom message in the Extend reports output with screenshot - Pass
	 * @param logger- Extend logger
	 * @param msg  - Message
	 * @param driver - WebDriver
	 */
	public void logPass(ExtentTest logger,String msg,WebDriver driver)
	{
		String screeshotPath = SeleniumUtility.captureScreenshot(driver);
		String image = logger.addScreenCapture(screeshotPath);
		logger.log(LogStatus.PASS, msg,image);
	}
	/**
	 * Prints the custom message in the Extend reports output with screenshot - Info
	 * @param logger- Extend logger
	 * @param msg  - Message
	 * @param driver - WebDriver
	 */
	public void logInfo(ExtentTest logger,String msg,WebDriver driver)
	{
		String screeshotPath = SeleniumUtility.captureScreenshot(driver);
		String image = logger.addScreenCapture(screeshotPath);
		logger.log(LogStatus.INFO, msg,image);
	}
	/**
	 * Prints the custom message in the Extend reports output with screenshot - Fail
	 * @param logger- Extend logger
	 * @param msg  - Message
	 * @param driver - WebDriver
	 */
	public void logFail(ExtentTest logger,String msg, WebDriver driver)
	{
		String screeshotPath = SeleniumUtility.captureScreenshot(driver);
		String image = logger.addScreenCapture(screeshotPath);		
		logger.log(LogStatus.FAIL, msg,image);		
	}		

}