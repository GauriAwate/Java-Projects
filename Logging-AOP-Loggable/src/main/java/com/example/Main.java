package com.example;

import com.example.config.AppConfig;
import com.example.exception.CustomException;
import com.example.model.User;
import com.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Starting application");

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = context.getBean(UserService.class);

        try {
            // Successful case
            User user = userService.processUser("123", "John Doe");
            logger.info("Created user: {} with name: {}", user.getId(), user.getName());

            // Error case
            userService.processUser(null, "Jane Doe");
        } catch (CustomException e) {
            logger.error("Application error occurred", e);
        }

        logger.info("Application shutdown");
    }
}