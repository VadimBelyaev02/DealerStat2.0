package com.leverx.dealerstat.service;

import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.exception.AlreadyExistsException;
import com.leverx.dealerstat.exception.NotFoundException;
import com.leverx.dealerstat.entity.User;
import com.leverx.dealerstat.model.RegistrationRequestDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> getAll();

    UserDTO getById(Long id);

    UserDTO getByEmail(String email);

    UserDTO update(UserDTO userDTO);

    void delete(Long id);
}

