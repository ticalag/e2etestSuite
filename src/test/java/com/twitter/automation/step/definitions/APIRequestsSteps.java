package com.twitter.automation.step.definitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static com.twitter.automation.api.requests.TwitterAPIRequests.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;


public class APIRequestsSteps {

    private int numberOfInitialTweetsFromApi;
    private int numberOfActualTweetsFromApi;
    private String tweetStatus;
    private String selectedTweetID;
    private static int initialNumberOfFavoriteTweets;

    public String getSelectedTweetID() {
        return selectedTweetID;
    }

    private void setSelectedTweetID(String selectedTweetID) {
        this.selectedTweetID = selectedTweetID;
    }

    public static int getInitialNumberOfFavoriteTweets() {
        return initialNumberOfFavoriteTweets;
    }

    private static void setInitialNumberOfFavoriteTweets(int numberOfFavoriteTweets) {
        initialNumberOfFavoriteTweets = numberOfFavoriteTweets;
    }

    public int getNumberOfInitialTweetsFromApi() {
        return numberOfInitialTweetsFromApi;
    }

    private void setNumberOfInitialTweetsFromApi(int numberOfInitialTweetsFromApi) {
        this.numberOfInitialTweetsFromApi = numberOfInitialTweetsFromApi;
    }

    public int getNumberOfActualTweetsFromApi() {
        return numberOfActualTweetsFromApi;
    }

    private void setNumberOfActualTweetsFromApi(int numberOfActualTweetsFromApi) {
        this.numberOfActualTweetsFromApi = numberOfActualTweetsFromApi;
    }

    public String getTweetStatus() {
        return tweetStatus;
    }

    private void setTweetStatus(String tweetStatus) {
        this.tweetStatus = tweetStatus;
    }

    @Given("that we request the number of tweets from API")
    public void weRequestTheNumberOfTweetsFromAPI() {
        setNumberOfInitialTweetsFromApi(requestListOfTweetsIdsFromAPI().size());
    }

    @When("we post {int} Tweet using API request")
    public void wePostTweetUsingAPIRequest(int numberOfTweets) {
        for (int i = 0; i < numberOfTweets; i++) {
            postTweetThroughAPI();
        }
    }

    @Given("^that we add as favorite tweet the (1|2|3)(?:st|nd|rd) posted tweet$")
    public void thatWeAddAsFavoriteTweetThePostedTweet(int tweetPosition) {
        addFavoriteTweetThroughApiRequest(requestListOfTweetsIdsFromAPI().get(tweetPosition - 1).toString());
    }

    @Then("^we should have ([^\"]*) favorite tweet added to the list$")
    public void weShouldHaveAFavoriteTweetAddedToTheList(int numberOfTweets) {
        assertEquals(requestListOfFavoriteTweetsIdsFromAPI().size(), getInitialNumberOfFavoriteTweets() + numberOfTweets);
    }

    @When("^that we get the tweet status from the (1|2|3)(?:st|nd|rd) position$")
    public void thatWeGetTheTweetStatusFromTheStPosition(int tweetPosition) {
        setTweetStatus(retrieveTweetStatusFromAPI(String.valueOf(requestListOfTweetsIdsFromAPI().get(tweetPosition - 1))));
    }

    @Then("^the expected tweet status is retrieved from API$")
    public void theExpectedTweetStatusIsRetrievedFromAPI() {
        assertEquals(getUniqueTweetMessage(), getTweetStatus());
    }

    @Given("^that we update the users language and time zone to \"([^\"]*)\" and \"([^\"]*)\" Time Zone$")
    public void thatWeUpdateTheUsersLanguageAndTimeZoneToAndTimeZone(String languageCode, String timeZone) {
        updateUserProfileLanguageUsingApiRequest(languageCode, timeZone);
    }

    @When("^we retrieve the account settings$")
    public void weRetrieveTheAccountSettings() {
        getUserTimeZoneAndLanguageFromAPI();
    }

    @Then("^the language should be \"([^\"]*)\" and the time-zone \"([^\"]*)\"$")
    public void theLanguageShouldBeAndTheTimeZone(String languageCode, String timeZone) {
        assertEquals(getUserLanguageFromAPI(), languageCode);
        assertEquals(getUserTimeZoneFromAPI(), timeZone);
    }

    @Given("^that we want to delete the (1|2|3)(?:st|nd|rd) tweet from the list$")
    public void thatWeWantToDeleteTheStTweetFromTheList(int tweetPosition) {
        setSelectedTweetID(requestListOfTweetsIdsFromAPI().get(tweetPosition - 1).toString());
        setNumberOfInitialTweetsFromApi(requestListOfTweetsIdsFromAPI().size());
    }


    @When("^we send a delete request with the selected tweet id$")
    public void weSendADeleteRequestWithTheSelectedTweetId() {
        deleteSelectedTweetRequest(getSelectedTweetID());
    }

    @Then("^that id will no longer exist in the user list and the list will be shorter by (\\d+)$")
    public void thatIdWillNoLongerExistInTheUserListAndTheListWillBeShorterBy(int numberOfDeletedTweets) {
        tweetIDShouldNotBeInTheUserList(getSelectedTweetID());
        setNumberOfActualTweetsFromApi(requestListOfTweetsIdsFromAPI().size());
        assertEquals(getNumberOfActualTweetsFromApi(), getNumberOfInitialTweetsFromApi() - numberOfDeletedTweets);
    }

    private void tweetIDShouldNotBeInTheUserList(String selectedTweetID) {
        for (Object tweetID : requestListOfTweetsIdsFromAPI()) {
            assertNotEquals(tweetID.toString(), selectedTweetID);
        }
    }

    @Given("^that we request the number of favorite tweets from API$")
    public void thatWeRequestTheNumberOfFavoriteTweetsFromAPI() {
        setInitialNumberOfFavoriteTweets(requestListOfFavoriteTweetsIdsFromAPI().size());
    }

    @And("^requesting the list of tweets from API$")
    public void requestingTheListOfTweetsFromAPI() {
        setNumberOfActualTweetsFromApi(requestListOfTweetsIdsFromAPI().size());
    }

    @Then("should have {word}{int} tweets is in the list")
    public void shouldHaveExpectedNumberOfTweets(String symbol, int numberOfTweets) {
        if ("-".equals(symbol)) {
            assertEquals(getNumberOfActualTweetsFromApi(), getNumberOfInitialTweetsFromApi() - numberOfTweets);
        } else {
            assertEquals(getNumberOfActualTweetsFromApi(), getNumberOfInitialTweetsFromApi() + numberOfTweets);
        }
    }
}
