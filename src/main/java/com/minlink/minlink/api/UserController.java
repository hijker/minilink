package com.minlink.minlink.api;

import com.minlink.minlink.model.User;
import com.minlink.minlink.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController()
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        String userId = principal.getAttribute("login");
        userService.createUserIfNotExist(userId, "github");
        return Collections.singletonMap("name", userId);
    }

}