package com.leverx.dealerstat.service;

import com.leverx.dealerstat.model.User;

public interface ConfirmationsService {
    void save(User userEntity);

    User findUserByCode(String code);

    String checkCode(String code);
}
