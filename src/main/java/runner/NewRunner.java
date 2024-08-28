package runner;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",        // Path to your feature files
        glue = "stepDefinitions",               // Package where your step definitions are located
        plugin = {"pretty", "html:target/cucumber-reports"} // Plugins for generating reports
      //  monochrome = true,                      // Makes the console output more readable
      //  tags = "@YourTag"                    // Run scenarios tagged with @YourTag (optional)
      //  strict = true                           // Fail the run if there are undefined or pending steps
)

public class NewRunner {
}
