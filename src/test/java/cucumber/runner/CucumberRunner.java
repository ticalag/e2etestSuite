package cucumber.runner;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty",
                "html:target/cucumber/cucumber-html-report",
                "json:target/cucumber/cucumber-json-report.json"
        },
        monochrome = true,
        features = "src/test/resources/cucumber.features",
        glue = {
                "com.twitter.automation.stepdefs"
        }
)
public class CucumberRunner {

}
