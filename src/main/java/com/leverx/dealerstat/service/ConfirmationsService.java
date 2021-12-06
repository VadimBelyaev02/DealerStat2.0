package com.leverx.dealerstat.service;

import com.leverx.dealerstat.model.User;

public interface ConfirmationsService {


    User findUserByCode(String code);

    String checkCode(String code);
}
