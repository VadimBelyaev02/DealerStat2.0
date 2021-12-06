package com.leverx.dealerstat.security;

import com.leverx.dealerstat.converter.UserConverter;
import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserFactory {

    private final UserService userService;

    @Autowired
    public AuthenticatedUserFactory(UserService userService) {
        this.userService = userService;
    }

    public UserDTO currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        return userService.findByEmail(email);
    }
}
