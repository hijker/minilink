package com.minlink.minlink.api;

import com.minlink.minlink.helper.ResponseGenerationHelper;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.minlink.minlink.constants.Constants.APPLICATION_JSON;
import static com.minlink.minlink.constants.Constants.APPLICATION_RAN_INTO_ERROR_WHILE_PROCESSING_REQUEST;
import static com.minlink.minlink.constants.Constants.ERROR_DURING_AUTHENTICATION;
import static com.minlink.minlink.constants.Constants.ERROR_RAN_INTO_AN_ISSUE;
import static com.minlink.minlink.constants.Constants.ERROR_UNAUTHORISED_REQUEST;
import static com.minlink.minlink.constants.Constants.GET_THE_SHORT_URLS_OF_A_USER_AND_ITS_STATS;
import static com.minlink.minlink.constants.Constants.GITHUB;
import static com.minlink.minlink.constants.Constants.LOGIN;
import static com.minlink.minlink.constants.Constants.REPORT_ENDPOINT;
import static com.minlink.minlink.constants.Constants.STATUS_200;
import static com.minlink.minlink.constants.Constants.STATUS_401;
import static com.minlink.minlink.constants.Constants.STATUS_500;
import static com.minlink.minlink.constants.Constants.THE_URL_DATA_OF_THE_USER;
import static com.minlink.minlink.constants.Constants.REPORT_SAMPLE_RESPONSE;
import static com.minlink.minlink.constants.Constants.USERID_CONCATENATION;

@RestController
public class ReportController {

    @Autowired
    UrlService urlService;

    @Autowired
    UserService userService;

    @Operation(summary = GET_THE_SHORT_URLS_OF_A_USER_AND_ITS_STATS)
    @ApiResponses({
            @ApiResponse(responseCode = STATUS_200, description = THE_URL_DATA_OF_THE_USER,
                    content = {@Content(examples = @ExampleObject(value = REPORT_SAMPLE_RESPONSE), mediaType = APPLICATION_JSON)}),
            @ApiResponse(responseCode = STATUS_401, description = ERROR_DURING_AUTHENTICATION,
                    content = {@Content(examples = @ExampleObject(value = ERROR_UNAUTHORISED_REQUEST), mediaType = APPLICATION_JSON)}),
            @ApiResponse(responseCode = STATUS_500, description = APPLICATION_RAN_INTO_ERROR_WHILE_PROCESSING_REQUEST,
                    content = {@Content(examples = @ExampleObject(value = ERROR_RAN_INTO_AN_ISSUE), mediaType = APPLICATION_JSON)})
    })
    @GetMapping(path = REPORT_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getReport(@AuthenticationPrincipal OAuth2User principal) {
        try {
            ResponseEntity<Map<String, Object>> validationResponse = ResponseGenerationHelper.handleAuth(principal);
            if (Objects.nonNull(validationResponse)) {
                return validationResponse;
            }
            String userId = principal.getAttribute(LOGIN);
            User user = userService.findUserByIdAndAuthProvider(userId, GITHUB);
            if (Objects.isNull(user)) {
                return ResponseGenerationHelper.userNotFoundError();
            }
            List<Url> urlsOfUser = urlService.findUrlsByUser(user.getuserId() + USERID_CONCATENATION + user.getauthProvider());
            return ResponseGenerationHelper.generateUrlsReport(urlsOfUser);
        } catch (Exception e) {
            return ResponseGenerationHelper.exceptionResponse();
        }
    }

}
