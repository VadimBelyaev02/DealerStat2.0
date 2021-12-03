package com.leverx.dealerstat.service;

import com.leverx.dealerstat.exception.AlreadyExistsException;
import com.leverx.dealerstat.exception.NotFoundException;
import com.leverx.dealerstat.model.User;

import java.util.List;

public interface UsersService {
    void save(User user) throws AlreadyExistsException;

    List<User> findAll();

    User findById(Long id);

    void confirm(User user);

    User findByEmail(String email) throws NotFoundException;

    void recoverPassword(User user, String password);

    void becomeTrader(User user);
}

