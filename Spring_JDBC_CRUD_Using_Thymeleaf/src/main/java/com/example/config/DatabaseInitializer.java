package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
@Component
public class DatabaseInitializer {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public DatabaseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @PostConstruct
    public void initialize() {
        String sql = """
 CREATE TABLE IF NOT EXISTS student (
 id INT PRIMARY KEY AUTO_INCREMENT,
 name VARCHAR(100) NOT NULL,
 email VARCHAR(255) NOT NULL UNIQUE
 )
 """;
        jdbcTemplate.execute(sql);
    }
}