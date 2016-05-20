package com.example.neo_services.mail;

import com.example.graph.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by Dmitrij on 20.10.2015.
 */


@Service
public class MailServiceImpl implements MailService {

    private JavaMailSender javaMailSender;
    public static final String domainName = "http://localhost:8080/";

    @Autowired
    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void confirmEmailMessage(Person user, String token) {
        String massageText = "Please go with the following link, to confirm your email " +
                "<a>" + domainName + "user/" + user.getUserName() + "/token/" + token + "</a>" +
                " Else ,just ignore this email";
        String subject = "Please confirm your email";
        sendEmail(massageText, user.getEmail(), subject);
    }

    private void sendEmail(String text, String email, String subject) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setSubject(subject);
            helper.setFrom("MyPlaceToGo");
            helper.setTo(email);
            helper.setText(text, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
