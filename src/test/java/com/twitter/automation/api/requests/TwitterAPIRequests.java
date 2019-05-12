package com.twitter.automation.api.requests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

import static com.twitter.automation.utils.CommonUtils.*;
import static io.restassured.RestAssured.given;

public class TwitterAPIRequests {

    private static final String BASE_URI = loadPropertiesFile().getProperty("twitter.api.uri");
    private static final String API_KEY = loadPropertiesFile().getProperty("api.key");
    private static final String API_SECRET_KEY = loadPropertiesFile().getProperty("api.secret.key");
    private static final String ACCESS_TOKEN = loadPropertiesFile().getProperty("access.token");
    private static final String ACCESS_TOKEN_SECRET = loadPropertiesFile().getProperty("access.token.secret");
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterAPIRequests.class);
    private static String uniqueTweetMessage;
    private static String userTimeZoneFromAPI;
    private static String userLanguageFromAPI;


    public static String getUniqueTweetMessage() {
        return TwitterAPIRequests.uniqueTweetMessage;
    }

    private static void setUniqueTweetMessage(String tweetMessage) {
        uniqueTweetMessage = tweetMessage;
    }

    private static String createUniqueTweetMessage() {
        return "Tweet " + UUID.randomUUID().toString() + " added through API POST request";
    }

    public static String getUserTimeZoneFromAPI() {
        return userTimeZoneFromAPI;
    }

    private static void setUserTimeZoneFromAPI(String userTimeZone) {
        userTimeZoneFromAPI = userTimeZone;
    }

    public static String getUserLanguageFromAPI() {
        return userLanguageFromAPI;
    }

    private static void setUserLanguageFromAPI(String userLanguage) {
        userLanguageFromAPI = userLanguage;
    }

    public static List<Object> requestListOfTweetsIdsFromAPI() {
        RestAssured.baseURI = BASE_URI + STATUSES;
        return given().auth().oauth(API_KEY, API_SECRET_KEY, ACCESS_TOKEN, ACCESS_TOKEN_SECRET)
                .when().get("/user_timeline.json?screen_name=e2e_tests&count=200")
                .then().log().ifValidationFails().statusCode(200).extract().body().jsonPath().getList("id");
    }

    public static List<Object> requestListOfFavoriteTweetsIdsFromAPI() {
        RestAssured.baseURI = BASE_URI + FAVORITES;
        return given().auth().oauth(API_KEY, API_SECRET_KEY, ACCESS_TOKEN, ACCESS_TOKEN_SECRET)
                .when().get("/list.json?count=200")
                .then().log().ifValidationFails().statusCode(200).extract().body().jsonPath().getList("id");
    }


    public static void postTweetThroughAPI() {
        RestAssured.baseURI = BASE_URI + STATUSES;
        setUniqueTweetMessage(createUniqueTweetMessage());
        Response response = given().auth().oauth(API_KEY, API_SECRET_KEY, ACCESS_TOKEN, ACCESS_TOKEN_SECRET)
                .queryParam("status", getUniqueTweetMessage())
                .when().post("/update.json")
                .then().log().ifValidationFails().statusCode(200).extract().response();
        JsonPath jsonPath = new JsonPath(response.asString());
        LOGGER.info("tweet with id [{}] and status [{}] was created", jsonPath.get("id"), getUniqueTweetMessage());
    }


    public static void addFavoriteTweetThroughApiRequest(String tweetID) {
        RestAssured.baseURI = BASE_URI + FAVORITES;
        given().auth().oauth(API_KEY, API_SECRET_KEY, ACCESS_TOKEN, ACCESS_TOKEN_SECRET)
                .when().post("/create.json?id=" + tweetID)
                .then().log().ifValidationFails().statusCode(200).extract().response();
    }


    public static String retrieveTweetStatusFromAPI(String tweetID) {
        RestAssured.baseURI = BASE_URI + STATUSES;
        return given().auth().oauth(API_KEY, API_SECRET_KEY, ACCESS_TOKEN, ACCESS_TOKEN_SECRET)
                .when().get("/show.json?id=" + tweetID)
                .then().log().ifValidationFails().statusCode(200).extract().body().jsonPath().get("text");
    }


    public static void updateUserProfileLanguageUsingApiRequest(String languageCode, String timeZone) {
        RestAssured.baseURI = BASE_URI + ACCOUNT;
        given().auth().oauth(API_KEY, API_SECRET_KEY, ACCESS_TOKEN, ACCESS_TOKEN_SECRET)
                .when().post("/settings.json?lang=" + languageCode.toLowerCase() + "&time_zone=" + timeZone)
                .then().log().ifValidationFails().statusCode(200).extract().response();
        LOGGER.info("updating user profile language code [{}] and time zone [{}] ", languageCode, timeZone);
    }

    public static void getUserTimeZoneAndLanguageFromAPI() {
        RestAssured.baseURI = BASE_URI + ACCOUNT;
        Response response = given().auth().oauth(API_KEY, API_SECRET_KEY, ACCESS_TOKEN, ACCESS_TOKEN_SECRET)
                .when().post("/settings.json")
                .then().log().ifValidationFails().statusCode(200).extract().response();

        JsonPath jsonPath = new JsonPath(response.asString());

        setUserTimeZoneFromAPI(jsonPath.get("time_zone.name").toString());
        setUserLanguageFromAPI(jsonPath.get("language").toString());
    }

    public static void deleteSelectedTweetRequest(String tweetID) {
        RestAssured.baseURI = BASE_URI + STATUSES;
        LOGGER.info("deleting using API for tweet id: [{}]", tweetID);
        given().auth().oauth(API_KEY, API_SECRET_KEY, ACCESS_TOKEN, ACCESS_TOKEN_SECRET)
                .when().post("/destroy/" + tweetID + ".json")
                .then().log().ifValidationFails().statusCode(200).extract().response();
    }
}


