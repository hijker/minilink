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

public class ResponseGenerationHelper {

    private static final String DOMAIN_PREFIX = "http://localhost:8080/";

    public static ResponseEntity<Map<String, Object>> handleAuth(OAuth2User principal) {
        Map<String, Object> response = new HashMap<>();
        if (principal == null) {
            response.put("error", "unauthorised request");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(response);
        }
        String userId = principal.getAttribute("login");
        if (!StringUtils.hasText(userId)) {
            response.put("error", "invalid credentials received");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(response);
        }
        return null;
    }

    public static ResponseEntity<Map<String, Object>> userNotFoundError() {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "user not found for the given id");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

    public static ResponseEntity<Map<String, Object>> generateUrlsReport(List<Url> urlsOfUser) {
        Map<String, Object> response = new HashMap<>();
        List<UrlResponse> urlResponse = urlsOfUser.stream()
                .map(Url::getUrlResponse)
                .collect(Collectors.toList());
        response.put("urls", urlResponse);
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<Map<String, Object>> exceptionResponse() {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "ran into an issue while processing the request, please contact system administrator");
        return ResponseEntity.internalServerError().body(response);
    }

    public static ResponseEntity<Map<String, Object>> successResponse(boolean deactivateRequest) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Url " + (deactivateRequest ? "deactivated" : "activated"));
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<Map<String, Object>> getMapResponseEntity(Url url) {
        Map<String, Object> response = new HashMap<>();
        response.put("shortUrl", DOMAIN_PREFIX + url.getShortUrl());
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<Map<String, Object>> nonUpdateResponse(boolean deactivateRequest) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Url is already " + (deactivateRequest ? "deactivated" : "activated"));
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(response);
    }
}
