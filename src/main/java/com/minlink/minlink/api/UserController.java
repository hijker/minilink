package com.minlink.minlink.api;

import com.minlink.minlink.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

import static com.minlink.minlink.constants.Constants.APPLICATION_JSON;
import static com.minlink.minlink.constants.Constants.END_POINT_TO_GET_USER_ID;
import static com.minlink.minlink.constants.Constants.GITHUB;
import static com.minlink.minlink.constants.Constants.INTERNAL_USAGE_USED_TO_GET_THE_CURRENT_USER_NAME;
import static com.minlink.minlink.constants.Constants.LOGIN;
import static com.minlink.minlink.constants.Constants.NAME_CONSTANT;
import static com.minlink.minlink.constants.Constants.NAME_JACK;
import static com.minlink.minlink.constants.Constants.STATUS_200;
import static com.minlink.minlink.constants.Constants.USER_ENDPOINT;

@RestController()
public class UserController {

    @Autowired
    UserService userService;

    @Operation(summary = INTERNAL_USAGE_USED_TO_GET_THE_CURRENT_USER_NAME)
    @ApiResponses({
            @ApiResponse(responseCode = STATUS_200, description = END_POINT_TO_GET_USER_ID,
                    content = {@Content(examples = @ExampleObject(value = NAME_JACK), mediaType = APPLICATION_JSON)}),
    })
    @GetMapping(path = USER_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        String userId = principal.getAttribute(LOGIN);
        userService.createUserIfNotExist(userId, GITHUB);
        return Collections.singletonMap(NAME_CONSTANT, userId);
    }

}