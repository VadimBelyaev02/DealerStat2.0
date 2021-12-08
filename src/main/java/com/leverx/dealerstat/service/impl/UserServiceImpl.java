package com.leverx.dealerstat.service.impl;

import com.leverx.dealerstat.dto.converter.UserConverter;
import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.exception.NotFoundException;
import com.leverx.dealerstat.entity.User;
import com.leverx.dealerstat.repository.ConfirmationRepository;
import com.leverx.dealerstat.repository.UserRepository;
import com.leverx.dealerstat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

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

    @Override
    @Transactional
    public UserDTO update(UserDTO userDTO) {
        if (!userRepository.existsById(userDTO.getId())) {
            throw new NotFoundException("User is not found");
        }
        return userConverter.convertToDTO(userRepository.save(userConverter.convertToModel(userDTO)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User is not found");
        }
        userRepository.deleteById(id);
    }
}
