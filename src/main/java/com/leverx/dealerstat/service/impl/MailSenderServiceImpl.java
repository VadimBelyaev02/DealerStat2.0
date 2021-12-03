package com.leverx.dealerstat.service.impl;

import com.leverx.dealerstat.exception.MessageSendingException;
import com.leverx.dealerstat.model.User;
import com.leverx.dealerstat.service.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class MailSenderServiceImpl implements MailSenderService {

    @Value("${spring.mail.username}")
    private String fromAddress;
    private final JavaMailSender mailSender;
    private final MimeMessage message;
    private final MimeMessageHelper helper;
    private final String siteURL = "http://localhost:8080";
    private final String senderName = "DealerStat";
    private String subject;
    private String fullName;
    private String URL;
    private String toAddress;
    private String content;

    @Autowired
    public MailSenderServiceImpl(JavaMailSender sender) {
        this.mailSender = sender;
        this.message = mailSender.createMimeMessage();
        this.helper = new MimeMessageHelper(message);
    }

    @Override
    public void sendVerificationCode(User user)  {
        subject = "Please verify your registration";
        fullName = user.getFirstName() + " " + user.getLastName();
        toAddress = user.getEmail();
        URL = siteURL + "/auth/confirm?code=" + user.getConfirmation().getCode();

        content = "Dear " + fullName + ",<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<a href=\"" + URL + "\">CONFIRM</a><br>"
                + "Thank you,<br>"
                + senderName;
        sendMessage();
    }

    @Override
    public void sendMessageToRecoverPassword(User user) {
        subject = "Password recovery";
        fullName = user.getFirstName() + " " + user.getLastName();
        toAddress = user.getEmail();
        String code = user.getConfirmation().getCode();

        content = "Dear " + fullName + ",<br>"
                + "Here is your code to password recovering code:<br>"
                + code + "<br>"
                + "Thank you,<br>"
                + senderName;
        sendMessage();
    }

    private void sendMessage() {
        try {
            helper.setFrom(fromAddress, senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
        } catch (UnsupportedEncodingException | MessagingException e) {
            throw new MessageSendingException("Error sending the message");
        }
    }

}
