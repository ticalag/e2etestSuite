package com.twitter.automation.stepdefs;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static com.twitter.automation.api.TwitterAPITests.*;
import static com.twitter.automation.api.TwitterAPITests.getNumberTweetsFromHomeTimeline;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class APIRequestsSteps {

    private int numberOfTweetsFromApiBeforeRequest;
    private int numberOfTweetsFromApiAfterRequest;
    private String tweetStatus;
    private String selectedTweetID;
    private static int numberOfFavoriteTweetsBeforeRequest;
    private static int numberOfFavoriteTweetsAfterRequest;

    public String getSelectedTweetID() {
        return selectedTweetID;
    }

    private void setSelectedTweetID(String selectedTweetID) {
        this.selectedTweetID = selectedTweetID;
    }

    public static int getNumberOfFavoriteTweetsBeforeRequest() {
        return numberOfFavoriteTweetsBeforeRequest;
    }

    public static void setNumberOfFavoriteTweetsBeforeRequest(int numberOfFavoriteTweets) {
        numberOfFavoriteTweetsBeforeRequest = numberOfFavoriteTweets;
    }

    public static int getNumberOfFavoriteTweetsAfterRequest() {
        return numberOfFavoriteTweetsAfterRequest;
    }

    public static void setNumberOfFavoriteTweetsAfterRequest(int numberOfFavoriteTweets) {
        numberOfFavoriteTweetsAfterRequest = numberOfFavoriteTweets;
    }

    public int getNumberOfTweetsFromApiBeforeRequest() {
        return numberOfTweetsFromApiBeforeRequest;
    }

    private void setNumberOfTweetsFromApiBeforeRequest(int numberOfTweetsFromApiBeforeRequest) {
        this.numberOfTweetsFromApiBeforeRequest = numberOfTweetsFromApiBeforeRequest;
    }

    public int getNumberOfTweetsFromApiAfterRequest() {
        return numberOfTweetsFromApiAfterRequest;
    }

    private void setNumberOfTweetsFromApiAfterRequest(int numberOfTweetsFromApiAfterRequest) {
        this.numberOfTweetsFromApiAfterRequest = numberOfTweetsFromApiAfterRequest;
    }

    public String getTweetStatus() {
        return tweetStatus;
    }

    private void setTweetStatus(String tweetStatus) {
        this.tweetStatus = tweetStatus;
    }

    @Given("that we get the number of tweets from API")
    public void weGetTheNumberOfTweetsFromAPI() {
        setNumberOfTweetsFromApiBeforeRequest(getNumberTweetsFromHomeTimeline());
    }

    @When("we post (.*) Tweet using API request")
    public void wePostTweetUsingAPIRequest(int numberOfTweets) {
        for (int i = 0; i < numberOfTweets; i++) {
            postTweetThroughAPI();
        }
    }

    @Then("the tweet is posted and there is (.*) tweet added to the list")
    public void theTweetIsPostedAndThereIsTweetAddedToTheList(int expectedNumberOfTweets) {
        setNumberOfTweetsFromApiAfterRequest(getNumberTweetsFromHomeTimeline());
        assertEquals(getNumberOfTweetsFromApiBeforeRequest() + expectedNumberOfTweets, getNumberOfTweetsFromApiAfterRequest());
    }

    @Given("^that we add as favorite tweet the (1|2|3)(?:st|nd|rd) posted tweet$")
    public void thatWeAddAsFavoriteTweetThePostedTweet(int tweetPosition) {
        setNumberOfFavoriteTweetsBeforeRequest(getNumberOfFavoriteTweetsIdsFromAPI());
        addFavoriteTweetByAPI(requestListOfTweetsIdsFromAPI().get(tweetPosition - 1).toString());
        setNumberOfFavoriteTweetsAfterRequest(getNumberOfFavoriteTweetsIdsFromAPI());

    }

    @Then("^we should have a favorite tweet added to the list$")
    public void weShouldHaveAFavoriteTweetAddedToTheList() {
        assertEquals(getNumberOfFavoriteTweetsBeforeRequest() + 1, getNumberOfFavoriteTweetsAfterRequest());
    }

    @When("^that we get the tweet status from the (1|2|3)(?:st|nd|rd) position$")
    public void thatWeGetTheTweetStatusFromTheStPosition(int tweetPosition) {
        setTweetStatus(retrieveTweetStatusFromAPI(String.valueOf(requestListOfTweetsIdsFromAPI().get(tweetPosition - 1))));
    }

    @Then("^the expected tweet status is retrieved from API$")
    public void theExpectedTweetStatusIsRetrievedFromAPI() {
        assertEquals(getExpectedTweetMessage(), getTweetStatus());
    }

    @Given("^that we update the users language and time zone to \"([^\"]*)\" and \"([^\"]*)\" Time Zone$")
    public void thatWeUpdateTheUsersLanguageAndTimeZoneToAndTimeZone(String languageCode, String timeZone) {
        updateUserProfileLanguage(languageCode, timeZone);
    }

    @When("^we retrieve the account settings$")
    public void weRetrieveTheAccountSettings() {
        getAccountSettingsFromAPI();
    }

    @Then("^the language should be \"([^\"]*)\" and the time-zone \"([^\"]*)\"$")
    public void theLanguageShouldBeAndTheTimeZone(String languageCode, String timeZone) {
        assertEquals(getUserLanguage(), languageCode);
        assertEquals(getUserTimeZone(), timeZone);
    }

    @Given("^that we want to delete the (1|2|3)(?:st|nd|rd) tweet from the list$")
    public void thatWeWantToDeleteTheStTweetFromTheList(int tweetPosition) {
        setSelectedTweetID(requestListOfTweetsIdsFromAPI().get(tweetPosition - 1).toString());
        setNumberOfTweetsFromApiBeforeRequest(requestListOfTweetsIdsFromAPI().size());
    }


    @When("^we send a delete request with the selected tweet id$")
    public void weSendADeleteRequestWithTheSelectedTweetId() {
        deleteSelectedTweetRequest(getSelectedTweetID());
    }

    @Then("^that id will no longer exist in the user list and the list will be shorter by (\\d+)$")
    public void thatIdWillNoLongerExistInTheUserListAndTheListWillBeShorterBy(int deletedTweets) {
        tweetIDShouldNotBeInTheUserList(getSelectedTweetID());
        setNumberOfTweetsFromApiAfterRequest(requestListOfTweetsIdsFromAPI().size());
        assertEquals(getNumberOfTweetsFromApiBeforeRequest() - deletedTweets, getNumberOfTweetsFromApiAfterRequest());
    }

    private void tweetIDShouldNotBeInTheUserList(String selectedTweetID) {
        for (Object tweetID : requestListOfTweetsIdsFromAPI()) {
            assertNotEquals(tweetID.toString(), selectedTweetID);
        }
    }
}
