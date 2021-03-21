package com.twitter.automation.page.object.model;


import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.twitter.automation.step.definitions.CucumberHooks.getDriver;
import static com.twitter.automation.step.definitions.HomePageSteps.setActualUnselectedFavoriteIcons;
import static com.twitter.automation.step.definitions.HomePageSteps.setInitialUnselectedFavoriteIcons;
import static com.twitter.automation.utils.CommonUtils.*;


public class TwitterHomePage {

    private final By SIGN_UP_BUTTON = By.cssSelector("[data-testid='signupButton']");
    private final By LOGIN_BUTTON = By.cssSelector("[data-testid='loginButton']");
    private final By SIGN_IN_EMAIL = By.cssSelector("label [name='session[username_or_email]']");
    private final By SIGN_IN_PASSWORD = By.cssSelector("label [name='session[password]']");
    private final By LOGIN_FORM_BUTTON = By.cssSelector("main [data-testid='LoginForm_Login_Button']");
    private final By SIDE_NAV_ACCOUNT_SWITCHER = By.cssSelector("[data-testid='SideNav_AccountSwitcher_Button']");
    private final By LOGOUT_BUTTON = By.cssSelector("[data-testid='AccountSwitcher_Logout_Button']");
    private final By LOGOUT_CONFIRMATION_BUTTON = By.cssSelector("[data-testid='confirmationSheetConfirm']");
    private final By RIGHT_SIDEBAR = By.cssSelector("[data-testid='sidebarColumn']");
    private final By SEARCH_BOX_INPUT = By.cssSelector("[data-testid='SearchBox_Search_Input']");
    private final By SIDE_NAV_TWEET_BUTTON = By.cssSelector("[data-testid='SideNav_NewTweet_Button']");
    private final By TWEET_COMPOSE_MODAL = By.cssSelector("[aria-labelledby='modal-header']");
    private final By TWEET_BOX_CONTENT = By.cssSelector("[aria-labelledby='modal-header'] [data-testid='tweetTextarea_0']");
    private final By SEND_TWEET_BUTTON = By.cssSelector("[data-testid='tweetButton']");
    private final By TWEET_ALERT_MESSAGE = By.cssSelector("[data-testid='toast']");
    private final By TWEET_CARET_BUTTON = By.cssSelector("[data-testid='primaryColumn'] [data-testid='caret']");
    private final By DELETE_TWEET_BUTTON_FROM_MODAL = By.cssSelector("[data-testid='confirmationSheetConfirm']");
    private final By TWEET_REPLY_ICON = By.cssSelector("[data-testid='reply']");
    private final By UNSELECTED_FAVORITE_ICON = By.cssSelector("[data-testid='like']");
    private final By SELECTED_FAVORITE_ICONS = By.cssSelector("[data-testid='unlike']");
    private final By HOME_NAVIGATION_TAB = By.cssSelector("[data-testid='AppTabBar_Home_Link']");
    private final By EXPLORE_NAVIGATION_TAB = By.cssSelector("[data-testid='AppTabBar_Explore_Link']");
    private final By NOTIFICATIONS_NAVIGATION_TAB = By.cssSelector("[data-testid='AppTabBar_Notifications_Link']");
    private final By MESSAGES_NAVIGATION_TAB = By.cssSelector("[data-testid='AppTabBar_DirectMessage_Link']");
    private final By USER_PROFILE_BUTTON = By.cssSelector("[aria-label='Profile']");
    private final By LIST_OF_TWEETS = By.cssSelector("[data-testid='tweet'] [lang]");
    private final By USER_TWEETS_COLUMN = By.cssSelector("[data-testid='primaryColumn']");
    private final By ACTIVE_TAB = By.cssSelector(".r-13gxpu9");
    private final By TWEET_MENU_CONTAINER = By.cssSelector("[role='menu']");
    private final By TIMELINE_LOADING = By.cssSelector("[aria-label='Loading timeline'][role=progressbar]");
    private final By HOME_TIMELINE_LOADER = By.cssSelector("[aria-label='Timeline: Your Home Timeline'] [role=progressbar]");
    private final By RECOMMENDATION_LOADER = By.cssSelector("[aria-label='Loading recommendations for users to follow'][role=progressbar]");
    private final By LOADING_PAGE = By.cssSelector(".r-k200y");

