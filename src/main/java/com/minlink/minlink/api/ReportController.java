package com.minlink.minlink.api;

import com.minlink.minlink.helper.ResponseGenerationHelper;
import com.minlink.minlink.model.Url;
import com.minlink.minlink.model.User;
import com.minlink.minlink.service.UrlService;
import com.minlink.minlink.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class ReportController {

    @Autowired
    UrlService urlService;

    @Autowired
    UserService userService;

    @GetMapping(path = "/report", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getReport(@AuthenticationPrincipal OAuth2User principal) {
        try {
            ResponseEntity<Map<String, Object>> validationResponse = ResponseGenerationHelper.handleAuth(principal);
            if (Objects.nonNull(validationResponse)) {
                return validationResponse;
            }
            String userId = principal.getAttribute("login");
            User user = userService.findUserByIdAndAuthProvider(userId, "github");
            if (Objects.isNull(user)) {
                return ResponseGenerationHelper.userNotFoundError();
            }
            List<Url> urlsOfUser = urlService.findUrlsByUser(user.getuserId() + ":" + user.getauthProvider());
            return ResponseGenerationHelper.generateUrlsReport(urlsOfUser);
        } catch (Exception e) {
            return ResponseGenerationHelper.exceptionResponse();
        }
    }

}
