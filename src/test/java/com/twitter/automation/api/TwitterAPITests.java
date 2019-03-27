package com.twitter.automation.api;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class TwitterAPITests {

    private static final String ACCOUNT = "account";
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("common");

    private static final String API_KEY = "rZiwgBfDxj9lF03awxCsaAyAd";
    private static final String API_SECRET_KEY = "OtYncFaRmqZI0FVCDZBnM1Se8kCPI2O30bF3NJhQAPDceU82BQ";
    private static final String ACCESS_TOKEN = "1103306156141883392-EhlksaKvWt1KRkcf2UBBiBEi2E9x6h";
    private static final String ACCESS_TOKEN_SECRET = "zme1ruserdSgeqLJ7T4DO0yBSdoMHfWbsxVLUfDgs5DFS";
    private static String BASE_URI = resourceBundle.getString("twitter.api.resource.url");

    private static final String FAVORITES = "favorites";
    private static final String STATUSES = "statuses";
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterAPITests.class);
    private static final String EXPECTED_TWEET_MESSAGE = "Tweet " + UUID.randomUUID().toString() + " added through API POST request";
    private static String userTimeZone;
    private static String userLanguage;


    public static String getExpectedTweetMessage() {
        return EXPECTED_TWEET_MESSAGE;
    }

    public static String getUserTimeZone() {
        return userTimeZone;
    }

    private static void setUserTimeZone(String userTimeZone) {
        TwitterAPITests.userTimeZone = userTimeZone;
    }

    public static String getUserLanguage() {
        return userLanguage;
    }

    private static void setUserLanguage(String userLanguage) {
        TwitterAPITests.userLanguage = userLanguage;
    }


    public static int getNumberTweetsFromHomeTimeline() {
        RestAssured.baseURI = BASE_URI + STATUSES;
        return given().auth().oauth(API_KEY, API_SECRET_KEY, ACCESS_TOKEN, ACCESS_TOKEN_SECRET)
                .when().get("/user_timeline.json?screen_name=e2e_tests")
                .then().assertThat().statusCode(200).extract().body().jsonPath().getList("id").size();
    }

    public static List<Object> requestListOfTweetsIdsFromAPI() {
        RestAssured.baseURI = BASE_URI + STATUSES;
        return given().auth().oauth(API_KEY, API_SECRET_KEY, ACCESS_TOKEN, ACCESS_TOKEN_SECRET)
                .when().get("/user_timeline.json?screen_name=e2e_tests")
                .then().assertThat().statusCode(200).extract().body().jsonPath().getList("id");
    }

    public static int getNumberOfFavoriteTweetsIdsFromAPI() {
        RestAssured.baseURI = BASE_URI + FAVORITES;
        return given().auth().oauth(API_KEY, API_SECRET_KEY, ACCESS_TOKEN, ACCESS_TOKEN_SECRET)
                .when().get("/list.json")
                .then().assertThat().statusCode(200).extract().body().jsonPath().getList("id").size();
    }


    public static void postTweetThroughAPI() {
        RestAssured.baseURI = BASE_URI + STATUSES;
        Response response = given().auth().oauth(API_KEY, API_SECRET_KEY, ACCESS_TOKEN, ACCESS_TOKEN_SECRET)
                .queryParam("status", EXPECTED_TWEET_MESSAGE)
                .when().post("/update.json")
                .then().assertThat().statusCode(200).extract().response();
        JsonPath jsonPath = new JsonPath(response.asString());
        LOGGER.info("tweet with id [{}] and status [{}] was created",jsonPath.get("id"), EXPECTED_TWEET_MESSAGE);
    }


    public static void addFavoriteTweetByAPI(String tweetID) {
        RestAssured.baseURI = BASE_URI + FAVORITES;
        given().auth().oauth(API_KEY, API_SECRET_KEY, ACCESS_TOKEN, ACCESS_TOKEN_SECRET)
                .when().post("/create.json?id=" + tweetID)
                .then().assertThat().statusCode(200).extract().response();
    }


    public static String retrieveTweetStatusFromAPI(String tweetID) {
        RestAssured.baseURI = BASE_URI + STATUSES;
        return given().auth().oauth(API_KEY, API_SECRET_KEY, ACCESS_TOKEN, ACCESS_TOKEN_SECRET)
                .when().get("/show.json?id=" + tweetID)
                .then().assertThat().statusCode(200).extract().body().jsonPath().get("text");
    }


    public static void updateUserProfileLanguage(String languageCode, String timeZone) {
        RestAssured.baseURI = BASE_URI + ACCOUNT;
        given().auth().oauth(API_KEY, API_SECRET_KEY, ACCESS_TOKEN, ACCESS_TOKEN_SECRET)
                .when().post("/settings.json?lang=" + languageCode.toLowerCase() + "&time_zone=" + timeZone)
                .then().assertThat().statusCode(200).extract().response();
    }

    // TODO retrieve all the account settings fields/values
    public static void getAccountSettingsFromAPI() {
        RestAssured.baseURI = BASE_URI + ACCOUNT;
        Response response = given().auth().oauth(API_KEY, API_SECRET_KEY, ACCESS_TOKEN, ACCESS_TOKEN_SECRET)
                .when().post("/settings.json")
                .then().assertThat().statusCode(200).extract().response();

        JsonPath jsonPath = new JsonPath(response.asString());

        setUserTimeZone(jsonPath.get("time_zone.name").toString());
        setUserLanguage(jsonPath.get("language").toString());
    }


    public static void deleteSelectedTweetRequest(String tweetID) {
        RestAssured.baseURI = BASE_URI + STATUSES;
        given().auth().oauth(API_KEY, API_SECRET_KEY, ACCESS_TOKEN, ACCESS_TOKEN_SECRET)
                .when().post("/destroy/" + tweetID + ".json")
                .then().assertThat().statusCode(200).extract().response();
    }
}


