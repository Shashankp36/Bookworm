package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mail.MailRequest;
import com.example.services.EmailServiceImpl;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailServiceImpl service;

    @PostMapping("/send")
    public String sendEmail(@RequestBody MailRequest mailRequest) {
        try {
            service.sendInvoiceEmail(mailRequest.getTo(), mailRequest.getSubject(), mailRequest.getMessage());
            return "Email sent successfully!";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Failed to send email: " + e.getMessage();
        }
    }
}
