
Feature: Tweeter interface
  As a tweeter user
  I want to be able to see all the components that are expected to be displayed for all the loggedin users
  And navigate, tweet and successfully communicate with other users

  Scenario: All the components should be displayed when the user is logged in
    Given that we are on the home timeline of the user
    When the user is "logs in"
    Then he should see all the home page components

  Scenario: Logged out user should see the login/registration page
    Given that the user goes to the tweeter home page
    And the user is "logged in"
    Then the login page should appear with the expected sign in / login components


  Scenario: Logging out should redirect the user to the login/registration page
    Given that we are on the home timeline of the user
    When the user is "logs in"
    And the user is "logged in"
    When the user is "logs out"
    And the user is "logged out"
    Then the login page should appear with the expected sign in / login components

  Scenario: Tweeter user should be able to post and delete tweets
    Given that we are on the home timeline of the user
    When the user is "logs in"
    And the user is "logged in"
    And the user is posting a tweet with the following text "Posting a tweet"
    Then the posted tweet should be displayed with the above mentioned text
    And when the user decides to delete his tweet
    Then the deleted tweet should no longer be displayed

  Scenario: Tweet user should be able to reply to tweet
    Given that we are on the home timeline of the user
    When the user is "logs in"
    And the user is "logged in"
    And the user is replying to the "first" tweet with the following text "Replying to a tweet"
    Then the 'first' tweet should contain the above mention message as a reply

  Scenario: Tweet user should be able to add a tweet as a favorite
    Given that we are on the home timeline of the user
    And the user is "logs in"
    And the user is "logged in"
    When the user adds the "first" tweet as a favorite
    Then the 'first' tweet is added as a favorite

  Scenario: Navigating to different tabs should work
    Given that we are on the home timeline of the user
    And the user is "logs in"
    And the user is "logged in"
    When the user clicks on "Moments" tab
    Then he should be redirected to the "Moments" page

