package com.leverx.dealerstat.service.impl;

import com.leverx.dealerstat.converter.UserConverter;
import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.exception.AlreadyExistsException;
import com.leverx.dealerstat.exception.NotFoundException;
import com.leverx.dealerstat.entity.Confirmation;
import com.leverx.dealerstat.entity.Role;
import com.leverx.dealerstat.entity.User;
import com.leverx.dealerstat.model.RegistrationRequestDTO;
import com.leverx.dealerstat.repository.ConfirmationRepository;
import com.leverx.dealerstat.repository.UserRepository;
import com.leverx.dealerstat.service.MailSenderService;
import com.leverx.dealerstat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final ConfirmationRepository confirmationRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserConverter userConverter,
                           ConfirmationRepository confirmationRepository,
                           PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.confirmationRepository = confirmationRepository;
        this.encoder = encoder;
    }


//    @Override
//    @Transactional
//    public UserDTO save(UserDTO user) throws AlreadyExistsException {
//        if (userRepository.existsByEmail(user.getEmail())) {
//            throw new AlreadyExistsException("User is already exists");
//        }
//
//        user.setPassword(encoder.encode(user.getPassword()));
//        user.setConfirmed(false);
//        user.setRole(Role.USER);
//        user.setCreatingDate(new Date());
//
//        int oneDayInMilliseconds = 16040;
//        String code = UUID.randomUUID().toString();
//        Date expirationTime = new Date(new Date().getTime() + oneDayInMilliseconds);
//
//        Confirmation confirmation = new Confirmation();
//        confirmation.setCode(code);
//        confirmation.setExpirationTime(expirationTime);
//        confirmation.setUser(user);
//
//        user.setConfirmation(confirmation);
//        repository.save(user);
//        //   confirmationsRepository.save(confirmation);
//        return user;
//    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(userConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("User is not found");
        });
        return userConverter.convertToDTO(user);
    }



    @Override
    @Transactional(readOnly = true)
    public UserDTO findByEmail(String email) throws NotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            throw new UsernameNotFoundException("User doesn't exist");
        });
        return userConverter.convertToDTO(user);
    }

//    @Override
//    @Transactional
//    public UserDTO becomeTrader(UserDTO user) {
//        user.setRole(Role.TRADER);
//        return repository.save(user);
//    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        if (!userRepository.existsByEmail(userDTO.getEmail())) {
            throw new NotFoundException("User is not found");
        }
        return userConverter.convertToDTO(userRepository.save(userConverter.convertToModel(userDTO)));
    }


}
