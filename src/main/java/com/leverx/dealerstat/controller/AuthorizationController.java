package com.leverx.dealerstat.controller;

import com.leverx.dealerstat.converter.UsersConverter;
import com.leverx.dealerstat.dto.AuthenticationRequestDTO;
import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.exception.AlreadyExistsException;
import com.leverx.dealerstat.exception.NotFoundException;
import com.leverx.dealerstat.model.User;
import com.leverx.dealerstat.security.JwtTokenProvider;
import com.leverx.dealerstat.service.ConfirmationsService;
import com.leverx.dealerstat.service.MailSenderService;
import com.leverx.dealerstat.service.UsersService;
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
    private final UsersService usersService;
    private final UsersConverter converter;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final ConfirmationsService confirmationsService;

    @Autowired
    public AuthorizationController(MailSenderService senderService,
                                   UsersService usersService,
                                   ConfirmationsService confirmationsService,
                                   UsersConverter converter, AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.senderService = senderService;
        this.usersService = usersService;
        this.confirmationsService = confirmationsService;
        this.converter = converter;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }


    @GetMapping("/confirm")
    public ResponseEntity<UserDTO> confirmRegistration(@RequestParam("code") String code) {
        User user = confirmationsService.findUserByCode(code);
        if (user == null || user.isConfirmed()) {
            return ResponseEntity.notFound().build();
        }
        usersService.confirm(user);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/forgot_password")
    public ResponseEntity<?> recoverPassword(@RequestParam("email") String email) {
        User user = usersService.findByEmail(email);
      //  confirmationsService.save(user);
        senderService.sendMessageToRecoverPassword(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset")
    public ResponseEntity<?> confirmRecovering(@RequestParam("code") String code,
                                               @RequestParam("password") String password) {
        User user = confirmationsService.findUserByCode(code);
        if (user == null || !user.isConfirmed()) {
            return new ResponseEntity<>("User is already confirmed", HttpStatus.CONFLICT);
        }
        usersService.recoverPassword(user, password);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check_code")
    public ResponseEntity<String> checkCode(@RequestParam String code) {
        return ResponseEntity.ok(confirmationsService.checkCode(code));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO requestDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    requestDTO.getEmail(), requestDTO.getPassword()));

            User user = usersService.findByEmail(requestDTO.getEmail());
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
            userEntity = usersService.save(userEntity);
            senderService.sendVerificationCode(userEntity);
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(converter.convertToDTO(userEntity));
    }
}
