package com.twitter.automation.step.definitions;

import com.twitter.automation.page.object.model.TwitterHomePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.UUID;

import static com.twitter.automation.utils.CommonUtils.waitForURL;
import static org.testng.Assert.*;


public class HomePageSteps {

    private final TwitterHomePage homePage = new TwitterHomePage();


    private static String uniqueTweetMessage;
    private static String uniqueTweetReplyMessage;
    private static int initialUnselectedFavoriteIcons;
    private static int actualUnselectedFavoriteIcons;


    private static int getInitialUnselectedFavoriteIcons() {
        return initialUnselectedFavoriteIcons;
    }

    public static void setInitialUnselectedFavoriteIcons(int initialValue) {
        initialUnselectedFavoriteIcons = initialValue;
    }

    private static int getActualUnselectedFavoriteIcons() {
        return actualUnselectedFavoriteIcons;
    }

    public static void setActualUnselectedFavoriteIcons(int actualValue) {
        actualUnselectedFavoriteIcons = actualValue;
    }

    private static String getUniqueTweetMessage() {
        return uniqueTweetMessage;
    }

    private static void setUniqueTweetMessage(String uniqueTweetMessage) {
        HomePageSteps.uniqueTweetMessage = uniqueTweetMessage;
    }

    @Given("^that the user goes to the tweeter home page$")
    public void thatTheUserGoesToTheTweeterHomePage() {
        homePage.navigateToTwitterHomePage();
    }

    @When("^the user is \"(logged in|logged out)\"$")
    public void theUserIs(String loginStatus) {
        if ("logged out".equals(loginStatus)) {
            assertTrue(homePage.isUserLoggedIn());
        } else {
            assertFalse(homePage.isUserLoggedIn());
        }
    }

    @Then("^the login page should appear with the expected sign in / login components$")
    public void theLoginPageShouldAppearWithTheExpectedSignInLoginComponents() {
        assertTrue(homePage.isLoginButtonDisplayed());
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
        assertTrue(homePage.isSideNavTweetButtonDisplayed());
        assertTrue(homePage.isSearchBoxDisplayed());
        assertTrue(homePage.isRightSideBarDisplayed());
        assertTrue(homePage.isTweetsContainerDisplayed());
    }

    @Then("^the posted tweet should be displayed and should contain the above message$")
    public void thePostedTweetShouldBeDisplayedAndShouldContainMessage() {
        assertEquals(homePage.getTweetTextFromPosition(1), uniqueTweetMessage);

    }


    @And("^then the user decides to delete (1|2|3)(?:st|nd|rd) tweet$")
    public void thenTheUserDecidesToDeleteLastTweet(int tweetPosition) {
        homePage.setTweetTextThatWillBeDeleted(homePage.getTweetTextFromPosition(1));
        homePage.deleteTweetFromUI(tweetPosition);

    }

    @Then("^the tweet from the (1|2|3)(?:st|nd|rd) position should no longer be displayed$")
    public void theTweetShouldNoLongerBeDisplayed(int tweetPosition) {
        assertNotEquals(homePage.getTweetTextThatWillBeDeleted(), homePage.getTweetTextFromPosition(tweetPosition));
    }

    @And("^the user is replying to the (1|2|3)(?:st|nd|rd) tweet with the following text \"([^\"]*)\"$")
    public void theUserIsReplyingToTheTweetWithTheFollowingText(int tweetPosition, String tweetReply) {
        uniqueTweetReplyMessage = tweetReply + " [ " + UUID.randomUUID() + " ]";
        homePage.replyToTweet(tweetPosition, uniqueTweetReplyMessage);
    }

    @Then("^the (1|2|3)(?:st|nd|rd) tweet should contain the Replying to a tweet message as a reply$")
    public void theTweetShouldContainTheMessageAsAReply(int tweetPosition) {
        assertEquals(homePage.getTweetTextFromPosition(tweetPosition), uniqueTweetReplyMessage);
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
        waitForURL(urlName, 10);
        assertTrue(homePage.isNavigationTabSelected(urlName));
    }


    @And("^open the top nav tweet modal$")
    public void openComposeTweetModalFromNavBar() {
        homePage.clickOnSideNavTweetButton();
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
