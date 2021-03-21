package com.twitter.automation.step.definitions;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;

import java.io.File;
import java.util.Base64;

public class CucumberHooks implements ConcurrentEventListener {

    private static WebDriver driver;

    @Before("@browser")
    public void setUp() {
        initializeSelectedDriver();
        driver.manage().window().maximize();
    }

    @After(order = 0)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }

    @After(order = 1)
    public void takeScreenshotOfFailedScenario(Scenario scenario) {
        String base64StringOfScreenshot;
        if (scenario.isFailed()) {
            try {
                File takeScreenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
                byte[] byteScreenshot = FileUtils.readFileToByteArray(takeScreenshot);
                base64StringOfScreenshot = "data:image/png;base64," + Base64.getEncoder().encodeToString(byteScreenshot);
                ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(base64StringOfScreenshot);
                scenario.log("Screenshot attached for above failed step");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initializeSelectedDriver() {
        switch (System.getProperty("Browser").toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            case "opera":
                WebDriverManager.operadriver().setup();
                driver = new OperaDriver();
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
        }
    }

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {

    }
}
