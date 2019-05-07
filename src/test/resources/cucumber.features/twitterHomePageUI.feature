@smoke-tests

Feature: Twitter interface
  As a tweeter user
  I want to be able to see all the components that are expected to be displayed for all the loggedin users
  And navigate, tweet and successfully communicate with other users

  @homeComponents
  Scenario: All the components should be displayed when the user is logged in
    Given that the user goes to the tweeter home page
    And the user is "logged out"
    When the user logs "in"
    Then he should see all the home page components

  @loginPage
  Scenario: Logged out user should see the login/registration page
    Given that the user goes to the tweeter home page
    When the user is "logged out"
    Then the login page should appear with the expected sign in / login components

  @redirect
  Scenario: Logging out should redirect the user to the login/registration page
    Given that the user goes to the tweeter home page
    And the user is "logged out"
    When the user logs "in"
    And the user is "logged in"
    When the user logs "out"
    Then the login page should appear with the expected sign in / login components

  @postTweet
  Scenario: Tweeter user should be able to post and delete tweets
    Given that the user goes to the tweeter home page
    And the user is "logged out"
    When the user logs "in"
    And the user navigates to the user timeline
    And that we request the number of tweets from API
    And open the top nav tweet modal
    And enters the following text "Posting a tweet using selenium"
    And when the user clicks on the emoji menu
    Then the emoji content is displayed and after clicking on it it's no longer displayed
    And user clicks on the tweet button and waits until the tweet is posted
    And requesting the list of tweets from API, we should have +1 tweets is in the list
    And we wait until the new tweet bar is displayed and we click on it to update the tweet message list
    Then the posted tweet should be displayed and should contain the above message


  @replyTweet
  Scenario: Tweet user should be able to reply to tweet
    Given that the user goes to the tweeter home page
    And the user is "logged out"
    When the user logs "in"
    And the user is replying to the 1st tweet with the following text "Replying to a tweet"
    And we wait until the new tweet bar is displayed and we click on it to update the tweet message list
    Then the 1st tweet should contain the "Replying to a tweet" message as a reply

  @deleteTweet
  Scenario: Tweeter user should be able to post and delete tweets
    Given that the user goes to the tweeter home page
    And the user is "logged out"
    When the user logs "in"
    And that we request the number of tweets from API
    And then the user decides to delete 2nd tweet
    Then the tweet from the 2nd position should no longer be displayed
    And requesting the list of tweets from API, we should have -1 tweets is in the list


  @favoriteTweet
  Scenario: Tweet user should be able to add a tweet as a favorite
    Given that the user goes to the tweeter home page
    And the user is "logged out"
    When the user logs "in"
    And the user navigates to the user timeline
    When the user adds the 1st tweet as a favorite
    Then there is "1" favorite tweet added to the list

  @tabNavigation
  Scenario Outline: Navigating to different tabs should work
    Given that the user goes to the tweeter home page
    And the user is "logged out"
    When the user logs "in"
    When the user clicks on "<tabName>" tab
    Then he should be redirected to the "<tabName>" page
    Examples:
      | tabName       |
      | Moments       |
      | Notifications |


