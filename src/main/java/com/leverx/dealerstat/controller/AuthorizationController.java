package com.leverx.dealerstat.controller;

import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.model.AuthenticationRequestDTO;
import com.leverx.dealerstat.model.RegistrationRequestDTO;
import com.leverx.dealerstat.model.ResetPasswordRequestDTO;
import com.leverx.dealerstat.service.AuthorizationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PutMapping("/confirm_registration")
    @ResponseStatus(HttpStatus.OK)
    public void confirmRegistration(@RequestParam("code") String code) {
        authorizationService.confirm(code);
    }

    @PostMapping("/forgot_password")
    @ResponseStatus(HttpStatus.OK)
    public void forgotPassword(@RequestBody String email) {
        authorizationService.forgotPassword(email);
    }

    @PostMapping("/reset")
    @ResponseStatus(HttpStatus.OK)
    public void resetPassword(@RequestBody ResetPasswordRequestDTO requestDTO) {
        authorizationService.resetPassword(requestDTO);
    }

    @GetMapping("/check_code")
    @ResponseStatus(HttpStatus.OK)
    public String checkCode(@RequestParam String code) {
        return authorizationService.checkCode(code);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> authenticate(@RequestBody AuthenticationRequestDTO requestDTO) {
        return authorizationService.authenticate(requestDTO);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody RegistrationRequestDTO requestDTO) {
        authorizationService.register(requestDTO);
    }
}
