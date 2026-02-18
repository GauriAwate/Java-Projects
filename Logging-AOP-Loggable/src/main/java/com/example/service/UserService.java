package com.example.service;

import com.example.annotation.Loggable;
import com.example.exception.CustomException;
import com.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Loggable
    public User processUser(String userId, String name) throws CustomException {
        if (userId == null || userId.trim().isEmpty()) {
            logger.warn("Invalid userId provided: {}", userId);
            throw new CustomException("User ID cannot be null or empty");
        }

        logger.debug("Creating user object for userId: {}", userId);
        User user = new User(userId, name);
        return user;
    }
}