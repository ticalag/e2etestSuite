package cucumber.runner;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import javax.annotation.Nonnull;


@CucumberOptions(
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:", "json:target/cucumber-reports/extent-report.json"},
        monochrome = true,
        strict = true,
        features = "src/test/resources/features",
        tags = "@regression",
        glue = {
                "com.twitter.automation.step.definitions"
        }
)

public class CucumberRunner extends AbstractTestNGCucumberTests {

    @DataProvider()
    @Override
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @BeforeClass(alwaysRun = true)
    @Parameters({"Browser"})
    public void setBrowser(@Nonnull String browserName) {
        System.setProperty("Browser", browserName);
    }

}