package com.minlink.minlink.api;

import com.minlink.minlink.helper.ResponseGenerationHelper;
import com.minlink.minlink.model.Url;
import com.minlink.minlink.service.UrlService;
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

import static com.minlink.minlink.constants.Constants.ACTIVATE_ENDPOINT;
import static com.minlink.minlink.constants.Constants.ACTIVATE_THE_GIVEN_SHORT_URL;
import static com.minlink.minlink.constants.Constants.APPLICATION_JSON;
import static com.minlink.minlink.constants.Constants.APPLICATION_RAN_INTO_ERROR_WHILE_PROCESSING_REQUEST;
import static com.minlink.minlink.constants.Constants.DEACTIVATE_ENDPOINT;
import static com.minlink.minlink.constants.Constants.DEACTIVATE_THE_GIVEN_SHORT_URL;
import static com.minlink.minlink.constants.Constants.ERROR_DURING_AUTHENTICATION;
import static com.minlink.minlink.constants.Constants.ERROR_RAN_INTO_AN_ISSUE;
import static com.minlink.minlink.constants.Constants.ERROR_UNAUTHORISED_REQUEST;
import static com.minlink.minlink.constants.Constants.GIVEN_SHORT_URL_IS_ALREADY_DEACTIVATED;
import static com.minlink.minlink.constants.Constants.GIVEN_SHORT_URL_IS_DEACTIVATED;
import static com.minlink.minlink.constants.Constants.GIVEN_SHORT_URL_IS_NOT_FOUND;
import static com.minlink.minlink.constants.Constants.MESSAGE_SHORT_URL_NOT_FOUND;
import static com.minlink.minlink.constants.Constants.MESSAGE_URL_ACTIVATED;
import static com.minlink.minlink.constants.Constants.MESSAGE_URL_DEACTIVATED;
import static com.minlink.minlink.constants.Constants.MESSAGE_URL_IS_ALREADY_ACTIVATED;
import static com.minlink.minlink.constants.Constants.MESSAGE_URL_IS_ALREADY_DEACTIVATED;
import static com.minlink.minlink.constants.Constants.STATUS_200;
import static com.minlink.minlink.constants.Constants.STATUS_304;
import static com.minlink.minlink.constants.Constants.STATUS_400;
import static com.minlink.minlink.constants.Constants.STATUS_401;
import static com.minlink.minlink.constants.Constants.STATUS_500;

@RestController
public class DeactivateController {

    @Autowired
    UrlService urlService;

    @Operation(summary = DEACTIVATE_THE_GIVEN_SHORT_URL)
    @ApiResponses({
            @ApiResponse(responseCode = STATUS_200, description = GIVEN_SHORT_URL_IS_DEACTIVATED,
                    content = {@Content(examples = @ExampleObject(value = MESSAGE_URL_DEACTIVATED), mediaType = APPLICATION_JSON)}),
            @ApiResponse(responseCode = STATUS_401, description = ERROR_DURING_AUTHENTICATION,
                    content = {@Content(examples = @ExampleObject(value = ERROR_UNAUTHORISED_REQUEST), mediaType = APPLICATION_JSON)}),
            @ApiResponse(responseCode = STATUS_400, description = GIVEN_SHORT_URL_IS_NOT_FOUND,
                    content = {@Content(examples = @ExampleObject(value = MESSAGE_SHORT_URL_NOT_FOUND), mediaType = APPLICATION_JSON)}),
            @ApiResponse(responseCode = STATUS_304, description = GIVEN_SHORT_URL_IS_ALREADY_DEACTIVATED,
                    content = {@Content(examples = @ExampleObject(value = MESSAGE_URL_IS_ALREADY_DEACTIVATED), mediaType = APPLICATION_JSON)}),
            @ApiResponse(responseCode = STATUS_500, description = APPLICATION_RAN_INTO_ERROR_WHILE_PROCESSING_REQUEST,
                    content = {@Content(examples = @ExampleObject(value = ERROR_RAN_INTO_AN_ISSUE), mediaType = APPLICATION_JSON)})
    })
    @PostMapping(path = DEACTIVATE_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> deactivateUrl(@AuthenticationPrincipal OAuth2User principal,
                                                             String shortUrl) {
        try {
            ResponseEntity<Map<String, Object>> validationResponse = ResponseGenerationHelper.handleAuth(principal);
            if (Objects.nonNull(validationResponse)) {
                return validationResponse;
            }
            Url url = urlService.findByShortUrl(shortUrl);
            if (Objects.isNull(url)) {
                return ResponseGenerationHelper.shortUrlNotFoundResponse();
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

    @Operation(summary = ACTIVATE_THE_GIVEN_SHORT_URL)
    @ApiResponses({
            @ApiResponse(responseCode = STATUS_200, description = GIVEN_SHORT_URL_IS_DEACTIVATED,
                    content = {@Content(examples = @ExampleObject(value = MESSAGE_URL_ACTIVATED), mediaType = APPLICATION_JSON)}),
            @ApiResponse(responseCode = STATUS_401, description = ERROR_DURING_AUTHENTICATION,
                    content = {@Content(examples = @ExampleObject(value = ERROR_UNAUTHORISED_REQUEST), mediaType = APPLICATION_JSON)}),
            @ApiResponse(responseCode = STATUS_400, description = GIVEN_SHORT_URL_IS_NOT_FOUND,
                    content = {@Content(examples = @ExampleObject(value = MESSAGE_SHORT_URL_NOT_FOUND), mediaType = APPLICATION_JSON)}),
            @ApiResponse(responseCode = STATUS_304, description = GIVEN_SHORT_URL_IS_ALREADY_DEACTIVATED,
                    content = {@Content(examples = @ExampleObject(value = MESSAGE_URL_IS_ALREADY_ACTIVATED), mediaType = APPLICATION_JSON)}),
            @ApiResponse(responseCode = STATUS_500, description = APPLICATION_RAN_INTO_ERROR_WHILE_PROCESSING_REQUEST,
                    content = {@Content(examples = @ExampleObject(value = ERROR_RAN_INTO_AN_ISSUE), mediaType = APPLICATION_JSON)})
    })
    @PostMapping(path = ACTIVATE_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> activateUrl(@AuthenticationPrincipal OAuth2User principal,
                                                           String shortUrl) {
        try {
            ResponseEntity<Map<String, Object>> validationResponse = ResponseGenerationHelper.handleAuth(principal);
            if (Objects.nonNull(validationResponse)) {
                return validationResponse;
            }
            Url url = urlService.findByShortUrl(shortUrl);
            if (Objects.isNull(url)) {
                return ResponseGenerationHelper.shortUrlNotFoundResponse();
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