    private final String BASE_URL = loadPropertiesFile().getProperty("twitter.url");
    private final String USERNAME = loadPropertiesFile().getProperty("username");
    private final String PASSWORD = loadPropertiesFile().getProperty("password");

    private String tweetTextThatWillBeDeleted;


    private final Logger LOGGER = LoggerFactory.getLogger(TwitterHomePage.class);


    public void navigateToTwitterHomePage() {
        getDriver().get(BASE_URL);
        removeWelcomeCookie();
        waitForElement(LOADING_PAGE, 10);
    }

    public void navigateToUserTimeline() {
        getDriver().findElement(USER_PROFILE_BUTTON).click();
        waitForElement(USER_TWEETS_COLUMN, 10);
    }

    public boolean isUserLoggedIn() {
        return isElementDisplayed(SIGN_UP_BUTTON);
    }


    public boolean isSearchBoxDisplayed() {
        return getDriver().findElement(SEARCH_BOX_INPUT).isDisplayed();
    }

    public boolean isRightSideBarDisplayed() {
        return getDriver().findElement(RIGHT_SIDEBAR).isDisplayed();
    }

    public boolean isTweetsContainerDisplayed() {
        return getDriver().findElement(USER_TWEETS_COLUMN).isDisplayed();
    }

    public boolean isSideNavTweetButtonDisplayed() {
        return getDriver().findElement(SIDE_NAV_TWEET_BUTTON).isDisplayed();
    }

    public boolean isSignUpButtonDisplayed() {
        return getDriver().findElement(SIGN_UP_BUTTON).isDisplayed();
    }

    public boolean isLoginButtonDisplayed() {
        return getDriver().findElement(LOGIN_BUTTON).isDisplayed();
    }

    public void clickOnSideNavTweetButton() {
        getDriver().findElement(SIDE_NAV_TWEET_BUTTON).click();
    }

    public void clickOnSelectedTweetMenuItem(String menuItemName) {
        waitForElement(TWEET_MENU_CONTAINER, 2);
        getDriver().findElement(By.xpath("//span[contains(text(),'" + menuItemName + "')]")).click();

    }

    public void login() {
        getDriver().findElement(LOGIN_BUTTON).click();
        waitForElement(SIGN_IN_EMAIL, 10);
        getDriver().findElement(SIGN_IN_EMAIL).sendKeys(USERNAME);
        getDriver().findElement(SIGN_IN_PASSWORD).sendKeys(PASSWORD);
        getDriver().findElement(LOGIN_FORM_BUTTON).click();
        waitForElement(USER_TWEETS_COLUMN, 30);
        waitForUrlContains("home", 10);
        waitForHomePageToFinishLoading();
    }

    public void logout() {
        getDriver().findElement(SIDE_NAV_ACCOUNT_SWITCHER).click();
        waitForElement(LOGOUT_BUTTON, 5);
        getDriver().findElement(LOGOUT_BUTTON).click();
        waitForElement(LOGOUT_CONFIRMATION_BUTTON, 3);
        getDriver().findElement(LOGOUT_CONFIRMATION_BUTTON).click();
        waitForElement(SIGN_UP_BUTTON, 10);
    }

    public String getTweetTextFromPosition(int positionNumber) {
        return getDriver().findElements(LIST_OF_TWEETS).get(positionNumber - 1).getText();
    }

    private void enterTextIntoElement(By locator, String tweetMessage) {
        for (int index = 0; index < tweetMessage.length(); index++) {
            char character = tweetMessage.charAt(index);
            getDriver().findElement(locator).sendKeys(String.valueOf(character));
        }
    }


    public void deleteTweetFromUI(int tweetPosition) {
        getDriver().findElements(TWEET_CARET_BUTTON).get(tweetPosition - 1).click();
        clickOnSelectedTweetMenuItem("Delete");
        waitForElement(DELETE_TWEET_BUTTON_FROM_MODAL, 3);
        getDriver().findElement(DELETE_TWEET_BUTTON_FROM_MODAL).click();
        waitForElement(TWEET_ALERT_MESSAGE, 10);
        waitForElementToDisappear(TWEET_ALERT_MESSAGE, 10);
    }

