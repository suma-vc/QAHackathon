package driverMethods;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;



public class WebDrivers {
	public WebDriver driver;
	   private String URL = "http://www.calculator.net";
	   
	   public void setup(String browser) throws Exception {
		   if (browser.equalsIgnoreCase("firefox")) {
		         System.out.println(" Executing on FireFox");
		         driver = new FirefoxDriver();
		         driver.get(URL);

		         driver.manage().window().maximize();
		      } else if (browser.equalsIgnoreCase("chrome")) {
		         System.out.println(" Executing on CHROME");
		         System.out.println("Executing on IE");
		         System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver.exe");
		         driver = new ChromeDriver();
		         driver.get(URL);

		         driver.manage().window().maximize();
		      } else if (browser.equalsIgnoreCase("ie")) {
		         System.out.println("Executing on IE");
		         System.setProperty("webdriver.ie.driver", "./Drivers/IEDriverServer.exe");
		         driver = new InternetExplorerDriver();
		         driver.get(URL);
		         driver.manage().window().maximize();
		      } else {
		         throw new IllegalArgumentException("The Browser Type is Undefined");
		      }   
	   }
	
}
