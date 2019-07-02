
import java.io.File;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import com.cucumber.listener.Reporter;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;
import cucumber.api.CucumberOptions;

@RunWith(ExtendedCucumber.class)
@ExtendedCucumberOptions(jsonReport = "target/cucumber.json",
        retryCount = 0, //To rerun the scenario , if the scenario fails 
        detailedReport = true,
        detailedAggregatedReport = true,
        overviewReport = true,
        //coverageReport = true,
        screenShotLocation="FailureScreenShots/",
        screenShotSize="700px",
        jsonUsageReport = "target/cucumber-usage.json",
        usageReport = true,
        toPDF = true,
        excludeCoverageTags = {"@flaky" },
        includeCoverageTags = {"@passed" },
        outputFolder = "target/")


@CucumberOptions
		(
			plugin = { "html:target/cucumber-html-report","json:target/cucumber.json", "pretty:target/cucumber-pretty.txt","usage:target/cucumber-usage.json", "junit:target/cucumber-results.xml" , "com.cucumber.listener.ExtentCucumberFormatter:target/CucumberExtentReport.html" },
			features = { "./src/test/java/features" },
			glue = { "step_definitions","helpers"},
			//tags = {"@BoostDigital_CreateNewProposal_SocialClic"},
			tags = {"@BoostDigital_CreateNewProposal_NGPack1"},
			monochrome = true,
			dryRun = false //To generate the steps that are not implemented
		)
public class CucumberRunnerTest {
    @AfterClass
    public static void setup() {
        Reporter.loadXMLConfig(new File("src/test/resources/extent-config.xml"));
        Reporter.setSystemInfo("user", System.getProperty("user.name"));
        Reporter.setSystemInfo("os", "Windows");
        Reporter.setTestRunnerOutput("Sample test runner output message");
    }
}
