package com.leverx.dealerstat.controller;

import com.leverx.dealerstat.converter.UsersConverter;
import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UsersController {

    private final UsersService usersService;
    private final UsersConverter converter;

    @Autowired
    public UsersController(@Qualifier("userServiceImpl") UsersService usersService,
                           UsersConverter converter) {
        this.usersService = usersService;
        this.converter = converter;
    }


    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOS = usersService.findAll().stream()
                .map(converter::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(userDTOS);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id) {
        UserDTO userDTO = converter.convertToDTO(usersService
                .findById(id));
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/become_trader")
    public ResponseEntity<UserDTO> becomeTrader() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        usersService.becomeTrader(usersService.findByEmail(email));
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }


}
