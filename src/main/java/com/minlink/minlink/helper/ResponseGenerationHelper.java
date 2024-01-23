package com.minlink.minlink.helper;

import com.minlink.minlink.model.Url;
import com.minlink.minlink.model.UrlResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.minlink.minlink.constants.Constants.ACTIVATED_CONSTANT;
import static com.minlink.minlink.constants.Constants.DEACTIVATED_CONSTANT;
import static com.minlink.minlink.constants.Constants.DOMAIN_PREFIX;
import static com.minlink.minlink.constants.Constants.ERROR_CONSTANT;
import static com.minlink.minlink.constants.Constants.INVALID_CREDENTIALS_RECEIVED;
import static com.minlink.minlink.constants.Constants.LOGIN;
import static com.minlink.minlink.constants.Constants.MESSAGE_CONSTANT;
import static com.minlink.minlink.constants.Constants.INTERNAL_ERROR_MESSAGE;
import static com.minlink.minlink.constants.Constants.SHORT_URL;
import static com.minlink.minlink.constants.Constants.SHORT_URL_NOT_FOUND;
import static com.minlink.minlink.constants.Constants.UNAUTHORISED_REQUEST;
import static com.minlink.minlink.constants.Constants.URLS_CONSTANT;
import static com.minlink.minlink.constants.Constants.URL_CONSTANT;
import static com.minlink.minlink.constants.Constants.URL_IS_ALREADY;
import static com.minlink.minlink.constants.Constants.USER_NOT_FOUND_FOR_THE_GIVEN_ID;

public class ResponseGenerationHelper {


    public static ResponseEntity<Map<String, Object>> handleAuth(OAuth2User principal) {
        Map<String, Object> response = new HashMap<>();
        if (principal == null) {
            response.put(ERROR_CONSTANT, UNAUTHORISED_REQUEST);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(response);
        }
        String userId = principal.getAttribute(LOGIN);
        if (!StringUtils.hasText(userId)) {
            response.put(ERROR_CONSTANT, INVALID_CREDENTIALS_RECEIVED);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(response);
        }
        return null;
    }

    public static ResponseEntity<Map<String, Object>> userNotFoundError() {
        Map<String, Object> response = new HashMap<>();
        response.put(ERROR_CONSTANT, USER_NOT_FOUND_FOR_THE_GIVEN_ID);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

    public static ResponseEntity<Map<String, Object>> generateUrlsReport(List<Url> urlsOfUser) {
        Map<String, Object> response = new HashMap<>();
        List<UrlResponse> urlResponse = urlsOfUser.stream()
                .map(Url::getUrlResponse)
                .collect(Collectors.toList());
        response.put(URLS_CONSTANT, urlResponse);
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<Map<String, Object>> exceptionResponse() {
        Map<String, Object> response = new HashMap<>();
        response.put(ERROR_CONSTANT, INTERNAL_ERROR_MESSAGE);
        return ResponseEntity.internalServerError().body(response);
    }

    public static ResponseEntity<Map<String, Object>> successResponse(boolean deactivateRequest) {
        Map<String, Object> response = new HashMap<>();
        response.put(MESSAGE_CONSTANT, URL_CONSTANT + (deactivateRequest ? DEACTIVATED_CONSTANT : ACTIVATED_CONSTANT));
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<Map<String, Object>> getMapResponseEntity(Url url) {
        Map<String, Object> response = new HashMap<>();
        response.put(SHORT_URL, DOMAIN_PREFIX + url.getShortUrl());
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<Map<String, Object>> nonUpdateResponse(boolean deactivateRequest) {
        Map<String, Object> response = new HashMap<>();
        response.put(MESSAGE_CONSTANT, URL_IS_ALREADY + (deactivateRequest ? DEACTIVATED_CONSTANT : ACTIVATED_CONSTANT));
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(response);
    }

    public static ResponseEntity<Map<String, Object>> shortUrlNotFoundResponse() {
        Map<String, Object> response = new HashMap<>();
        response.put(MESSAGE_CONSTANT, SHORT_URL_NOT_FOUND);
        return ResponseEntity.badRequest().body(response);
    }
}
