package com.minlink.minlink.constants;

public class Constants {

    public static final String STATUS_200 = "200";
    public static final String STATUS_304 = "304";
    public static final String STATUS_307 = "307";
    public static final String STATUS_400 = "400";
    public static final String STATUS_401 = "401";
    public static final String STATUS_404 = "404";
    public static final String STATUS_500 = "500";

    public static final String DEACTIVATE_ENDPOINT = "/deactivate";
    public static final String ACTIVATE_ENDPOINT = "/activate";
    public static final String USER_ENDPOINT = "/user";
    public static final String CREATE_ENDPOINT = "/create";
    public static final String REPORT_ENDPOINT = "/report";
    public static final String REDIRECT_ENDPOINT = "/{shortUrl:[a-z,A-z,0-9]{7}}";

    public static final String APPLICATION_JSON = "application/json";
    public static final String LOGIN = "login";
    public static final String GITHUB = "github";
    public static final String USERID_CONCATENATION = ":";
    public static final String LOCATION = "Location";
    public static final String MESSAGE_CONSTANT = "message";
    public static final String SHORT_URL = "shortUrl";
    public static final String HIT_COUNT = "hitCount";

    public static final String DOMAIN_PREFIX = "http://localhost:8080/";

    public static final String NAME_CONSTANT = "name";
    public static final String ERROR_CONSTANT = "error";
    public static final String URLS_CONSTANT = "urls";
    public static final String UNAUTHORISED_REQUEST = "Unauthorised request";
    public static final String INVALID_CREDENTIALS_RECEIVED = "Invalid credentials received";
    public static final String USER_NOT_FOUND_FOR_THE_GIVEN_ID = "user not found for the given id";
    public static final String DEACTIVATE_THE_GIVEN_SHORT_URL = "Deactivate the given shortUrl";
    public static final String GIVEN_SHORT_URL_IS_DEACTIVATED = "Given short url is deactivated";
    public static final String GIVEN_SHORT_URL_IS_NOT_FOUND = "Given short url is not found";
    public static final String ERROR_DURING_AUTHENTICATION = "Error during authentication";
    public static final String GIVEN_SHORT_URL_IS_ALREADY_DEACTIVATED = "Given short url is already deactivated";
    public static final String APPLICATION_RAN_INTO_ERROR_WHILE_PROCESSING_REQUEST = "Application ran into error while processing request";
    public static final String ACTIVATE_THE_GIVEN_SHORT_URL = "Activate the given shortUrl";
    public static final String CREATE_A_NEW_SHORT_URL = "Create a new shortUrl";
    public static final String THE_SHORT_URL_CREATED = "The short url created";
    public static final String REDIRECT_A_SHORT_URL = "Redirect a shortUrl";
    public static final String REDIRECTS_TO_ORIGINAL_URL = "Redirects to original Url";
    public static final String WHEN_SHORT_URL_IS_NOT_FOUND_OR_IS_DEACTIVATED = "When short url is not found or is deactivated";
    public static final String GET_THE_SHORT_URLS_OF_A_USER_AND_ITS_STATS = "Get the short urls of a user and its stats";
    public static final String THE_URL_DATA_OF_THE_USER = "The url data of the user";
    public static final String INTERNAL_USAGE_USED_TO_GET_THE_CURRENT_USER_NAME = "Internal usage : Used to get the current user Name";
    public static final String END_POINT_TO_GET_USER_ID = "End point to get userId";
    public static final String INTERNAL_ERROR_MESSAGE = "Ran into an issue while processing the request, please contact system administrator";
    public static final String SHORT_URL_NOT_FOUND = "Short Url not found";
    public static final String DEACTIVATED_CONSTANT = "deactivated";
    public static final String ACTIVATED_CONSTANT = "activated";
    public static final String URL_CONSTANT = "Url ";
    public static final String URL_IS_ALREADY = "Url is already ";

    public static final String MESSAGE_URL_DEACTIVATED = "{ \"message\": \"Url deactivated\"}";
    public static final String MESSAGE_URL_ACTIVATED = "{ \"message\": \"Url activated\"}";
    public static final String ERROR_UNAUTHORISED_REQUEST = "{ \"error\": \"Unauthorised request\"}";
    public static final String MESSAGE_SHORT_URL_NOT_FOUND = "{ \"message\": \"Short Url not found\"}";
    public static final String MESSAGE_URL_IS_ALREADY_DEACTIVATED = "{ \"message\": \"Url is already deactivated\"}";
    public static final String MESSAGE_URL_IS_ALREADY_ACTIVATED = "{ \"message\": \"Url is already activated\"}";
    public static final String ERROR_RAN_INTO_AN_ISSUE = "{ \"error\": \"" + INTERNAL_ERROR_MESSAGE + "\"}";
    public static final String SAMPLE_CREATE_RESPONSE = "{ \"shortUrl\": \"http://localhost:8080/ase2ww1\"}";
    public static final String NAME_JACK = "{ \"name\": \"jack\"}";
    public static final String REPORT_SAMPLE_RESPONSE = "{ \"urls\": [ {\"originalUrl\" : \"http://example.com\"," +
            "\"isDeactivated\" : false,\"shortUrl\" : \"s14swa2\",\"hitCount\" : \"12\" }," +
            " {\"originalUrl\" : \"http://anotherexample.com\",\"isDeactivated\" : true,\"shortUrl\" : \"cf3r4fe\",\"hitCount\" : \"9\" }]}";

}
