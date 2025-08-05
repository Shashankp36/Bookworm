package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@ComponentScan(basePackages = {
	    "com.example.demo",
	    "com.example.service",
	    "com.example.repository",
	    "com.example.security",
	    "com.example.exce",
	    "com.example.model",
	    "com.example.configuration",
	    "com.example.dto",
	    "com.example.*",
	    "com.example.controller"
	})

@EntityScan(basePackages = "com.example.*")
@EnableJpaRepositories(basePackages = "com.example.*")
@EnableMethodSecurity(prePostEnabled = true)
public class BookwormApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookwormApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
