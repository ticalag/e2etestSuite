package com.twitter.automation.ui;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static com.twitter.automation.api.TwitterAPITests.getNumberTweetsFromHomeTimeline;
import static com.twitter.automation.stepdefs.HomePageSteps.getDriver;


public class TwitterHomePageTests {

    private static final By LOGGED_IN = By.cssSelector("body.logged-in");
    private static final By SIGN_UP_BUTTON = By.cssSelector(".StaticLoggedOutHomePage-buttonSignup");
    private static final By LOGIN_FORM = By.cssSelector(".LoginForm");
    private static final By SIGN_IN_EMAIL = By.cssSelector(".StaticLoggedOutHomePage-login .js-signin-email");
    private static final By SIGN_IN_PASSWORD = By.cssSelector(".StaticLoggedOutHomePage-login .LoginForm-password .text-input");
    private static final By LOG_IN_BUTTON = By.cssSelector(".StaticLoggedOutHomePage-login .js-submit");
    private static final By USER_SETTINGS_BUTTON = By.cssSelector("#user-dropdown .settings");
    private static final By USER_SIGN_OUT_BUTTON = By.cssSelector(".dropdown-menu #signout-button");
    private static final By TOP_BAR_COMPONENT = By.cssSelector(".topbar.js-topbar");
    private static final By LEFT_DASHBOARD = By.cssSelector("#page-container .dashboard-left");
    private static final By RIGHT_DASHBOARD = By.cssSelector("#page-container .dashboard-right");
    private static final By TWEETS_CONTAINER = By.cssSelector(".top-timeline-tweetbox");
    private static final By TOP_NAV_TWEET_BUTTON = By.cssSelector(".global-nav-inner #global-new-tweet-button");
    private static final By TWEET_COMPOSE_MODAL = By.cssSelector(".modal .TweetstormDialog-content");
    private static final By TWEET_BOX_CONTENT = By.cssSelector("#Tweetstorm-tweet-box-0 .RichEditor-scrollContainer .tweet-box.rich-editor");
    private static final By TWEET_BOX = By.cssSelector("#Tweetstorm-tweet-box-0  div.tweet-box.rich-editor > div");
    private static final By SEND_TWEET_BUTTON = By.cssSelector("#Tweetstorm-tweet-box-0 .js-send-tweets");
    private static final By TWEET_ALERT_MESSAGE = By.cssSelector(".js-message-drawer-visible");
    public static final By NEW_TWEET_BAR = By.cssSelector(".js-new-tweets-bar");
    private static final By LIST_OF_TWEETS_FROM_UI = By.cssSelector(".stream-items .tweet-text");
    private static final By TWEET_CARET_BUTTON = By.cssSelector(".content .js-tooltip .Icon--small");
    private static final By DELETE_TWEET_BUTTON_FROM_DROPDOWN = By.cssSelector(".my-tweet.dismissible-content.focus .js-actionDelete");
    private static final By DELETE_TWEET_MODAL = By.cssSelector("#delete-tweet-dialog-dialog > div.modal-content");
    private static final By DELETE_TWEET_BUTTON_FROM_MODAL = By.cssSelector("#delete-tweet-dialog-dialog .delete-action");
    private static final By TWEET_REPLY_ICON = By.cssSelector(".ProfileTweet-action--reply .Icon--reply");
    private static final By TWEET_REPLY_MODAL = By.cssSelector("#global-tweet-dialog-dialog > div.modal-content");
    private static final By TWEET_REPLY_BOX = By.cssSelector("#tweet-box-global");
    private static final By TWEET_REPLY_BUTTON = By.cssSelector("#global-tweet-dialog-dialog .tweet-button > button");
    private static final By UNSELECTED_FAVORITE_ICON = By.cssSelector("div[class*='my-tweet']:not([class*='favorited']) [title='Like']");
    private static final By SELECTED_FAVORITE_ICONS = By.cssSelector(".my-tweet.favorited [data-original-title='Undo like']");
    private static final By HOME_NAVIGATION_TAB = By.cssSelector(".js-global-actions .home");
    private static final By MOMENTS_NAVIGATION_TAB = By.cssSelector(".js-global-actions .moments");
    private static final By NOTIFICATIONS_NAVIGATION_TAB = By.cssSelector(".js-global-actions .notifications");
    private static final By MESSAGES_NAVIGATION_TAB = By.cssSelector(".js-global-actions .dm-nav");
    private static final By EMOJI_ICON = By.cssSelector("#Tweetstorm-tweet-box-0 .Icon.Icon--smiley");
    private static final By EMOJI_CONTENT = By.cssSelector("#Tweetstorm-tweet-box-0 .EmojiPicker-content");


