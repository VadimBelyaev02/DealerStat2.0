package com.leverx.dealerstat.service;

import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.entity.User;

public interface ConfirmationService {

    UserDTO findUserByCode(String code);

    String checkCode(String code);
}
