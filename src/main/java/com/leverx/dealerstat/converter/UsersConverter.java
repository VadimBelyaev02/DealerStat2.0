package com.leverx.dealerstat.converter;

import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.model.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UsersConverter {

    public UserDTO convertToDTO(final User user) {
        final String firstName = user.getFirstName();
        final String lastName = user.getFirstName();
        final String password = user.getPassword();
        final String email = user.getEmail();
        final Date date = user.getCreatingDate();
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