    public void replyToTweet(int tweetPosition, String tweetReply) {
        waitForElement(TWEET_REPLY_ICON, 10);
        getDriver().findElements(TWEET_REPLY_ICON).get(tweetPosition - 1).click();
        waitForElement(TWEET_COMPOSE_MODAL, 2);
        enterTextIntoElement(TWEET_BOX_CONTENT, tweetReply);
        LOGGER.info("replying with message: [{}]", tweetReply);
        waitForTextToBePresentInElement(2, TWEET_BOX_CONTENT, tweetReply);
    }

    public void addTweetToFavorite(int tweetPosition) {
        waitForHomePageToFinishLoading();
        setInitialUnselectedFavoriteIcons(getDriver().findElements(UNSELECTED_FAVORITE_ICON).size());
        int initialNumberOfSelectedFavoriteIcons = getDriver().findElements(SELECTED_FAVORITE_ICONS).size();
        getDriver().findElements(UNSELECTED_FAVORITE_ICON).get(tweetPosition - 1).click();
        waitForNumberOfElements(SELECTED_FAVORITE_ICONS, 5, initialNumberOfSelectedFavoriteIcons + 1);
        setActualUnselectedFavoriteIcons(getDriver().findElements(UNSELECTED_FAVORITE_ICON).size());
    }

    public boolean isFavoriteTweet(int tweetPosition) {
        return getDriver().findElements(SELECTED_FAVORITE_ICONS).get(tweetPosition - 1).isDisplayed();
    }


    public void clickOnSelectedNavigationButton(String navigationTabName) {
        switch (navigationTabName.toLowerCase()) {
            case "home":
                getDriver().findElement(HOME_NAVIGATION_TAB).click();
                break;
            case "explore":
                getDriver().findElement(EXPLORE_NAVIGATION_TAB).click();
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

    public Boolean isNavigationTabSelected(String navigationTabName) {
        boolean isTabSelected = false;
        switch (navigationTabName.toLowerCase()) {
            case "home":
                isTabSelected = getDriver().findElement(HOME_NAVIGATION_TAB).findElement(ACTIVE_TAB).isDisplayed();
                break;
            case "explore":
                isTabSelected = getDriver().findElement(EXPLORE_NAVIGATION_TAB).findElement(ACTIVE_TAB).isDisplayed();
                break;
            case "notifications":
                isTabSelected = getDriver().findElement(NOTIFICATIONS_NAVIGATION_TAB).findElement(ACTIVE_TAB).isDisplayed();
                break;
            case "messages":
                isTabSelected = getDriver().findElement(MESSAGES_NAVIGATION_TAB).findElement(ACTIVE_TAB).isDisplayed();
                break;
        }
        return isTabSelected;
    }

    public void enterTweetMessageIntoModal(String tweetMessage) {
        waitForElement(TWEET_COMPOSE_MODAL, 3);
        enterTextIntoElement(TWEET_BOX_CONTENT, tweetMessage);
        waitForTextToBePresentInElement(3, TWEET_BOX_CONTENT, tweetMessage);
    }

    public void clickAndWaitForTweetToAppear() {
        getDriver().findElement(SEND_TWEET_BUTTON).click();
        waitForElementToDisappear(TWEET_COMPOSE_MODAL, 3);
        waitForElement(TWEET_ALERT_MESSAGE, 3);
        waitForElementToDisappear(TWEET_ALERT_MESSAGE, 10);

    }

    public void removeWelcomeCookie() {
        Cookie ck = new Cookie("eu_cn", "1");
        getDriver().manage().addCookie(ck);
    }

    public void setTweetTextThatWillBeDeleted(String tweetThatWillBeDeleted) {
        waitForElement(TWEET_CARET_BUTTON, 10);
        tweetTextThatWillBeDeleted = tweetThatWillBeDeleted;
    }

    public String getTweetTextThatWillBeDeleted() {
        return tweetTextThatWillBeDeleted;
    }

    public void waitForHomePageToFinishLoading() {
        waitForElementToDisappear(TIMELINE_LOADING, 10);
        waitForElementToDisappear(HOME_TIMELINE_LOADER, 10);
        waitForElementToDisappear(RECOMMENDATION_LOADER, 10);
        waitForElement(LIST_OF_TWEETS, 2);
        waitForElement(UNSELECTED_FAVORITE_ICON, 2);
    }
}
