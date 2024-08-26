package utilities;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.model.Media;

/**
 * LoggerUtility is a utility class that provides static methods for logging messages with different statuses.
 */
public class Logger_v5Utility {
	private static final Logger logger = LoggerFactory.getLogger(Logger_v5Utility.class);

	/**
	 * Logs a message with a PASS status.
	 *
	 * @param extentLogger The ExtentTest logger to use for logging.
	 * @param msg The message to log.
	 */
	public static void logPass(ExtentTest extentLogger, String msg) {
		try {
			extentLogger.log(Status.PASS, msg);
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
			extentLogger.log(Status.INFO, msg);
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
			extentLogger.log(Status.FAIL, msg);
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
	public static void logPass(ExtentTest logger,String msg,WebDriver driver)
	{
		String screeshotPath = SeleniumUtility.captureScreenshot(driver);
		Media media = 
				MediaEntityBuilder.createScreenCaptureFromPath(screeshotPath).build();
		logger.log(Status.PASS, msg,media);
		//logger.addScreenCaptureFromPath(screeshotPath);
	}
	/**
	 * Prints the custom message in the Extend reports output with screenshot - Info
	 * @param logger- Extend logger
	 * @param msg  - Message
	 * @param driver - WebDriver
	 */
	public static void logInfo(ExtentTest logger,String msg,WebDriver driver)
	{
		String screeshotPath = SeleniumUtility.captureScreenshot(driver);
		Media media = 
				MediaEntityBuilder.createScreenCaptureFromPath(screeshotPath).build();
		logger.log(Status.INFO, msg,media);
	}
	/**
	 * Prints the custom message in the Extend reports output with screenshot - Fail
	 * @param logger- Extend logger
	 * @param msg  - Message
	 * @param driver - WebDriver
	 */
	public static void logFail(ExtentTest logger,String msg, WebDriver driver)
	{
		String screeshotPath = SeleniumUtility.captureScreenshot(driver);
		Media media = 
				MediaEntityBuilder.createScreenCaptureFromPath(screeshotPath).build();
		logger.log(Status.FAIL, msg,media);		
	}	
	public static void logPassMarkupJSON(ExtentTest logger,String msg)
	{
	
		
		logger.log(Status.PASS, MarkupHelper.createCodeBlock(msg, CodeLanguage.JSON));
				
	}	
	public static void logPassMarkupXML(ExtentTest logger,String msg)
	{
		
		
		logger.log(Status.PASS, MarkupHelper.createCodeBlock(msg, CodeLanguage.XML));
		
	}	
	public static void logPassMarkupTable(ExtentTest logger,String data[][])
	{
		
		
		logger.log(Status.PASS, MarkupHelper.createTable(data));
		
	}	

}