package com.leverx.dealerstat.service;

import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.model.AuthenticationRequestDTO;
import com.leverx.dealerstat.model.RegistrationRequestDTO;
import com.leverx.dealerstat.model.ResetPasswordRequestDTO;

import java.util.Map;

public interface AuthorizationService {

    void confirm(String code);

    void register(RegistrationRequestDTO requestDTO);

    String checkCode(String code);

    void forgotPassword(String email);

    void resetPassword(ResetPasswordRequestDTO requestDTO);

    Map<String, String> authenticate(AuthenticationRequestDTO requestDTO);
}
