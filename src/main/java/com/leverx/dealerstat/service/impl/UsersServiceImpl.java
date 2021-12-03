package com.leverx.dealerstat.service.impl;

import com.leverx.dealerstat.exception.AlreadyExistsException;
import com.leverx.dealerstat.exception.NotFoundException;
import com.leverx.dealerstat.model.Role;
import com.leverx.dealerstat.model.User;
import com.leverx.dealerstat.repository.ConfirmationsRepository;
import com.leverx.dealerstat.repository.UsersRepository;
import com.leverx.dealerstat.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("userServiceImpl")
public class UsersServiceImpl implements UsersService {

    private final UsersRepository repository;
    private final ConfirmationsRepository confirmationsRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UsersServiceImpl(UsersRepository repository,
                            ConfirmationsRepository confirmationsRepository,
                            PasswordEncoder encoder) {
        this.repository = repository;
        this.confirmationsRepository = confirmationsRepository;
        this.encoder = encoder;
    }


    @Override
    @Transactional
    public void save(User user) throws AlreadyExistsException {
        if (repository.existsByEmail(user.getEmail())) {
            throw new AlreadyExistsException("The user already exists");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setConfirmed(false);
        user.setRole(Role.USER);
        user.setCreatingDate(new Date());

        repository.save(user);
    }

    @Override
    @Transactional
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("User is not found");
        });
    }

    @Override
    @Transactional
    public void confirm(User user) {
        User userFromDB = repository.findById(user.getId()).orElseThrow(() -> {
            throw new NotFoundException("User is not found");
        });
        userFromDB.setConfirmed(true);
        repository.save(userFromDB);
        confirmationsRepository.deleteByUserId(user.getId());
    }

    @Override
    @Transactional
    public User findByEmail(String email) throws NotFoundException {
        return repository.findByEmail(email).orElseThrow(() -> {
            throw new UsernameNotFoundException("User doesn't exist");
        });
    }

    @Override
    @Transactional
    public void recoverPassword(User user, String password) {
        User userFromDB = repository.findById(user.getId()).orElseThrow(() -> {
            throw new NotFoundException("User doesn't exist");
        });
        userFromDB.setPassword(encoder.encode(password));
        repository.save(userFromDB);
        confirmationsRepository.deleteByUserId(user.getId());
    }

    @Override
    @Transactional
    public void becomeTrader(User user) {
        User userFromDB = repository.findById(user.getId()).orElseThrow(() -> {
            throw new NotFoundException("User doesn't exist");
        });;
        userFromDB.setRole(Role.TRADER);
        repository.save(userFromDB);
    }
}
