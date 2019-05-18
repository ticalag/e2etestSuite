package com.twitter.automation.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class CommonUtils {

    public static final String FAVORITES = "favorites";
    public static final String STATUSES = "statuses";
    public static final String ACCOUNT = "account";

    public static Properties loadPropertiesFile() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("src/test/resources/common.properties")) {
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return properties;
    }

    public static boolean isElementPresent(WebDriver driver, By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public static Boolean waitForUrlToContain(WebDriver driver, String containsText, int specifiedTimeout) {
        WebDriverWait wait = new WebDriverWait(driver, specifiedTimeout);
        return wait.until(ExpectedConditions.urlContains(containsText));
    }

    public static WebElement waitForElement(WebDriver driver, By locator, long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static Boolean waitForElementToDisappear(WebDriver driver, By locator, long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static Boolean waitForTextToBePresentInElement(WebDriver driver, long seconds, By locator, String expectedText) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, expectedText));
    }

    public static Boolean waitForURL(WebDriver driver, String expectedURL, long waitTimeInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, waitTimeInSeconds);
        return wait.until(ExpectedConditions.urlContains(expectedURL.toLowerCase()));
    }

}
