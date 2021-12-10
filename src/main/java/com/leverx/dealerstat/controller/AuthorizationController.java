package com.leverx.dealerstat.controller;

import com.leverx.dealerstat.exception.NotValidException;
import com.leverx.dealerstat.model.AuthenticationRequestDTO;
import com.leverx.dealerstat.model.RegistrationRequestDTO;
import com.leverx.dealerstat.model.ResetPasswordRequestDTO;
import com.leverx.dealerstat.service.AuthorizationService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
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
    public void resetPassword(@RequestBody @Valid ResetPasswordRequestDTO requestDTO, BindingResult result) {
        authorizationService.resetPassword(requestDTO);
    }

    @GetMapping("/check_code")
    @ResponseStatus(HttpStatus.OK)
    public String checkCode(@RequestParam String code) {
        return authorizationService.checkCode(code);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> authenticate(@RequestBody @Valid AuthenticationRequestDTO requestDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new NotValidException(result.getAllErrors().toString());
        }
        return authorizationService.authenticate(requestDTO);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid RegistrationRequestDTO requestDTO, BindingResult result) {
        if (result.hasErrors()) {
            result.getFieldError();
            throw new NotValidException(result.getAllErrors().toString());
        }
        authorizationService.register(requestDTO);
    }
}
