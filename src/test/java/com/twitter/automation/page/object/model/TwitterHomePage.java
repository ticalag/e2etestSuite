package com.twitter.automation.page.object.model;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.twitter.automation.stepdefs.HomePageSteps.setActualUnselectedFavoriteIcons;
import static com.twitter.automation.stepdefs.HomePageSteps.setInitialUnselectedFavoriteIcons;
import static com.twitter.automation.utils.CommonUtils.*;


public class TwitterHomePage {


    private final By LOGGED_IN = By.cssSelector("body.logged-in");
    private final By SIGN_UP_BUTTON = By.cssSelector(".StaticLoggedOutHomePage-buttonSignup");
    private final By LOGIN_FORM = By.cssSelector(".LoginForm");
    private final By SIGN_IN_EMAIL = By.cssSelector(".StaticLoggedOutHomePage-login .js-signin-email");
    private final By SIGN_IN_PASSWORD = By.cssSelector(".StaticLoggedOutHomePage-login .LoginForm-password .text-input");
    private final By LOG_IN_BUTTON = By.cssSelector(".StaticLoggedOutHomePage-login .js-submit");
    private final By USER_SETTINGS_BUTTON = By.cssSelector("#user-dropdown .settings");
    private final By USER_SIGN_OUT_BUTTON = By.cssSelector(".dropdown-menu #signout-button");
    private final By TOP_BAR_COMPONENT = By.cssSelector(".topbar.js-topbar");
    private final By LEFT_DASHBOARD = By.cssSelector("#page-container .dashboard-left");
    private final By RIGHT_DASHBOARD = By.cssSelector("#page-container .dashboard-right");
    private final By TWEETS_CONTAINER = By.cssSelector(".top-timeline-tweetbox");
    private final By TOP_NAV_TWEET_BUTTON = By.cssSelector(".global-nav-inner #global-new-tweet-button");
    private final By TWEET_COMPOSE_MODAL = By.cssSelector(".modal .TweetstormDialog-content");
    private final By TWEET_BOX_CONTENT = By.cssSelector("#Tweetstorm-tweet-box-0 .RichEditor-scrollContainer .tweet-box.rich-editor");
    private final By TWEET_BOX = By.cssSelector("#Tweetstorm-tweet-box-0  div.tweet-box.rich-editor > div");
    private final By SEND_TWEET_BUTTON = By.cssSelector("#Tweetstorm-tweet-box-0 .js-send-tweets");
    private final By TWEET_ALERT_MESSAGE = By.cssSelector(".js-message-drawer-visible");
    private final By NEW_TWEET_BAR = By.cssSelector(".js-new-tweets-bar");
    private final By TWEET_CARET_BUTTON = By.cssSelector(".content .js-tooltip .Icon--small");
    private final By DELETE_TWEET_BUTTON_FROM_DROPDOWN = By.cssSelector(".my-tweet.dismissible-content.focus .js-actionDelete");
    private final By DELETE_TWEET_MODAL = By.cssSelector("#delete-tweet-dialog-dialog > div.modal-content");
    private final By DELETE_TWEET_BUTTON_FROM_MODAL = By.cssSelector("#delete-tweet-dialog-dialog .delete-action");
    private final By TWEET_REPLY_ICON = By.cssSelector(".ProfileTweet-action--reply .Icon--reply");
    private final By TWEET_REPLY_MODAL = By.cssSelector("#global-tweet-dialog-dialog > div.modal-content");
    private final By TWEET_REPLY_BOX = By.cssSelector("#tweet-box-global");
    private final By TWEET_REPLY_BUTTON = By.cssSelector("#global-tweet-dialog-dialog .tweet-button > button");
    private final By UNSELECTED_FAVORITE_ICON = By.cssSelector("div[class*='my-tweet']:not([class*='favorited']) [title='Like']");
    private final By SELECTED_FAVORITE_ICONS = By.cssSelector(".my-tweet.favorited [data-original-title='Undo like']");
    private final By HOME_NAVIGATION_TAB = By.cssSelector(".js-global-actions .home");
    private final By MOMENTS_NAVIGATION_TAB = By.cssSelector(".js-global-actions .moments");
    private final By NOTIFICATIONS_NAVIGATION_TAB = By.cssSelector(".js-global-actions .notifications");
    private final By MESSAGES_NAVIGATION_TAB = By.cssSelector(".js-global-actions .dm-nav");
    private final By EMOJI_ICON = By.cssSelector("#Tweetstorm-tweet-box-0 .Icon.Icon--smiley");
    private final By EMOJI_CONTENT = By.cssSelector("#Tweetstorm-tweet-box-0 .EmojiPicker-content");
    private final By USER_TARGET_LINK = By.cssSelector(".account-group .u-linkComplex-target");
    private final By USER_TIMELINE_RIGHT_SIDEBAR = By.cssSelector(".SidebarCommonModules");

