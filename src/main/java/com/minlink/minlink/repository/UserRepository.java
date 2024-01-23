package com.minlink.minlink.repository;

import com.minlink.minlink.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUserIdAndAuthProvider(String userId, String authProvider);
}
