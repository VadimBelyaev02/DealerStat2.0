package com.leverx.dealerstat.service;

import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.exception.AlreadyExistsException;
import com.leverx.dealerstat.exception.NotFoundException;
import com.leverx.dealerstat.entity.User;
import com.leverx.dealerstat.model.RegistrationRequestDTO;

import java.util.List;

public interface UserService {

    UserDTO save(UserDTO user);

    List<UserDTO> findAll();

    UserDTO findById(Long id);

    UserDTO confirm(UserDTO user);

    UserDTO findByEmail(String email);

    void recoverPassword(UserDTO user, String password);

    UserDTO becomeTrader(UserDTO user);

    UserDTO update(UserDTO userDTO);

    UserDTO register(RegistrationRequestDTO requestDTO);
}

