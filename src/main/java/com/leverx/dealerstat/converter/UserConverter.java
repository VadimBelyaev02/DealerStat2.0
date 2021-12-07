package com.leverx.dealerstat.converter;

import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.entity.User;
import com.leverx.dealerstat.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    private final UserService userService;

    public UserConverter(UserService userService) {
        this.userService = userService;
    }

    public UserDTO convertToDTO(final User user) {
        final String firstName = user.getFirstName();
        final String lastName = user.getFirstName();
        final String password = user.getPassword();
        final String email = user.getEmail();
        return UserDTO.builder()
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .email(email)
                .build();
    }

    public User convertToModel(final UserDTO userDTO) {
        final String firstName = userDTO.getFirstName();
        final String lastName = userDTO.getLastName();
        final String password = userDTO.getPassword();
        final String email = userDTO.getEmail();
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .email(email)
                .build();
    }
}
