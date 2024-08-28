package qaHackathon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.CommonUtility;
import utilities.SeleniumUtility;

public class IndianExpresssAutomation {

    public  Map  webAutomation() {
    	Map<String, String> map = new HashMap<>();

    	SeleniumUtility util=new SeleniumUtility();
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "./chromeDriver.exe");

        // Set up Chrome options
        ChromeOptions options = new ChromeOptions();
   //     options.addArguments("--headless"); // Run in headless mode

        // Initialize WebDriver
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

   //     WebDriverWait wait = new WebDriverWait(driver, 10);

        try {
            // Step 1.1: Open the IndianExpress website
            driver.get("https://indianexpress.com");
            driver.manage().window().maximize(); 
            Thread.sleep(5000);
            // Step 1.2: Click on the "India" section link
            WebElement sectionLink =driver.findElement(By.linkText("India"));
            sectionLink.click();
Thread.sleep(3000);
 String currentWindow=util.getTheCurrentWindowHandle(driver);
 util.switchToNewlyOpenedWindow(driver, currentWindow);
 
            // Step 1.3: Find the carousel section
           // WebElement carousel = driver.findElement(By.xpath("//ul[@class='slick-dots']"))  ;
            Thread.sleep(2000);
            // Step 1.4: Extract information from three carousel items
            List<WebElement> carousels = driver.findElements(By.xpath("//li[@role='presentation']//button[contains(@id,'slick-slide-control1')]"));
            int count = 0;

           // for (WebElement item : carousels) {
                carousels = driver.findElements(By.xpath("//li[@role='presentation']//button[contains(@id,'slick-slide-control1')]"));

              //  if (count >= 3) break; // Only process the first three items
                carousels.get(count).click();

                Thread.sleep(1000);
                driver.findElement(By.xpath("//li[contains(@class,'swiper-slide') and @aria-hidden='false']")).click();
                Thread.sleep(3000);
                carousels = driver.findElements(By.xpath("//li[@role='presentation']//button[contains(@id,'slick-slide-control1')]"));
                // Extract Headline
                String headline = driver.findElement(By.xpath("//*[contains(@class, 'native_story_title')]")).getText();
                // Extract News Link
                String link = driver.getCurrentUrl();
                // Extract Date and Time
                String dateTimeText = driver.findElement(By.xpath("//span[@itemprop='dateModified']")).getText().split(":")[1];
System.out.println(dateTimeText);
                // Format Date and Time (Modify format if needed)

//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy HH:mm z");
//LocalDateTime dateTime = LocalDateTime.parse(dateTimeText, formatter);
//System.out.println(dateTime);
         

                // Print the results
                System.out.println("Headline: " + headline);
                System.out.println("News Link: " + link);
                System.out.println("Date and Time of Publication: " + dateTimeText);
                System.out.println("Team Name: YourTeamName"); // Replace with your team's name
                System.out.println();
            	map.put("headline", headline);
    			map.put("link", link);
    			map.put("dateTimet", dateTimeText);
                count++;
                driver.navigate().back();
       //     }
                

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
		return map;
    }
}
