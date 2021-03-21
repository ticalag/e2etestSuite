package com.twitter.automation.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.twitter.automation.step.definitions.CucumberHooks.getDriver;


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

    public static boolean isElementDisplayed(By element) {
        try {
            return getDriver().findElement(element).isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }


    public static void waitForUrlContains(String containsText, int specifiedTimeout) {
        WebDriverWait wait = new WebDriverWait(getDriver(), specifiedTimeout);
        wait.until(ExpectedConditions.urlContains(containsText));
    }

    public static void waitForElement(By locator, long timeOutInSeconds) {
        waitForVisibilityOfElement(timeOutInSeconds, locator);
    }

    public static void waitForNumberOfElements(By locator, long timeOutInSeconds, int numberOfExpectedElements) {
        waitForNumberOfElements(timeOutInSeconds, locator, numberOfExpectedElements);
    }


    public static void waitForVisibilityOfElement(long seconds, By locator) {
        WebDriverWait wait = new WebDriverWait(getDriver(), seconds);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void waitForNumberOfElements(long seconds, By locator, int expectNumberOfElements) {
        WebDriverWait wait = new WebDriverWait(getDriver(), seconds);
        wait.until(ExpectedConditions.numberOfElementsToBe(locator, expectNumberOfElements));
    }

    public static void waitForElementToDisappear(By locator, long timeOutInSeconds) {
        waitForInvisibilityOfElement(timeOutInSeconds, locator);
    }

    public static void waitForInvisibilityOfElement(long seconds, By locator) {
        WebDriverWait wait = new WebDriverWait(getDriver(), seconds);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static void waitForTextToBePresentInElement(long seconds, By locator, String expectedText) {
        WebDriverWait wait = new WebDriverWait(getDriver(), seconds);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, expectedText));
    }

    public static Boolean waitForURL(String expectedURL, long waitTimeInSeconds) {
        return waitForURLToContain(waitTimeInSeconds, expectedURL.toLowerCase());
    }

    public static Boolean waitForURLToContain(long seconds, String expectedURL) {
        WebDriverWait wait = new WebDriverWait(getDriver(), seconds);
        return wait.until(ExpectedConditions.urlContains(expectedURL));
    }



}
