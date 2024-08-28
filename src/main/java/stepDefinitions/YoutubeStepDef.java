package stepDefinitions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.HackathonPageObjects;
import utilities.CommonUtility;


public class YoutubeStepDef {
	WebDriver driver;
	List<String> upcomingVideoNames;
	   String videoName;
	   CommonUtility util=new CommonUtility();
	@Given("User launches the browser {string}")
	public void user_launches_the_browser(String string) {
		   System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");
		    driver = new ChromeDriver();	
		    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		    driver.manage().window().maximize();  
	}

	@When("user navigates to the website {string}")
	public void user_navigates_to_the_website(String url) {
		 driver.get(url);
	}

	@When("Search for {string}")
	public void search_for(String value) {
		  WebElement searchBox = driver.findElement(HackathonPageObjects.searchBox);
          searchBox.sendKeys(value);
     searchBox.sendKeys(org.openqa.selenium.Keys.RETURN);
	}

	@When("Open {string} channel")
	public void open_channel(String channel) {
		  WebElement channelLink = driver.findElement(HackathonPageObjects.channelLink);
          channelLink.click();
	}

	@When("Navigate to {string} tab")
	public void navigate_to_tab(String string) {
		 WebElement videosTab = driver.findElement(HackathonPageObjects.videoTab);
         videosTab.click();
	}

	@When("Make API call to get video name")
	public void make_api_call_to_get_video_name() {
		 String apiUrl = "http://<LAB IP>/video";
         // String videoName = getVideoName(apiUrl);
            videoName = "Panel Discussion - Role of Software in Mobility";
	}

	@When("Locate the video by scrolling")
	public void locate_the_video_by_scrolling() {
		 boolean videoFound = false;
         try {
			while (!videoFound) {
			     List<WebElement> videos = driver.findElements(HackathonPageObjects.videoTitles);
			     for (WebElement video : videos) {
			         if (video.getAttribute("title").contains(videoName)) {
			             video.getLocation(); // Scroll into view
			           
			             ((org.openqa.selenium.JavascriptExecutor) driver)
			                 .executeScript("arguments[0].scrollIntoView(true);", video);
			             Thread.sleep(1000);
			             ((org.openqa.selenium.JavascriptExecutor) driver)
			             .executeScript("arguments[0].scrollIntoView({behavior: \"auto\", block: \"center\", inline: \"nearest\"});", video);
			             // Step 7: Capture the screenshot
			             File screenshot = ((org.openqa.selenium.TakesScreenshot) driver).getScreenshotAs(org.openqa.selenium.OutputType.FILE);
			             String date= util.generateDate();
			             Files.copy(screenshot.toPath(), Paths.get("screenshot.png"),StandardCopyOption.REPLACE_EXISTING);
			             
			             // Step 8: Click on the video
			             video.click();
			             Thread.sleep(5000);  // Wait for video to loadS
			             videoFound = true;
			             break;
			         }
			     }
			     if (!videoFound) {
			         ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("window.scrollBy(0, window.innerHeight);");
			     }
			 }
		} catch (WebDriverException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Then("Capture the screenshot")
	public void capture_the_screenshot() {
		   File screenshot = ((org.openqa.selenium.TakesScreenshot) driver).getScreenshotAs(org.openqa.selenium.OutputType.FILE);
           String date= util.generateDate();
           try {
			Files.copy(screenshot.toPath(), Paths.get("screenshot.png"),StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Then("Validate the file upload Click on the video")
	public void validate_the_file_upload_click_on_the_video() {
	   
	}

	@Then("Change the video quality to {string}")
	public void change_the_video_quality_to(String string) {
		 driver.findElement(HackathonPageObjects.settingsbtn).click();
         
         driver.findElement(HackathonPageObjects.qualityMenu).click();
          
	}

	@When("Get and list names of all upcoming videos")
	public void get_and_list_names_of_all_upcoming_videos() {
		 List<WebElement> upcomingVideos = driver.findElements(HackathonPageObjects.upComingVideoTitles);
          upcomingVideoNames = upcomingVideos.stream()
             .map(WebElement::getText)
             .collect(Collectors.toList());

	}

	@When("Write the data to JSON file")
	public void write_the_data_to_json_file() {
		 try {
			JsonObject jsonObject = new JsonObject();
			 jsonObject.addProperty("team", "team-name");
			 jsonObject.addProperty("video", videoName);
			 JsonArray jsonArray = new JsonArray();
			 for (String upcomingVideoName : upcomingVideoNames) {
			     jsonArray.add(upcomingVideoName);
			 }
			 jsonObject.add("upcoming-videos", jsonArray);
			 
			 FileOutputStream fos = new FileOutputStream("data.json");
			 fos.write(new Gson().toJson(jsonObject).getBytes());
			 fos.close();
			 Gson gson = new GsonBuilder().setPrettyPrinting().create();
			 String json = gson.toJson(jsonObject);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Then("Post\\/upload file to server")
	public void post_upload_file_to_server() {
	    String uploadUrl = "http://<LAB IP>/upload";
        //    String uploadResponse = uploadFile(uploadUrl, "data.json");
	}

	@Then("Validate the file upload")
	public void validate_the_file_upload() {
		  String resultUrl = "http://<LAB IP>/result";
	         //   JsonObject result = validateUpload(resultUrl, uploadResponse);
	           // System.out.println(result);
		  driver.quit();
	}

}
