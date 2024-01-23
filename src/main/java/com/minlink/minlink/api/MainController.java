package com.minlink.minlink.api;

import com.minlink.minlink.helper.ResponseGenerationHelper;
import com.minlink.minlink.helper.UrlGenerationHelper;
import com.minlink.minlink.model.Url;
import com.minlink.minlink.model.User;
import com.minlink.minlink.service.UrlService;
import com.minlink.minlink.service.UserService;
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
public class MainController {

    @Autowired
    UserService userService;

    @Autowired
    UrlService urlService;

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> createShortUrl(@AuthenticationPrincipal OAuth2User principal,
                                                              String originalUrl) {
        try {
            ResponseEntity<Map<String, Object>> validationResponse = ResponseGenerationHelper.handleAuth(principal);
            if (Objects.nonNull(validationResponse)) {
                return validationResponse;
            }
            String userId = principal.getAttribute("login");
            User user = userService.createUserIfNotExist(userId, "github");
            String prefix = UrlGenerationHelper.generatePrefix();
            Url url = urlService.createUrl(user.getuserId() + ":" + user.getauthProvider(), originalUrl, prefix);
            return ResponseGenerationHelper.getMapResponseEntity(url);
        } catch (Exception e) {
            return ResponseGenerationHelper.exceptionResponse();
        }
    }
}