    private static String tweetTextThatWillBeDeleted;
    private static int initialUnselectedFavoriteIcons;
    private static int actualUnselectedFavoriteIcons;


    private static List<WebElement> LIST_OF_TWEET_TEXT;


    // TODO fix read from properties file
    //private static ResourceBundle resourceBundle = ResourceBundle.getBundle("/resources/common");


    public static void navigateToTwitterHomePage() {
        getDriver().get("https://twitter.com");
    }

    public static boolean isUserLoggedIn() {
        return isElementPresent(LOGGED_IN);
    }

    public static boolean isTopBarDisplayed() {
        return getDriver().findElement(TOP_BAR_COMPONENT).isDisplayed();
    }

    public static boolean isLeftDashboardDisplayed() {
        return getDriver().findElement(LEFT_DASHBOARD).isDisplayed();
    }

    public static boolean isRightDashboardDisplayed() {
        return getDriver().findElement(RIGHT_DASHBOARD).isDisplayed();
    }

    public static boolean isTweetsContainerDisplayed() {
        return getDriver().findElement(TWEETS_CONTAINER).isDisplayed();
    }

    public static boolean isTopNavTweetButtonDisplayed() {
        return getDriver().findElement(TOP_NAV_TWEET_BUTTON).isDisplayed();
    }

    public static boolean isSignUpButtonDisplayed() {
        return getDriver().findElement(SIGN_UP_BUTTON).isDisplayed();
    }

    public static boolean isLoginFormDisplayed() {
        return getDriver().findElement(LOGIN_FORM).isDisplayed();
    }

    public static void clickOnTopNavTweetButton() {
        getDriver().findElement(TOP_NAV_TWEET_BUTTON).click();
    }


    public static void login() {
        getDriver().findElement(SIGN_IN_EMAIL).sendKeys("gaby88sm@yahoo.com");
        getDriver().findElement(SIGN_IN_PASSWORD).sendKeys("testing123");
        getDriver().findElement(LOG_IN_BUTTON).click();
    }

    public static void logout() {
        getDriver().findElement(USER_SETTINGS_BUTTON).click();
        getDriver().findElement(USER_SIGN_OUT_BUTTON).click();
        waitForUrlContains(getDriver(), "logout", 2);

    }

