package com.bodima.project_lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendStudentCredentials(String toEmail, String username, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Welcome to the LMS - Your Login Credentials");
        message.setText("Hello,\n\nYou've been registered to the SHILPA LMS.\n\nUsername: " + username + "\nPassword: " + password + "\n\nPlease change your password after logging in.");

        mailSender.send(message);
    }
}
