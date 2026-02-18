package com.example.util;

import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Component
public class LogViewer {
    private static final String LOG_FILE_PATH = "logs/product-crud.log";

    public String readLogFile() throws Exception {
        return Files.lines(Paths.get(LOG_FILE_PATH))
                .collect(Collectors.joining("\n"));
    }
}