    private final String BASE_URL = loadPropertiesFile().getProperty("twitter.url");
    private final String USERNAME = loadPropertiesFile().getProperty("username");
    private final String PASSWORD = loadPropertiesFile().getProperty("password");


    private final Logger LOGGER = LoggerFactory.getLogger(TwitterHomePage.class);
    private WebDriver driver;

    private List<WebElement> LIST_OF_TWEET_TEXT;

    public TwitterHomePage(WebDriver driver) {
        this.driver = driver;

    }

    public void navigateToTwitterHomePage() {
        LOGGER.info("navigated to {}", BASE_URL);
        driver.get(BASE_URL);
    }

    public void navigateToUserTimeline() {
        driver.findElement(USER_TARGET_LINK).click();
        waitForElement(driver, USER_TIMELINE_RIGHT_SIDEBAR, 10);
    }

    public boolean isUserLoggedIn() {
        return isElementPresent(driver, LOGGED_IN);
    }

    public boolean isTopBarDisplayed() {
        return driver.findElement(TOP_BAR_COMPONENT).isDisplayed();
    }

    public boolean isLeftDashboardDisplayed() {
        return driver.findElement(LEFT_DASHBOARD).isDisplayed();
    }

    public boolean isRightDashboardDisplayed() {
        return driver.findElement(RIGHT_DASHBOARD).isDisplayed();
    }

    public boolean isTweetsContainerDisplayed() {
        return driver.findElement(TWEETS_CONTAINER).isDisplayed();
    }

    public boolean isTopNavTweetButtonDisplayed() {
        return driver.findElement(TOP_NAV_TWEET_BUTTON).isDisplayed();
    }

    public boolean isSignUpButtonDisplayed() {
        return driver.findElement(SIGN_UP_BUTTON).isDisplayed();
    }

    public boolean isLoginFormDisplayed() {
        return driver.findElement(LOGIN_FORM).isDisplayed();
    }

    public void clickOnTopNavTweetButton() {
        driver.findElement(TOP_NAV_TWEET_BUTTON).click();
    }


    public void login() {
        driver.findElement(SIGN_IN_EMAIL).sendKeys(USERNAME);
        driver.findElement(SIGN_IN_PASSWORD).sendKeys(PASSWORD);
        driver.findElement(LOG_IN_BUTTON).click();
    }

    public void logout() {
        driver.findElement(USER_SETTINGS_BUTTON).click();
        driver.findElement(USER_SIGN_OUT_BUTTON).click();
        waitForUrlContains(driver, "logout", 2);

    }

    public String getTweetTextFromPosition(int positionNumber) {
        LIST_OF_TWEET_TEXT = driver.findElements(By.cssSelector(".stream-items .tweet-text"));
        return LIST_OF_TWEET_TEXT.get(positionNumber - 1).getText();
    }


    private void enterTextIntoElement(By locator, String tweetMessage) {
        for (int index = 0; index < tweetMessage.length(); index++) {
            char character = tweetMessage.charAt(index);
            driver.findElement(locator).sendKeys(String.valueOf(character));
        }
    }

    public void clickOnNewTweetBar() {
        waitForElement(driver, NEW_TWEET_BAR, 60);
        driver.findElement(NEW_TWEET_BAR).click();
    }

