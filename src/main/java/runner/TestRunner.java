package runner;

import java.text.SimpleDateFormat;
import java.util.Calendar;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import io.cucumber.core.cli.Main;


public class TestRunner {

	public static ExtentTest logger;

	public static ExtentReports reports;


	public static void main(String[] args) {
		try {

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());


				Main.run(new String[] { "classpath:features", "-g", "stepDefinitions", "-g", "runner",
						"-t","@youtubeAutomation",
						"-p", "pretty",
						"-p", "html:target/cucumber-reports.html",
						"-m"
				}

						, Thread.currentThread().getContextClassLoader());
				//reports.flush();

				System.out.println("Reports done");
			
		} catch (Throwable e) {
			System.out.println("Reports not done");
			e.printStackTrace();
		}

	}
}
