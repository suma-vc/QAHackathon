package runner;

import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import qaHackathon.AppiumAutomation;
import qaHackathon.IndianExpresssAutomation;
import utilities.Logger_v5Utility;

public class MainCodeHackathon {
	public static ExtentSparkReporter extentSparkReporter;
	public static ExtentTest logger;
	
	public static ExtentReports extentReports;

	public static void main(String[] args) {
		 extentSparkReporter  = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/extentReport.html");
         extentReports = new ExtentReports();
         extentReports.attachReporter(extentSparkReporter);
         logger = extentReports.createTest("IndianExpress Hackathon Automation");
         
		AppiumAutomation appium=new AppiumAutomation();
	//	IndianExpresssAutomation webAuto=new IndianExpresssAutomation();
		
		Map<String, String> mapAppium = new HashMap<>();
		Map<String, String> mapWeb = new HashMap<>();
		
		try {
		//	mapWeb=webAuto.webAutomation();

     //       Logger_v5Utility.logPass(logger, "Indian express data: "+mapWeb);

			mapAppium=	appium.AppiumTest();
			  Logger_v5Utility.logPass(logger, "Appium mobile data: "+mapAppium);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  finally {
         // driver.quit();
      }

        extentReports.flush();

	}

}
