package com.leverx.dealerstat.security;

import com.leverx.dealerstat.model.User;
import com.leverx.dealerstat.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserFactory {

    private final UsersService usersService;

    @Autowired
    public AuthenticatedUserFactory(UsersService usersService) {
        this.usersService = usersService;
    }

    public User currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        return usersService.findByEmail(email);
    }
}
