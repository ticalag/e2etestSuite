@smokeTestsAPI @regression

Feature: Test Twitter API Requests
  As members of the twitter API team
  We should ensure that our APIs are working and returning correct response
  So that organizations can use our API with confidence

  @postTweetAPI
  Scenario: Successfully post a Tweet using POST request
    Given that we request the number of tweets from API
    When we post 1 Tweet using API request
    And  requesting the list of tweets from API
    Then should have +1 tweets is in the list


  @addFavoriteTweet
  Scenario: Favorite Tweet is added to the favorite tweet list by API Response
    Given that we request the number of favorite tweets from API
    When that we add as favorite tweet the 1st posted tweet
    Then we should have 1 favorite tweet added to the list

  @profileUpdate
  Scenario Outline: Profile update to <language> using API request
    Given that we update the users language and time zone to "<language>" and "<timeZone>" Time Zone
    When we retrieve the account settings
    Then the language should be "<language>" and the time-zone "<timeZone>"
    Examples:
      | language | timeZone |
      | es       | Madrid   |
      | fr       | Paris    |
      | en       | London   |

  @deleteTweetAPI
  Scenario: Successfully delete selected tweet using delete request
    Given that we request the number of tweets from API
    And that we want to delete the 2nd tweet from the list
    When we send a delete request with the selected tweet id
    Then that id will no longer exist in the user list and the list will be shorter by 1

  @tweetStatus
  Scenario: Retrieve tweet status using GET statuses/show Request
    Given we post 1 Tweet using API request
    When that we get the tweet status from the 1st position
    Then the expected tweet status is retrieved from API

