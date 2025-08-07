package com.example.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class MailConfigTester {
    @Autowired
    private JavaMailSender sender;

    @PostConstruct
    public void test() {
        System.out.println("✅ JavaMailSender is initialized: " + sender.getClass().getName());
    }
}
