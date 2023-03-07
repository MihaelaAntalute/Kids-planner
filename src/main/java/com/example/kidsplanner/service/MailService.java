package com.example.kidsplanner.service;


import com.example.kidsplanner.model.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {
    private JavaMailSender emailSender;

    @Autowired
    public MailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendEmailWithAverage (String recipientMail, Period period) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("antalutemihaela32@gmail.com");
        helper.setTo(recipientMail);
        helper.setSubject("The average for your activities are: " + period.getAverageEvaluation());
        if (period.getAverageEvaluation() > 7) {
            helper.setText("Congratulations! You will got what you want from your wishlist!");
        } else {
            helper.setText("I'm sorry!Maybe next time you will got something from your wish list!");
        }
        emailSender.send(message);
    }

}
