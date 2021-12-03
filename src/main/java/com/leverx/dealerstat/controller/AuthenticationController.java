package com.leverx.dealerstat.controller;

import com.leverx.dealerstat.dto.AuthenticationRequestDTO;
import com.leverx.dealerstat.exception.NotFoundException;
import com.leverx.dealerstat.model.User;
import com.leverx.dealerstat.security.JwtTokenProvider;
import com.leverx.dealerstat.service.ConfirmationsService;
import com.leverx.dealerstat.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final ConfirmationsService confirmationsService;
    private final UsersService usersService;
    private final JwtTokenProvider tokenProvider;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    ConfirmationsService confirmationsService,
                                    @Qualifier("userServiceImpl") UsersService usersService,
                                    JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.confirmationsService = confirmationsService;
        this.usersService = usersService;
        this.tokenProvider = tokenProvider;
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


    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
