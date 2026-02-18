package com.example.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Component
public class LogViewer {
    private static final Logger logger = LoggerFactory.getLogger(LogViewer.class);
    private static final String LOG_FILE_PATH = System.getProperty("user.dir") + "/logs/student-crud.log";

    public String readLogFile() {
        try {
            return Files.lines(Paths.get(LOG_FILE_PATH))
                    .collect(Collectors.joining("\n"));
        } catch (Exception e) {
            logger.error("Error reading log file at {}: {}", LOG_FILE_PATH, e.getMessage());
            return "Unable to read logs: " + e.getMessage();
        }
    }
}