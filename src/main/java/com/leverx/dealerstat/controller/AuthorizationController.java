package com.leverx.dealerstat.controller;

import com.leverx.dealerstat.converter.UsersConverter;
import com.leverx.dealerstat.model.AuthenticationRequestDTO;
import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.exception.AlreadyExistsException;
import com.leverx.dealerstat.exception.NotFoundException;
import com.leverx.dealerstat.entity.User;
import com.leverx.dealerstat.security.JwtTokenProvider;
import com.leverx.dealerstat.service.ConfirmationService;
import com.leverx.dealerstat.service.MailSenderService;
import com.leverx.dealerstat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    private final MailSenderService senderService;
    private final UserService userService;
    private final UsersConverter converter;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final ConfirmationService confirmationService;

    @Autowired
    public AuthorizationController(MailSenderService senderService,
                                   UserService userService,
                                   ConfirmationService confirmationService,
                                   UsersConverter converter, AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.senderService = senderService;
        this.userService = userService;
        this.confirmationService = confirmationService;
        this.converter = converter;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @GetMapping("/confirm")
    public ResponseEntity<UserDTO> confirmRegistration(@RequestParam("code") String code) {
        User user = confirmationService.findUserByCode(code);
        if (user == null || user.isConfirmed()) {
            return ResponseEntity.notFound().build();
        }
        userService.confirm(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<?> recoverPassword(@RequestParam("email") String email) {
        User user = userService.findByEmail(email);
      //  confirmationsService.save(user);
        senderService.sendMessageToRecoverPassword(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset")
    public ResponseEntity<?> confirmRecovering(@RequestParam("code") String code,
                                               @RequestParam("password") String password) {
        User user = confirmationService.findUserByCode(code);
        if (user == null || !user.isConfirmed()) {
            return new ResponseEntity<>("User is already confirmed", HttpStatus.CONFLICT);
        }
        userService.recoverPassword(user, password);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check_code")
    public ResponseEntity<String> checkCode(@RequestParam String code) {
        return ResponseEntity.ok(confirmationService.checkCode(code));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO requestDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    requestDTO.getEmail(), requestDTO.getPassword()));

            User user = userService.findByEmail(requestDTO.getEmail());
            if (!user.isConfirmed()) {
                throw new NotFoundException("User is not confirmed");
            }

            String token = tokenProvider.createToken(requestDTO.getEmail(), user.getRole().name());
            Map<String, String> response = new HashMap<>();
            response.put("email", requestDTO.getEmail());
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO user) {
        User userEntity = converter.convertToModel(user);
        try {
            userEntity = userService.save(userEntity);
            senderService.sendVerificationCode(userEntity);
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(converter.convertToDTO(userEntity));
    }
}