    // TODO extract this into an Utils class
    public static boolean isElementPresent(By by) {
        try {
            getDriver().findElement(by);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }


    // TODO extract these wait methods into an Utils class
    public static Boolean waitForUrlContains(WebDriver driver, String containsText, int specifiedTimeout) {
        WebDriverWait wait = new WebDriverWait(driver, specifiedTimeout);
        return wait.until(ExpectedConditions.urlContains(containsText));
    }

    public static WebElement waitForElement(By locator, long timeOutInSeconds) {
        return waitForVisibilityOfElement(getDriver(), timeOutInSeconds, locator);
    }


    public static WebElement waitForVisibilityOfElement(WebDriver driver, long seconds, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void waitForElementToDisappear(By locator, long timeOutInSeconds) {
        waitForInvisibilityOfElement(getDriver(), timeOutInSeconds, locator);
    }

    public static Boolean waitForInvisibilityOfElement(WebDriver driver, long seconds, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static List<WebElement> waitUntilNumberOfElementsAreEqual(By locator, int expectedNumberOfElements, long timeOutInSeconds) {
        return waitForNumberOfElementsToBe(getDriver(), timeOutInSeconds, locator, expectedNumberOfElements);
    }

    public static List<WebElement> waitForNumberOfElementsToBe(WebDriver driver, long seconds, By locator, int expectedNumberOfElements) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        return wait.until(ExpectedConditions.numberOfElementsToBe(locator, expectedNumberOfElements));
    }

    public static Boolean isTextPresentInElement(By locator, String expectedText, long timeOutInSeconds) {
        return waitForTextToBePresentInElement(getDriver(), timeOutInSeconds, locator, expectedText);
    }

    public static Boolean waitForTextToBePresentInElement(WebDriver driver, long seconds, By locator, String expectedText) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, expectedText));
    }

    public static Boolean waitForURL(String expectedURL, long waitTimeInSeconds) {
        return waitForURLToContain(getDriver(), waitTimeInSeconds, expectedURL.toLowerCase());
    }

    public static Boolean waitForURLToContain(WebDriver driver, long seconds, String expectedURL) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        return wait.until(ExpectedConditions.urlContains(expectedURL));
    }


    public static String getTweetTextFromPosition(int positionNumber) {
        LIST_OF_TWEET_TEXT = getDriver().findElements(By.cssSelector(".stream-items .tweet-text"));
        return LIST_OF_TWEET_TEXT.get(positionNumber - 1).getText();
    }


    public static void postTweet(String tweetMessage) {

        //TODO break this into a smaller piece
        clickOnTopNavTweetButton();
        waitForElement(TWEET_COMPOSE_MODAL, 3);
        sendTextToTextBox(TWEET_BOX_CONTENT, tweetMessage);
        waitForTextToBePresentInElement(getDriver(), 3, TWEET_BOX, tweetMessage);
        getDriver().findElement(SEND_TWEET_BUTTON).click();
        waitForElement(TWEET_ALERT_MESSAGE, 5);
        waitForElementToDisappear(TWEET_ALERT_MESSAGE, 10);
    }

    public static void sendTextToTextBox(By locator, String tweetMessage) {
        for (int i = 0; i < tweetMessage.length(); i++) {
            char c = tweetMessage.charAt(i);
            String string = new StringBuilder().append(c).toString();
            getDriver().findElement(locator).sendKeys(string);
        }
    }

    public static void clickOnNewTweetBar() {
        getDriver().findElement(NEW_TWEET_BAR).click();
    }

    public static void waitUntilAllTheTweetsAreDisplayed() {
        waitUntilNumberOfElementsAreEqual(LIST_OF_TWEETS_FROM_UI, getNumberTweetsFromHomeTimeline(), 10);

    }

    public static void deleteTweetFromUI(int tweetPosition) {
        getDriver().findElements(TWEET_CARET_BUTTON).get(tweetPosition - 1).click();
        getDriver().findElement(DELETE_TWEET_BUTTON_FROM_DROPDOWN).click();
        waitForElement(DELETE_TWEET_MODAL, 3);
        getDriver().findElement(DELETE_TWEET_BUTTON_FROM_MODAL).click();
        waitForElement(TWEET_ALERT_MESSAGE, 5);
        waitForElementToDisappear(TWEET_ALERT_MESSAGE, 10);
    }

    public static void replyToTweet(int tweetPosition, String tweetReply) {
        getDriver().findElements(TWEET_REPLY_ICON).get(tweetPosition - 1).click();
        waitForElement(TWEET_REPLY_MODAL, 2);
        sendTextToTextBox(TWEET_REPLY_BOX, tweetReply);
        waitForTextToBePresentInElement(getDriver(), 2, TWEET_REPLY_BOX, tweetReply);
        getDriver().findElement(TWEET_REPLY_BUTTON).click();
        waitForElement(TWEET_ALERT_MESSAGE, 2);
        waitForElementToDisappear(TWEET_ALERT_MESSAGE, 5);
    }

    public static void addTweetToFavorite(int tweetPosition) {
        waitUntilAllTheTweetsAreDisplayed();
        setInitialUnselectedFavoriteIcons(getDriver().findElements(UNSELECTED_FAVORITE_ICON).size());
        getDriver().findElements(UNSELECTED_FAVORITE_ICON).get(tweetPosition - 1).click();
        waitForElement(SELECTED_FAVORITE_ICONS, 3);
        setActualUnselectedFavoriteIcons(getDriver().findElements(UNSELECTED_FAVORITE_ICON).size());
    }

    public static boolean isFavoriteTweet(int tweetPosition) {
        return getDriver().findElements(SELECTED_FAVORITE_ICONS).get(tweetPosition - 1).isDisplayed();
    }


    public static int getInitialUnselectedFavoriteIcons() {
        return initialUnselectedFavoriteIcons;
    }

    public static void setInitialUnselectedFavoriteIcons(int initialUnselectedFavoriteIcons) {
        TwitterHomePageTests.initialUnselectedFavoriteIcons = initialUnselectedFavoriteIcons;
    }

    public static int getActualUnselectedFavoriteIcons() {
        return actualUnselectedFavoriteIcons;
    }

    public static void setActualUnselectedFavoriteIcons(int actualUnselectedFavoriteIcons) {
        TwitterHomePageTests.actualUnselectedFavoriteIcons = actualUnselectedFavoriteIcons;
    }

    public static void clickOnSelectedNavigationButton(String navigationTabName) {
        switch (navigationTabName.toLowerCase()) {
            case "home":
                getDriver().findElement(HOME_NAVIGATION_TAB).click();
                break;
            case "moments":
                getDriver().findElement(MOMENTS_NAVIGATION_TAB).click();
                break;
            case "notifications":
                getDriver().findElement(NOTIFICATIONS_NAVIGATION_TAB).click();
                break;
            case "messages":
                getDriver().findElement(MESSAGES_NAVIGATION_TAB).click();
                break;
            default:
        }
    }

    public static Boolean isNavigationTabSelected(String navigationTabName) {
        boolean isTabSelected = false;
        switch (navigationTabName.toLowerCase()) {
            case "home":
                isTabSelected = getDriver().findElement(HOME_NAVIGATION_TAB).getAttribute("class").contains("active");
                break;
            case "moments":
                isTabSelected = getDriver().findElement(MOMENTS_NAVIGATION_TAB).getAttribute("class").contains("active");
                break;
            case "notifications":
                isTabSelected = getDriver().findElement(NOTIFICATIONS_NAVIGATION_TAB).getAttribute("class").contains("active");
                break;
            case "messages":
                isTabSelected = getDriver().findElement(MESSAGES_NAVIGATION_TAB).getAttribute("class").contains("active");
                break;
        }
        return isTabSelected;
    }

    public static void clickOnEmojiMenu() {
        getDriver().findElement(EMOJI_ICON).click();
    }

    public static boolean isEmojiContentDisplayed() {
        return getDriver().findElement(EMOJI_CONTENT).isDisplayed();
    }

    public static void enterTweetMessageIntoModal(String tweetMessage) {
        waitForElement(TWEET_COMPOSE_MODAL, 3);
        sendTextToTextBox(TWEET_BOX_CONTENT, tweetMessage);
        waitForTextToBePresentInElement(getDriver(), 3, TWEET_BOX, tweetMessage);
    }

    public static void postAndWaitForTweetToAppear() {
        getDriver().findElement(SEND_TWEET_BUTTON).click();
        waitForElement(TWEET_ALERT_MESSAGE, 5);
        waitForElementToDisappear(TWEET_ALERT_MESSAGE, 10);
    }

    public static String getTweetTextThatWillBeDeleted() {
        return tweetTextThatWillBeDeleted;
    }

    public static void setTweetTextThatWillBeDeleted(String tweetTextThatWillBeDeleted) {
        TwitterHomePageTests.tweetTextThatWillBeDeleted = tweetTextThatWillBeDeleted;
    }

    public static String saveTweetText(int tweetPosition) {
        return getDriver().findElements(LIST_OF_TWEETS_FROM_UI).get(tweetPosition - 1).getText();
    }
}
