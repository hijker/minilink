package com.minlink.minlink.api;

import com.minlink.minlink.helper.ResponseGenerationHelper;
import com.minlink.minlink.helper.UrlGenerationHelper;
import com.minlink.minlink.model.Url;
import com.minlink.minlink.model.User;
import com.minlink.minlink.service.UrlService;
import com.minlink.minlink.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

import static com.minlink.minlink.constants.Constants.APPLICATION_JSON;
import static com.minlink.minlink.constants.Constants.APPLICATION_RAN_INTO_ERROR_WHILE_PROCESSING_REQUEST;
import static com.minlink.minlink.constants.Constants.CREATE_A_NEW_SHORT_URL;
import static com.minlink.minlink.constants.Constants.CREATE_ENDPOINT;
import static com.minlink.minlink.constants.Constants.ERROR_DURING_AUTHENTICATION;
import static com.minlink.minlink.constants.Constants.ERROR_RAN_INTO_AN_ISSUE;
import static com.minlink.minlink.constants.Constants.ERROR_UNAUTHORISED_REQUEST;
import static com.minlink.minlink.constants.Constants.GITHUB;
import static com.minlink.minlink.constants.Constants.LOGIN;
import static com.minlink.minlink.constants.Constants.SAMPLE_CREATE_RESPONSE;
import static com.minlink.minlink.constants.Constants.STATUS_200;
import static com.minlink.minlink.constants.Constants.STATUS_401;
import static com.minlink.minlink.constants.Constants.STATUS_500;
import static com.minlink.minlink.constants.Constants.THE_SHORT_URL_CREATED;
import static com.minlink.minlink.constants.Constants.USERID_CONCATENATION;

@RestController
public class MainController {

    @Autowired
    UserService userService;

    @Autowired
    UrlService urlService;

    @Operation(summary = CREATE_A_NEW_SHORT_URL)
    @ApiResponses({
            @ApiResponse(responseCode = STATUS_200, description = THE_SHORT_URL_CREATED,
                    content = {@Content(examples = @ExampleObject(value = SAMPLE_CREATE_RESPONSE), mediaType = APPLICATION_JSON)}),
            @ApiResponse(responseCode = STATUS_401, description = ERROR_DURING_AUTHENTICATION,
                    content = {@Content(examples = @ExampleObject(value = ERROR_UNAUTHORISED_REQUEST), mediaType = APPLICATION_JSON)}),
            @ApiResponse(responseCode = STATUS_500, description = APPLICATION_RAN_INTO_ERROR_WHILE_PROCESSING_REQUEST,
                    content = {@Content(examples = @ExampleObject(value = ERROR_RAN_INTO_AN_ISSUE), mediaType = APPLICATION_JSON)})
    })
    @PostMapping(path = CREATE_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> createShortUrl(@AuthenticationPrincipal OAuth2User principal,
                                                              String originalUrl) {
        try {
            ResponseEntity<Map<String, Object>> validationResponse = ResponseGenerationHelper.handleAuth(principal);
            if (Objects.nonNull(validationResponse)) {
                return validationResponse;
            }
            String userId = principal.getAttribute(LOGIN);
            User user = userService.createUserIfNotExist(userId, GITHUB);
            String prefix = UrlGenerationHelper.generatePrefix();
            Url url = urlService.createUrl(user.getuserId() + USERID_CONCATENATION + user.getauthProvider(), originalUrl, prefix);
            return ResponseGenerationHelper.getMapResponseEntity(url);
        } catch (Exception e) {
            return ResponseGenerationHelper.exceptionResponse();
        }
    }
}
