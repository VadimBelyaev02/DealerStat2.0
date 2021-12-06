package com.leverx.dealerstat.service.impl;

import com.leverx.dealerstat.exception.AlreadyExistsException;
import com.leverx.dealerstat.exception.NotFoundException;
import com.leverx.dealerstat.model.Confirmation;
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
import java.util.UUID;

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
    public User save(User user) throws AlreadyExistsException {
        if (repository.existsByEmail(user.getEmail())) {
            throw new AlreadyExistsException("User is already exists");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setConfirmed(false);
        user.setRole(Role.USER);
        user.setCreatingDate(new Date());

        int oneDayInMilliseconds = 16040;
        String code = UUID.randomUUID().toString();
        Date expirationTime = new Date(new Date().getTime() + oneDayInMilliseconds);

        Confirmation confirmation = new Confirmation();
        confirmation.setCode(code);
        confirmation.setExpirationTime(expirationTime);
        confirmation.setUser(user);

        user.setConfirmation(confirmation);
        repository.save(user);
     //   confirmationsRepository.save(confirmation);
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
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
    public User becomeTrader(User user) {
        user.setRole(Role.TRADER);
        return repository.save(user);
    }
}
