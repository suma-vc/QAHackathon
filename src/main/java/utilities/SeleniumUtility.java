package utilities;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.relevantcodes.extentreports.ExtentTest;

import dev.failsafe.internal.util.Assert;

public class SeleniumUtility {
	private static final Logger logger = LoggerFactory.getLogger(FileUtility.class);
	public static String getCurrentDateTime() {

		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy-HH-mm-ss");

		Date date = new Date();

		String formatedDate = dateFormat.format(date);

		return formatedDate;

	}
	public static String captureScreenshot(WebDriver driver) {

		String screenshotPath = System.getProperty("user.dir") + "/Screenshots/" + SeleniumUtility.getCurrentDateTime()
				+ ".png";

		File dest = new File(screenshotPath);

		TakesScreenshot ts = (TakesScreenshot) driver;

		File src = ts.getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(src, dest);
		} catch (IOException e) {
			System.out.println("ERROR: Unable to capture Screenshot");
			System.out.println("ERROR: " + e.getMessage());
		}

		return screenshotPath;
	}
	
	public static void highLightElement(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {

			System.out.println(e.getMessage());
		}

		js.executeScript("arguments[0].setAttribute('style','border: solid 2px white');", element);

	}
	public static void syncWebElement(WebDriver driver, WebElement element, Duration time) {

		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.visibilityOf(element));
		wait.until(ExpectedConditions.elementToBeClickable(element));

	}
	public static void verifyBrokenLink(String linkurl) {
		URL url = null;
		HttpURLConnection connection = null;

		try {
			url = new URL(linkurl);
		} catch (MalformedURLException e) {
			System.out.println("URL is not configured");
		}

		try {
			connection = (HttpURLConnection) url.openConnection();

			connection.setConnectTimeout(5000);

			connection.connect();

		} catch (IOException e) {

		}

		int code = 0;
		try {
			code = connection.getResponseCode();
		} catch (IOException e) {

		}

		System.out.println("Response Code from Server is " + code);

		if(code==200) {
			System.out.println("Link url is not broken " + linkurl);
		}
		else
			System.out.println("Link url is broken " + linkurl);
		
	}
	public static void verifyMultipleBrokenLink(WebDriver driver, String xpath) {

		URL url = null;
		HttpURLConnection connection = null;

		List<WebElement> allLinks = driver.findElements(By.xpath(xpath));

		System.out.println("Total links to verify " + allLinks.size());

		for (WebElement ele : allLinks) {

			String hrefurl = ele.getAttribute("href");

			System.out.println("Testing link with this url " + hrefurl);

			try {
				url = new URL(hrefurl);
			} catch (MalformedURLException e) {
				System.out.println("URL is not configured");
			}

			try {
				connection = (HttpURLConnection) url.openConnection();

				connection.setConnectTimeout(5000);

				connection.connect();

			} catch (IOException e) {

			}

			int code = 0;
			try {
				code = connection.getResponseCode();
			} catch (IOException e) {

			}

			System.out.println("Response Code from Server is " + code);

			if(code==200) {
				System.out.println("Link url is not broken " + hrefurl);
			}
			else
				System.out.println("Link url is broken " + hrefurl);

		}

		
	}
	/**
	 *  To select the list value from the dropdown using the list value
	 * @param driver - Webdriver
	 * @param locator - Object locator
	 * @param lstValue - List value to be select
	 * @param dropdownName - Dropdown Name
	 */
	public void selectValueFromList(WebDriver driver ,By locator,String lstValue,String dropdownName)
	{
		try
		{			
			WebElement elm = driver.findElement(locator);
			if (!(elm==null))
			{
				Select s = new Select(elm);
				s.selectByValue(lstValue);
				logger.info("Selected the list value : "+ lstValue);
			}
			else		
				logger.error("Element not found : "+ dropdownName);
		}
		catch(Exception e)
		{
			logger.error("Element not found : "+ dropdownName + "Exception thrown is : " + e.getMessage());
		}
	}


	/**
	 *  Get the list values present in the drodown
	 * @param driver - Webdriver 
	 * @param locator -  Object locator
	 * @param lstName - Dropdown name
	 * @return
	 */
	public List<WebElement> getTheDropdownValues(WebDriver driver, By locator, String lstName)
	{
		try
		{
			WebElement elm = driver.findElement(locator);
			Select s = new Select(elm);
			List<WebElement> options = s.getOptions();
			return options;
		}
		catch(NoSuchElementException e)
		{
			logger.error("Element not found : "+ lstName);
			return null;
		}
		catch(Exception e)
		{
			logger.error("Exception : "+ e.getMessage());
			return null;
		}
	}
	/***
	 *  Read the Table Content and prints the data in the console
	 * @param driver - WebDriver
	 * @param locator - Object locator
	 */
	/** Locater have a path of table **/
	// Table/TBODY/TR/TD

	public String[][] displayTableContent(WebDriver driver,By locator)
	{
		String[][] tabledata = null;
		try
		{		
			WebElement tblMain = driver.findElement(locator);
			if (!(tblMain==null))
			{
				WebElement tblbody = tblMain.findElement(By.tagName("TBODY"));

				// Get the Total number of Rows - TR
				List<WebElement> rows = tblbody.findElements(By.tagName("TR"));
				List<WebElement> cols = rows.get(0).findElements(By.tagName("TD"));
				 tabledata = new String[rows.size()][cols.size()];

				for (int i=0;i<rows.size();i++)
				{
					for (int j=0;j<cols.size();j++)
					{
						String cellContent = cols.get(j).getAttribute("text").toString().trim();
						tabledata[i][j]=cellContent;
						System.out.println("The content :"+ i + j + ": "+ cellContent );
					}
				}
			}
			else
				logger.error("Table not found");
		}
		catch(NoSuchElementException e)
		{
			logger.error("Exception occured : "+ e.getMessage());			
		}
		return tabledata;
	}	
	public int getTheColumnPosition(WebDriver driver,By locator,String colName)
	{
		int colPos = -1;
		try
		{
			WebElement tblMain = driver.findElement(locator);
			if (!(tblMain==null))
			{
				WebElement tblbody = tblMain.findElement(By.tagName("TBODY"));

				// Get the Total number of Rows - TR
				List<WebElement> heading = tblbody.findElements(By.tagName("TH"));				

				for (int i=0;i<heading.size();i++)
				{				
					String columnName = heading.get(i).getText();
					System.out.println("The column name is : "+ columnName );
					if (columnName.equalsIgnoreCase(colName))
					{
						colPos = i;
						break;
					}
				}
			}

			if (colPos==-1){
				logger.error("No column found with name : "+ colName);
				return colPos; 
			}

		}
		catch(Exception e)
		{
			logger.error("No colum found");			
		}
		return colPos;
	}
	/**
	 * Clicks on Table column based on the column Index and Searching text 
	 * @param driver - WebDriver
	 * @param locator - Object locator
	 * @param searchText - Searching text
	 * @param colIndex - Column Index 
	 */
	public void clickOnTableColumn(WebDriver driver, By locator,String searchText,String colIndex)
	{
		try
		{
			WebElement tblMain = driver.findElement(locator);
			if (!(tblMain==null))
			{
				WebElement tblbody = tblMain.findElement(By.tagName("TBODY"));

				// Get the Total number of Rows - TR
				List<WebElement> rows = tblbody.findElements(By.tagName("TR"));
				List<WebElement> cols = rows.get(0).findElements(By.tagName("TD"));

				for (int i=0;i<rows.size();i++)
				{
					WebElement rowElm  = rows.get(i);
					WebElement colRequired = rowElm.findElement(By.xpath(".//TD["+colIndex+"]"));

					String cellContent = colRequired.getAttribute("text").toString().trim();
					if (cellContent.equalsIgnoreCase(searchText))
					{
						colRequired.click();						
						logger.info("Clicked on the Row number : "+ i+" with the column : "+ colIndex,driver );
						break;
					}
					System.out.println("The content at row : "+ i +" columm index :" +colIndex +". CellContent is : "+ cellContent);
				}
			}		
			else
				logger.error("Table not found",driver);

		}
		catch(NoSuchElementException e)
		{
			logger.error("No Element found",driver);
		}
		catch(Exception e)
		{
			logger.error("The exception is :" +e.getMessage(),driver);
		}
	}
	/**
	 *  Return the current window handles 
	 * @param driver
	 * @return
	 */

	public String getTheCurrentWindowHandle(WebDriver driver)
	{
		try
		{
			String currentWindowName = driver.getWindowHandle();
			return currentWindowName;
		}
		catch(NoSuchWindowException e)
		{
			logger.error("No window found",driver);
			return null;
		}
	}

	/**
	 *  Switch to other window handles
	 * @param driver - WebDriver 
	 * @param currentWindow  - Window handle, where drive has to switch
	 * @return
	 */
	public boolean switchToNewlyOpenedWindow(WebDriver driver,String currentWindow)
	{
		try
		{			
			Set<String> totalwindows = driver.getWindowHandles();
			for (String window : totalwindows)
			{
				if (!window.equalsIgnoreCase(currentWindow))
				{
					driver.switchTo().window(window);
					logger.info("Successfully navigated to Newly opened window");
					break;
				}
			}			
		}
		catch(NoSuchWindowException e){
			logger.error("No window found with the Window handle",driver);
			return false;
		}		
		return true;		
	}	
	/** 
	 *  Enter the username and password in the Windows based popup for Internet Explorer
	 * @param driver - WebDriver
	 * @param userName - Username
	 * @param password - Password 
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	public void enterTextInWindowsPopup(WebDriver driver,String userName,String password) throws AWTException, InterruptedException
	{
		try
		{
			Alert alert = driver.switchTo().alert();
			alert.sendKeys(userName);			
			logger.info("Entered the username : "+ userName);
			StringSelection stringSelection = new StringSelection(password); 
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null); 
			Robot robot = new Robot(); 							
			robot.keyPress(KeyEvent.VK_TAB); 
			robot.keyPress(KeyEvent.VK_CONTROL); // Copy 
			robot.keyPress(KeyEvent.VK_V); 
			robot.keyRelease(KeyEvent.VK_V); 
			robot.keyRelease(KeyEvent.VK_CONTROL); 
			logger.info("Entered the password : "+ password);
			alert.accept();		
			logger.info("Accepted the Alert");
			Thread.sleep(3000);
		}

		catch(NoAlertPresentException e)
		{			
			logger.error("No Alert Present", driver);
		}

		catch(Exception e)		
		{
			logger.error("Exception occured "+ e.getMessage(), driver);
		}
	}
	public void downloadFile(WebDriver driver) throws AWTException, InterruptedException
	{
		try
		{
				Robot robot = new Robot();        
		        robot.mouseMove(1039,705); // move mouse point to specific location         
		        robot.delay(1500);        // delay is to make code wait for mentioned milliseconds before executing next step    
		        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK); // press left click         
		        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK); // release left click     
		        robot.delay(1500);   
		        captureScreenshot(driver);
		        logger.info("Clicked on File Download", driver);
		       // robot.keyPress(KeyEvent.VK_DOWN); // press keyboard arrow key to select Save radio button        
		             
		        robot.keyPress(KeyEvent.VK_ENTER);  
		        Thread.sleep(2000); 
		        logger.info("Clicked on Save file Button", driver);
		        captureScreenshot(driver);
		        // For Close Button
		        robot.mouseMove(1149,705);
		        robot.delay(1500); 
		        robot.keyPress(KeyEvent.VK_ENTER);  
		        Thread.sleep(3000);
		        logger.info("Closed the windows Save/Download Dialog", driver);
		        // press enter key of keyboard to perform above selected action    
		}
		catch(Exception e)		
		{
			e.printStackTrace();
			logger.error("Exception occured "+ e.getMessage(), driver);
		}
	}
	
	public void scrollIntoViewJS(WebDriver driver, WebElement element) {
		   // Create an instance of JavascriptExecutor
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Scroll the element into view
        js.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	public void scrollIntoViewSpecificOptionsJS(WebDriver driver, WebElement element) {
		// Create an instance of JavascriptExecutor
		JavascriptExecutor js = (JavascriptExecutor) driver;
		 // Scroll the element into view with specific options
		js.executeScript("arguments[0].scrollIntoView({behavior: \"auto\", block: \"center\", inline: \"nearest\"});", element);
	}
	
	public void scrollByPixelsJS(WebDriver driver, int x, int y) {
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("window.scrollBy(arguments[0], arguments[1]);", x, y);
	}
	
	public void scrollToTopJS(WebDriver driver) {
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("window.scrollTo(0, 0);");
	}
	public void scrollToBottomJS(WebDriver driver) {
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}
	
	public String getPageTitleJS(WebDriver driver) {
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    return (String) js.executeScript("return document.title;");
	}
	public void setInputValueJS(WebDriver driver, WebElement element, String value) {
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("arguments[0].value = arguments[1];", element, value);
	}
	public void clickElementJS(WebDriver driver, WebElement element) {
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("arguments[0].click();", element);
	}

	public void highlightElementJS(WebDriver driver, WebElement element) {
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("arguments[0].style.border='3px solid red'", element);
	}
	public String getInnerTextOfElementJS(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
	     String innerText = (String)js.executeScript("return arguments[0].innerText;", element);
		return innerText;
	}

}
