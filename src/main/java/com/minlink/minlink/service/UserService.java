package com.minlink.minlink.service;

import com.minlink.minlink.model.User;
import com.minlink.minlink.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User createUserIfNotExist(String userId, String authProvider) {
        User user = userRepository.findByUserIdAndAuthProvider(userId, authProvider);
        return Objects.requireNonNullElseGet(user, () -> userRepository.save(new User(userId, authProvider)));
    }

    public User findUserByIdAndAuthProvider(String userId, String authProvider) {
        return userRepository.findByUserIdAndAuthProvider(userId, authProvider);
    }
}
