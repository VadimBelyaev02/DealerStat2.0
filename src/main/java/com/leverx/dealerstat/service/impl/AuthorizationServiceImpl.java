package com.leverx.dealerstat.service.impl;

import com.leverx.dealerstat.entity.Confirmation;
import com.leverx.dealerstat.entity.User;
import com.leverx.dealerstat.entity.enums.Role;
import com.leverx.dealerstat.exception.AccessDeniedException;
import com.leverx.dealerstat.exception.AlreadyExistsException;
import com.leverx.dealerstat.exception.NotFoundException;
import com.leverx.dealerstat.model.AuthenticationRequestDTO;
import com.leverx.dealerstat.model.RegistrationRequestDTO;
import com.leverx.dealerstat.model.ResetPasswordRequestDTO;
import com.leverx.dealerstat.repository.ConfirmationRepository;
import com.leverx.dealerstat.repository.UserRepository;
import com.leverx.dealerstat.security.jwt.JwtTokenProvider;
import com.leverx.dealerstat.service.AuthorizationService;
import com.leverx.dealerstat.service.MailSenderService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private final MailSenderService senderService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationRepository confirmationRepository;

    public AuthorizationServiceImpl(MailSenderService senderService,
                                    UserRepository userRepository,
                                    AuthenticationManager authenticationManager,
                                    JwtTokenProvider tokenProvider,
                                    PasswordEncoder passwordEncoder,
                                    ConfirmationRepository confirmationRepository) {
        this.senderService = senderService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.confirmationRepository = confirmationRepository;
    }

    @Override
    @Transactional
    public void confirm(String code) {
        Confirmation confirmation = confirmationRepository.findByCode(code).orElseThrow(() -> {
            throw new NotFoundException("Code is not found");
        });
        confirmation.getUser().setConfirmed(true);
        confirmationRepository.delete(confirmation);
    }

    @Override
    @Transactional
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            throw new NotFoundException("User is not found");
        });

        String code = UUID.randomUUID().toString();
        LocalDate expirationTime = LocalDate.now().plusDays(1);

        Confirmation confirmation = new Confirmation();
        confirmation.setCode(code);
        confirmation.setUser(user);
        confirmation.setExpirationTime(expirationTime);
        String subject = "Code to recover password";
        senderService.sendMessage(subject, email, code);
        confirmationRepository.save(confirmation);
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequestDTO requestDTO) {
        User user = userRepository.findByEmail(requestDTO.getEmail()).orElseThrow(() -> {
            throw new NotFoundException("User is not found");
        });
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
    }

    @Override
    @Transactional
    public Map<String, String> authenticate(AuthenticationRequestDTO requestDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    requestDTO.getEmail(), requestDTO.getPassword()));

            User user = userRepository.findByEmail(requestDTO.getEmail()).orElseThrow(() -> {
                throw new NotFoundException("User is not found");
            });
            if (!user.isConfirmed()) {
                throw new AccessDeniedException("User is not confirmed");
            }

            String token = tokenProvider.createToken(requestDTO.getEmail(), user.getRole().name());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return response;
        } catch (AuthenticationException e) {
            throw new AccessDeniedException("Invalid email or password", e);
        }

    }

    @Override
    @Transactional
    public void register(RegistrationRequestDTO requestDTO) {
        if (userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new AlreadyExistsException("User is already exists");
        }
        User user = new User();
        user.setConfirmed(false);
        user.setEmail(requestDTO.getEmail());
        user.setRole(Role.USER);
        user.setCreatingDate(LocalDate.now());
        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        String code = UUID.randomUUID().toString();
        LocalDate expirationTime = LocalDate.now().plusDays(1);
        Confirmation confirmation = new Confirmation();
        confirmation.setCode(code);
        confirmation.setUser(user);
        confirmation.setExpirationTime(expirationTime);
        user.setConfirmation(confirmation);
        String subject = "Please confirm your account";
        senderService.sendMessage(subject, requestDTO.getEmail(), code);
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public String checkCode(String code) {
        return confirmationRepository.findByCode(code).orElseThrow(() -> {
            throw new NotFoundException("Code is not found");
        }).getCode();
    }
}
