package com.example.bookstoreweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication

public class BookStoreWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(BookStoreWebApplication.class, args);
	}
}
