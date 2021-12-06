package com.leverx.dealerstat.security;

import com.leverx.dealerstat.converter.UsersConverter;
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
    private final UsersConverter usersConverter;

    @Autowired
    public AuthenticatedUserFactory(UserService userService, UsersConverter usersConverter) {
        this.userService = userService;
        this.usersConverter = usersConverter;
    }

    public UserDTO currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        return usersConverter.convertToDTO(userService.findByEmail(email));
    }
}
