package com.leverx.dealerstat.service;

import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.exception.AlreadyExistsException;
import com.leverx.dealerstat.exception.NotFoundException;
import com.leverx.dealerstat.entity.User;
import com.leverx.dealerstat.model.RegistrationRequestDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> findAll();

    UserDTO findById(Long id);

    UserDTO findByEmail(String email);

    UserDTO update(UserDTO userDTO);

    void delete(Long id);
}

