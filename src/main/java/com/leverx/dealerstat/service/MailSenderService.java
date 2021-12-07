package com.leverx.dealerstat.service;

public interface MailSenderService {

    void sendMessage(String subject, String email, String code);

}