    public void deleteTweetFromUI(int tweetPosition) {
        driver.findElements(TWEET_CARET_BUTTON).get(tweetPosition - 1).click();
        driver.findElement(DELETE_TWEET_BUTTON_FROM_DROPDOWN).click();
        waitForElement(driver, DELETE_TWEET_MODAL, 3);
        driver.findElement(DELETE_TWEET_BUTTON_FROM_MODAL).click();
        waitForElement(driver, TWEET_ALERT_MESSAGE, 5);
        waitForElementToDisappear(driver, TWEET_ALERT_MESSAGE, 10);
    }

    public void replyToTweet(int tweetPosition, String tweetReply) {
        driver.findElements(TWEET_REPLY_ICON).get(tweetPosition - 1).click();
        waitForElement(driver, TWEET_REPLY_MODAL, 2);
        enterTextIntoElement(TWEET_REPLY_BOX, tweetReply);
        waitForTextToBePresentInElement(driver, 2, TWEET_REPLY_BOX, tweetReply);
        driver.findElement(TWEET_REPLY_BUTTON).click();
        waitForElement(driver, TWEET_ALERT_MESSAGE, 2);
        waitForElementToDisappear(driver, TWEET_ALERT_MESSAGE, 5);
    }

    public void addTweetToFavorite(int tweetPosition) {
        setInitialUnselectedFavoriteIcons(driver.findElements(UNSELECTED_FAVORITE_ICON).size());
        driver.findElements(UNSELECTED_FAVORITE_ICON).get(tweetPosition - 1).click();
        waitForElement(driver, SELECTED_FAVORITE_ICONS, 3);
        setActualUnselectedFavoriteIcons(driver.findElements(UNSELECTED_FAVORITE_ICON).size());
    }

    public boolean isFavoriteTweet(int tweetPosition) {
        return driver.findElements(SELECTED_FAVORITE_ICONS).get(tweetPosition - 1).isDisplayed();
    }


    public void clickOnSelectedNavigationButton(String navigationTabName) {
        switch (navigationTabName.toLowerCase()) {
            case "home":
                driver.findElement(HOME_NAVIGATION_TAB).click();
                break;
            case "moments":
                driver.findElement(MOMENTS_NAVIGATION_TAB).click();
                break;
            case "notifications":
                driver.findElement(NOTIFICATIONS_NAVIGATION_TAB).click();
                break;
            case "messages":
                driver.findElement(MESSAGES_NAVIGATION_TAB).click();
                break;
            default:
        }
    }

    public Boolean isNavigationTabSelected(String navigationTabName) {
        boolean isTabSelected = false;
        switch (navigationTabName.toLowerCase()) {
            case "home":
                isTabSelected = driver.findElement(HOME_NAVIGATION_TAB).getAttribute("class").contains("active");
                break;
            case "moments":
                isTabSelected = driver.findElement(MOMENTS_NAVIGATION_TAB).getAttribute("class").contains("active");
                break;
            case "notifications":
                isTabSelected = driver.findElement(NOTIFICATIONS_NAVIGATION_TAB).getAttribute("class").contains("active");
                break;
            case "messages":
                isTabSelected = driver.findElement(MESSAGES_NAVIGATION_TAB).getAttribute("class").contains("active");
                break;
        }
        return isTabSelected;
    }

    public void clickOnEmojiMenu() {
        driver.findElement(EMOJI_ICON).click();
    }

    public boolean isEmojiContentDisplayed() {
        return driver.findElement(EMOJI_CONTENT).isDisplayed();
    }

    public void enterTweetMessageIntoModal(String tweetMessage) {
        waitForElement(driver, TWEET_COMPOSE_MODAL, 3);
        enterTextIntoElement(TWEET_BOX_CONTENT, tweetMessage);
        waitForTextToBePresentInElement(driver, 3, TWEET_BOX, tweetMessage);
    }

    public void clickAndWaitForTweetToAppear() {
        driver.findElement(SEND_TWEET_BUTTON).click();
        waitForElement(driver, TWEET_ALERT_MESSAGE, 15);
        waitForElementToDisappear(driver, TWEET_ALERT_MESSAGE, 15);
    }

}
