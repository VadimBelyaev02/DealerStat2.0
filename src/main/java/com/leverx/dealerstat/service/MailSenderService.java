package com.leverx.dealerstat.service;

import com.leverx.dealerstat.model.User;

public interface MailSenderService {

    void sendVerificationCode(User user);

    void sendMessageToRecoverPassword(User user);

}
