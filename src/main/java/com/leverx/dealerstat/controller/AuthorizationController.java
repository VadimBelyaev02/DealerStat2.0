package com.leverx.dealerstat.controller;

import com.leverx.dealerstat.converter.UsersConverter;
import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.exception.AlreadyExistsException;
import com.leverx.dealerstat.model.User;
import com.leverx.dealerstat.service.ConfirmationsService;
import com.leverx.dealerstat.service.MailSenderService;
import com.leverx.dealerstat.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    private final MailSenderService senderService;
    private final UsersService usersService;
    private final UsersConverter converter;
    private final ConfirmationsService confirmationsService;

    @Autowired
    public AuthorizationController(MailSenderService senderService,
                                   @Qualifier("userServiceImpl") UsersService usersService,
                                   ConfirmationsService confirmationsService,
                                   UsersConverter converter) {
        this.senderService = senderService;
        this.usersService = usersService;
        this.confirmationsService = confirmationsService;
        this.converter = converter;
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

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO user) {
        User userEntity = converter.convertToModel(user);
        try {
            usersService.save(userEntity);
            confirmationsService.save(userEntity);
            senderService.sendVerificationCode(userEntity);
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<?> recoverPassword(@RequestParam("email") String email) {
        User user = usersService.findByEmail(email);
        confirmationsService.save(user);
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
}
