package com.example.pmswebportal.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.pmswebportal.dto.EmailDetail;

@Service
public class MailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    /**
     * Send Mail
     * 
     * @param EmailDetail
     * @return
     */
    public boolean sendSimpleMail(EmailDetail emailDetail) {
        // Creating a simple mail message
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        // Setting up necessary details
        mailMessage.setFrom(sender);
        mailMessage.setTo(emailDetail.getRecipient());
        mailMessage.setText(emailDetail.getMsgBody());
        mailMessage.setSubject(emailDetail.getSubject());

        // Sending the mail
        javaMailSender.send(mailMessage);
        return true;
    }

}
