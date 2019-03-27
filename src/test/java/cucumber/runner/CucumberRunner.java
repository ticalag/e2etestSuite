package cucumber.runner;


import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
        plugin = {"pretty",
                "html:target/cucumber/cucumber-html-report",
                "json:target/cucumber/cucumber-json-report.json"
        },
        monochrome = true,
        features = "src/test/resources/cucumber.features",
        tags = "@smoke-tests",
        glue = {
                "com.twitter.automation.stepdefs"
        }
)

public class CucumberRunner extends AbstractTestNGCucumberTests {

}
