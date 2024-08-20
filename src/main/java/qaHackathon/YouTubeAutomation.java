package qaHackathon;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import utilities.CommonUtility;

import com.google.gson.JsonArray;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.apache.commons.io.IOUtils;
import java.nio.charset.StandardCharsets; // Added import statement



public class YouTubeAutomation {
    public static void main(String[] args) {
    	CommonUtility util=new CommonUtility();
    	
        System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");

       // ChromeOptions options = new ChromeOptions();
    //    options.addArguments("--headless");  // Run in headless mode
        
     //   WebDriver driver = new ChromeDriver(options);	
        WebDriver driver = new ChromeDriver();	
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        
        try {
            // Step 1: Open YouTube
            driver.get("https://www.youtube.com");
            driver.manage().window().maximize();            
            // Step 2: Search for 'step-inforum'
            WebElement searchBox = driver.findElement(By.name("search_query"));
            searchBox.sendKeys("step-inforum");
            searchBox.sendKeys(org.openqa.selenium.Keys.RETURN);
            
            // Step 3: Open 'step-in forum' channel
            WebElement channelLink = driver.findElement(By.partialLinkText("STeP-IN Forum"));
            channelLink.click();
            
            // Step 4: Navigate to 'Videos' tab
            WebElement videosTab = driver.findElement(By.xpath("//div[text()=\"Videos\"]"));
            videosTab.click();
            
            // Step 5: Make API call to get video name
            String apiUrl = "http://<LAB IP>/video";
           // String videoName = getVideoName(apiUrl);
             String videoName = "Panel Discussion - Role of Software in Mobility";
            
            
            // Step 6: Locate the video by scrolling
            boolean videoFound = false;
            while (!videoFound) {
                List<WebElement> videos = driver.findElements(By.id("video-title-link"));
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
            
            // Step 9: Change the video quality to 360p
            WebElement settingsButton = driver.findElement(By.xpath("//button[@title='Settings']"));
            settingsButton.click();
            WebElement qualityMenu = driver.findElement(By.xpath("//div[contains(text(), 'Quality')]"));
            qualityMenu.click();
            Thread.sleep(2000);
            WebElement quality360p = driver.findElement(By.xpath("//span[text()='360p']"));
            quality360p.click();
            
            // Step 10: Get and list names of all upcoming videos
            List<WebElement> upcomingVideos = driver.findElements(By.id("video-title"));
            List<String> upcomingVideoNames = upcomingVideos.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
            
            // Step 11: Write the data to JSON file
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
            
            // Step 12: Post/upload file to server
            String uploadUrl = "http://<LAB IP>/upload";
            String uploadResponse = uploadFile(uploadUrl, "data.json");
            
            // Step 13: Validate the file upload
            String resultUrl = "http://<LAB IP>/result";
            JsonObject result = validateUpload(resultUrl, uploadResponse);
            System.out.println(result);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           // driver.quit();
        }
    }
    
    
  

 // ...

 // Helper method to get video name from API
 private static String getVideoName(String apiUrl) throws IOException {
     CloseableHttpClient httpClient = HttpClients.createDefault();
     HttpGet request = new HttpGet(apiUrl);
     CloseableHttpResponse response = httpClient.execute(request);
     String responseBody = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
     response.close();
     httpClient.close();

     JsonObject jsonResponse = new Gson().fromJson(responseBody, JsonObject.class);
     return jsonResponse.get("video_name").getAsString();
 }

 // Helper method to upload file
 private static String uploadFile(String uploadUrl, String filePath) throws IOException {
     CloseableHttpClient httpClient = HttpClients.createDefault();
     HttpPost uploadFile = new HttpPost(uploadUrl);
     File file = new File(filePath);
     FileEntity fileEntity = new FileEntity(file, ContentType.APPLICATION_OCTET_STREAM);
     uploadFile.setEntity(fileEntity);
     CloseableHttpResponse response = httpClient.execute(uploadFile);
     String responseBody = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
     response.close();
     httpClient.close();

     JsonObject jsonResponse = new Gson().fromJson(responseBody, JsonObject.class);
     return jsonResponse.get("response").getAsString();
 }

 // Helper method to validate upload
 private static JsonObject validateUpload(String resultUrl, String uploadResponse) throws IOException {
     CloseableHttpClient httpClient = HttpClients.createDefault();
     HttpGet request = new HttpGet(resultUrl + "/" + uploadResponse);
     CloseableHttpResponse response = httpClient.execute(request);
     String responseBody = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
     response.close();
     httpClient.close();

     return new Gson().fromJson(responseBody, JsonObject.class);
 }
}

