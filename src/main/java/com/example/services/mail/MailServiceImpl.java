package com.example.services.mail;

import com.example.domain.PlaceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Dmitrij on 20.10.2015.
 */


@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;


    @Override
    public void confirmEmailMessage(PlaceUser user, HttpServletRequest request) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setSubject("Please confirm your emali");
            helper.setFrom("MyPlaceToGo");
            helper.setTo(user.getUserEmail());
            helper.setText("Please go with the following link, to confirm your email " +
                            "http://localhost:8080/"+"/confirm/"+user.getToken().getToken() +
                            " Else ,just ignore the email",
                    true);
            javaMailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
