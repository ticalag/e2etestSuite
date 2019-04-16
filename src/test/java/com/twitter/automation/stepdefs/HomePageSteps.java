package com.twitter.automation.stepdefs;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.UUID;

import static com.twitter.automation.ui.TwitterHomePageTests.*;
import static org.junit.Assert.*;
import static org.testng.Assert.assertEquals;

public class HomePageSteps {


    private static WebDriver driver;
    private static String uniqueTweetMessage;

    @Before
    public void before() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @After
    public void after() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }

    @Given("^that the user goes to the tweeter home page$")
    public void thatTheUserGoesToTheTweeterHomePage() {
        navigateToTwitterHomePage();
    }

    @When("^the user is \"(logged in|logged out)\"$")
    public void theUserIs(String loginStatus) {
        if ("logged out".equals(loginStatus)) {
            assertFalse(isUserLoggedIn());
        } else {
            assertTrue(isUserLoggedIn());
        }
    }

    @Then("^the login page should appear with the expected sign in / login components$")
    public void theLoginPageShouldAppearWithTheExpectedSignInLoginComponents() {
        assertTrue(isLoginFormDisplayed());
        assertTrue(isSignUpButtonDisplayed());
    }

    @When("^the user logs \"(in|out)\"$")
    public void theUserLogs(String loginStatus) {
        if ("in".equals(loginStatus)) {
            login();
        } else {
            logout();

        }
    }

    @Then("^he should see all the home page components$")
    public void heShouldSeeAllTheHomePageComponents() {
        assertTrue(isTopBarDisplayed());
        assertTrue(isTopNavTweetButtonDisplayed());
        assertTrue(isLeftDashboardDisplayed());
        assertTrue(isRightDashboardDisplayed());
        assertTrue(isTweetsContainerDisplayed());
    }

    @And("^the user is posting a tweet with the following text \"([^\"]*)\"$")
    public void theUserIsPostingATweetWithTheFollowingText(String tweetMessage) {
        uniqueTweetMessage = tweetMessage + " [ " + UUID.randomUUID() + " ]";
        postTweet(uniqueTweetMessage);

    }

    @Then("^the posted tweet should be displayed and should contain the above message$")
    public void thePostedTweetShouldBeDisplayedAndShouldContainMessage() {
        assertEquals(uniqueTweetMessage, getTweetTextFromPosition(1));

    }

    @And("^we wait until the new tweet bar is displayed and we click on it to update the tweet message list$")
    public void weWaitUntilTheNewTweetBarIsDisplayedAndWeClickOnItToUpdateTheTweetMessageList() {
        waitForElement(NEW_TWEET_BAR, 10);
        clickOnNewTweetBar();
        waitUntilAllTheTweetsAreDisplayed();
    }

    @And("^then the user decides to delete (1|2|3)(?:st|nd|rd) tweet$")
    public void thenTheUserDecidesToDeleteLastTweet(int tweetPosition) {
        setTweetTextThatWillBeDeleted(getTweetTextFromPosition(1));
        deleteTweetFromUI(tweetPosition);

    }

    @Then("^the tweet from the (1|2|3)(?:st|nd|rd) position should no longer be displayed$")
    public void theTweetShouldNoLongerBeDisplayed(int tweetPosition) {
        assertNotEquals(getTweetTextThatWillBeDeleted(), getTweetTextFromPosition(tweetPosition));
    }

    @And("^the user is replying to the (1|2|3)(?:st|nd|rd) tweet with the following text \"([^\"]*)\"$")
    public void theUserIsReplyingToTheTweetWithTheFollowingText(int tweetPosition, String tweetReply) {
        replyToTweet(tweetPosition, tweetReply);
    }

    @Then("^the (1|2|3)(?:st|nd|rd) tweet should contain the \"([^\"]*)\" message as a reply$")
    public void theTweetShouldContainTheMessageAsAReply(int tweetPosition, String tweetReply) {
        assertEquals(getTweetTextFromPosition(tweetPosition + 1), tweetReply);
    }

    @When("^the user adds the (1|2|3)(?:st|nd|rd) tweet as a favorite$")
    public void theUserAddsTheTweetAsAFavorite(int tweetPosition) {
        addTweetToFavorite(tweetPosition);
    }

    @Then("^the (1|2|3)(?:st|nd|rd) tweet is added as a favorite$")
    public void theStTweetIsAddedAsAFavorite(int tweetPosition) {
        assertTrue(isFavoriteTweet(tweetPosition));
    }

    @And("^there is \"([^\"]*)\" favorite tweet added to the list$")
    public void thereIsFavoriteTweetAddedToTheList(int numberOfFavoriteTweets) {
        System.out.println("actual " + getActualUnselectedFavoriteIcons());
        System.out.println("intial " + getInitialUnselectedFavoriteIcons());

        assertEquals(getActualUnselectedFavoriteIcons(), getInitialUnselectedFavoriteIcons() - numberOfFavoriteTweets);
    }

    @When("^the user clicks on \"([^\"]*)\" tab$")
    public void theUserClicksOnTab(String navigationTabName) {
        clickOnSelectedNavigationButton(navigationTabName);
    }

    @Then("^he should be redirected to the \"([^\"]*)\" page$")
    public void heShouldBeRedirectedToThePage(String urlName) {
        waitForURL(urlName, 2);
        assertTrue(isNavigationTabSelected(urlName));
    }

    @And("^when the user clicks on the emoji menu$")
    public void whenTheUserClicksOnTheEmojiMenu() {
        clickOnEmojiMenu();
    }

    @Then("^the emoji content is displayed and after clicking on it it's no longer displayed$")
    public void theEmojiContentIsDisplayedAndAfterClickingOnItItSNoLongerDisplayed() {
        assertTrue(isEmojiContentDisplayed());
        clickOnEmojiMenu();
        assertFalse(isEmojiContentDisplayed());
    }

    @And("^the user click on the right nav tweet button and opens the compose tweet modal$")
    public void theUserClickOnTheRightNavTweetButtonAndOpensTheComposeTweetModal() {
        clickOnTopNavTweetButton();
    }

    @And("^enters the following text \"([^\"]*)\"$")
    public void entersTheFollowingText(String tweetMessage) {
        uniqueTweetMessage = tweetMessage + " [ " + UUID.randomUUID() + " ]";
        enterTweetMessageIntoModal(uniqueTweetMessage);
    }

    @And("^user clicks on the tweet button and waits until the tweet is posted$")
    public void userClicksOnTheTweetButtonAndWaitsUntilTheTweetIsPosted() {
        postAndWaitForTweetToAppear();
    }
}
