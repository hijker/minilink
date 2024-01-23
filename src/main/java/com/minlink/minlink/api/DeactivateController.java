package com.minlink.minlink.api;

import com.minlink.minlink.helper.ResponseGenerationHelper;
import com.minlink.minlink.model.Url;
import com.minlink.minlink.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
public class DeactivateController {

    @Autowired
    UrlService urlService;

    @PostMapping(path = "/deactivate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> deactivateUrl(@AuthenticationPrincipal OAuth2User principal,
                                                             String shortUrl) {
        try {
            ResponseEntity<Map<String, Object>> validationResponse = ResponseGenerationHelper.handleAuth(principal);
            if (Objects.nonNull(validationResponse)) {
                return validationResponse;
            }
            Url url = urlService.findByShortUrl(shortUrl);
            if (Objects.isNull(url)) {
                return ResponseEntity.badRequest().build();
            }
            if (url.isDeactivated()) {
                return ResponseGenerationHelper.nonUpdateResponse(true);
            }
            urlService.deactivateUrl(url);
            return ResponseGenerationHelper.successResponse(true);
        } catch (Exception e) {
            return ResponseGenerationHelper.exceptionResponse();
        }
    }

    @PostMapping(path = "/activate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> activateUrl(@AuthenticationPrincipal OAuth2User principal,
                                                           String shortUrl) {
        try {
            ResponseEntity<Map<String, Object>> validationResponse = ResponseGenerationHelper.handleAuth(principal);
            if (Objects.nonNull(validationResponse)) {
                return validationResponse;
            }
            Url url = urlService.findByShortUrl(shortUrl);
            if (Objects.isNull(url)) {
                return ResponseEntity.badRequest().build();
            }
            if (!url.isDeactivated()) {
                return ResponseGenerationHelper.nonUpdateResponse(false);
            }
            urlService.activateUrl(url);
            return ResponseGenerationHelper.successResponse(false);
        } catch (Exception e) {
            return ResponseGenerationHelper.exceptionResponse();
        }
    }
}
