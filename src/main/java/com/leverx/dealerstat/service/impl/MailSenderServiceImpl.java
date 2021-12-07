package com.leverx.dealerstat.service.impl;

import com.leverx.dealerstat.exception.MessageSendingException;
import com.leverx.dealerstat.entity.User;
import com.leverx.dealerstat.service.MailSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailSenderServiceImpl implements MailSenderService {

    private final String fromEmail = "sendermail83@gmail.com";
    private final String password = "ecqxfgeuetrlcsxi";

    public void sendMessage(String subject, String email, String code) {
        String siteURL = "http://localhost:8080";
        String URL = siteURL + "/auth/confirm?code=" + code;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", 587);
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(new InternetAddress(fromEmail));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(code);
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            throw new MessageSendingException("There was a exception during sending the message", e);
        }
    }
}
