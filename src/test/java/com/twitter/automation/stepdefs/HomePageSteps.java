package com.twitter.automation.stepdefs;

import com.twitter.automation.page.object.model.TwitterHomePage;
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

import static com.twitter.automation.utils.CommonUtils.waitForURL;
import static org.junit.Assert.*;
import static org.testng.Assert.assertEquals;

public class HomePageSteps {


    private static WebDriver driver;

    public static WebDriver getDriver() {
        return driver;
    }

    private TwitterHomePage homePage;
    private static String uniqueTweetMessage;
    private static String tweetTextThatWillBeDeleted;
    private static int initialUnselectedFavoriteIcons;
    private static int actualUnselectedFavoriteIcons;

    @Before
    public void setUp() {
        System.out.println();
        WebDriverManager.chromedriver().setup();
        //TODO add logic to change browser based on user preference
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        homePage = new TwitterHomePage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


    public static int getInitialUnselectedFavoriteIcons() {
        return initialUnselectedFavoriteIcons;
    }

    public static void setInitialUnselectedFavoriteIcons(int initialUnselectedFavoriteIcons) {
        HomePageSteps.initialUnselectedFavoriteIcons = initialUnselectedFavoriteIcons;
    }

    public static int getActualUnselectedFavoriteIcons() {
        return actualUnselectedFavoriteIcons;
    }

    public static void setActualUnselectedFavoriteIcons(int actualUnselectedFavoriteIcons) {
        HomePageSteps.actualUnselectedFavoriteIcons = actualUnselectedFavoriteIcons;
    }

    public static String getTweetTextThatWillBeDeleted() {
        return tweetTextThatWillBeDeleted;
    }

    public static void setTweetTextThatWillBeDeleted(String tweetTextThatWillBeDeleted) {
        HomePageSteps.tweetTextThatWillBeDeleted = tweetTextThatWillBeDeleted;
    }

    public static String getUniqueTweetMessage() {
        return uniqueTweetMessage;
    }

    public static void setUniqueTweetMessage(String uniqueTweetMessage) {
        HomePageSteps.uniqueTweetMessage = uniqueTweetMessage;
    }

    @Given("^that the user goes to the tweeter home page$")
    public void thatTheUserGoesToTheTweeterHomePage() {
        homePage.navigateToTwitterHomePage();
    }

    @When("^the user is \"(logged in|logged out)\"$")
    public void theUserIs(String loginStatus) {
        if ("logged out".equals(loginStatus)) {
            assertFalse(homePage.isUserLoggedIn());
        } else {
            assertTrue(homePage.isUserLoggedIn());
        }
    }

    @Then("^the login page should appear with the expected sign in / login components$")
    public void theLoginPageShouldAppearWithTheExpectedSignInLoginComponents() {
        assertTrue(homePage.isLoginFormDisplayed());
        assertTrue(homePage.isSignUpButtonDisplayed());
    }

    @When("^the user logs \"(in|out)\"$")
    public void theUserLogs(String loginStatus) {
        if ("in".equals(loginStatus)) {
            homePage.login();
        } else {
            homePage.logout();

        }
    }

    @Then("^he should see all the home page components$")
    public void heShouldSeeAllTheHomePageComponents() {
        assertTrue(homePage.isTopBarDisplayed());
        assertTrue(homePage.isTopNavTweetButtonDisplayed());
        assertTrue(homePage.isLeftDashboardDisplayed());
        assertTrue(homePage.isRightDashboardDisplayed());
        assertTrue(homePage.isTweetsContainerDisplayed());
    }

    @Then("^the posted tweet should be displayed and should contain the above message$")
    public void thePostedTweetShouldBeDisplayedAndShouldContainMessage() {
        assertEquals(uniqueTweetMessage, homePage.getTweetTextFromPosition(1));

    }

    @And("^we wait until the new tweet bar is displayed and we click on it to update the tweet message list$")
    public void weWaitUntilTheNewTweetBarIsDisplayedAndWeClickOnItToUpdateTheTweetMessageList() {
        homePage.clickOnNewTweetBar();
    }

    @And("^then the user decides to delete (1|2|3)(?:st|nd|rd) tweet$")
    public void thenTheUserDecidesToDeleteLastTweet(int tweetPosition) {
        setTweetTextThatWillBeDeleted(homePage.getTweetTextFromPosition(1));
        homePage.deleteTweetFromUI(tweetPosition);

    }

    @Then("^the tweet from the (1|2|3)(?:st|nd|rd) position should no longer be displayed$")
    public void theTweetShouldNoLongerBeDisplayed(int tweetPosition) {
        assertNotEquals(getTweetTextThatWillBeDeleted(), homePage.getTweetTextFromPosition(tweetPosition));
    }

    @And("^the user is replying to the (1|2|3)(?:st|nd|rd) tweet with the following text \"([^\"]*)\"$")
    public void theUserIsReplyingToTheTweetWithTheFollowingText(int tweetPosition, String tweetReply) {
        homePage.replyToTweet(tweetPosition, tweetReply);
    }

    @Then("^the (1|2|3)(?:st|nd|rd) tweet should contain the \"([^\"]*)\" message as a reply$")
    public void theTweetShouldContainTheMessageAsAReply(int tweetPosition, String tweetReply) {
        assertEquals(homePage.getTweetTextFromPosition(tweetPosition + 1), tweetReply);
    }

    @When("^the user adds the (1|2|3)(?:st|nd|rd) tweet as a favorite$")
    public void theUserAddsTheTweetAsAFavorite(int tweetPosition) {
        homePage.addTweetToFavorite(tweetPosition);
    }

    @Then("^the (1|2|3)(?:st|nd|rd) tweet is added as a favorite$")
    public void theStTweetIsAddedAsAFavorite(int tweetPosition) {
        assertTrue(homePage.isFavoriteTweet(tweetPosition));
    }

    @And("^there is \"([^\"]*)\" favorite tweet added to the list$")
    public void thereIsFavoriteTweetAddedToTheList(int numberOfFavoriteTweets) {
        assertEquals(getActualUnselectedFavoriteIcons(), getInitialUnselectedFavoriteIcons() - numberOfFavoriteTweets);
    }

    @When("^the user clicks on \"([^\"]*)\" tab$")
    public void theUserClicksOnTab(String navigationTabName) {
        homePage.clickOnSelectedNavigationButton(navigationTabName);
    }

    @Then("^he should be redirected to the \"([^\"]*)\" page$")
    public void heShouldBeRedirectedToThePage(String urlName) {
        waitForURL(getDriver(), urlName, 2);
        assertTrue(homePage.isNavigationTabSelected(urlName));
    }

    @And("^when the user clicks on the emoji menu$")
    public void whenTheUserClicksOnTheEmojiMenu() {
        homePage.clickOnEmojiMenu();
    }

    @Then("^the emoji content is displayed and after clicking on it it's no longer displayed$")
    public void theEmojiContentIsDisplayedAndAfterClickingOnItItSNoLongerDisplayed() {
        assertTrue(homePage.isEmojiContentDisplayed());
        homePage.clickOnEmojiMenu();
        assertFalse(homePage.isEmojiContentDisplayed());
    }

    @And("^the user click on the right nav tweet button and opens the compose tweet modal$")
    public void theUserClickOnTheRightNavTweetButtonAndOpensTheComposeTweetModal() {
        homePage.clickOnTopNavTweetButton();
    }

    @And("^enters the following text \"([^\"]*)\"$")
    public void entersTheFollowingText(String tweetMessage) {
        setUniqueTweetMessage(tweetMessage + " [ " + UUID.randomUUID() + " ]");
        homePage.enterTweetMessageIntoModal(getUniqueTweetMessage());
    }

    @And("^user clicks on the tweet button and waits until the tweet is posted$")
    public void userClicksOnTheTweetButtonAndWaitsUntilTheTweetIsPosted() {
        homePage.clickAndWaitForTweetToAppear();
    }

    @And("^the user navigates to the user timeline$")
    public void theUserNavigatesToTheUserTimeline() {
        homePage.navigateToUserTimeline();
    }
}
