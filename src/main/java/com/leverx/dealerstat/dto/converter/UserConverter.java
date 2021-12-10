package com.leverx.dealerstat.dto.converter;

import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.entity.enums.Role;
import com.leverx.dealerstat.entity.User;
import com.leverx.dealerstat.repository.ConfirmationRepository;
import com.leverx.dealerstat.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserConverter {

    private final UserRepository userRepository;
    private final ConfirmationRepository confirmationRepository;

    public UserConverter(UserRepository userRepository, ConfirmationRepository confirmationRepository) {
        this.userRepository = userRepository;
        this.confirmationRepository = confirmationRepository;
    }

    public UserDTO convertToDTO(final User user) {
        final Long id = user.getId();
        final String firstName = user.getFirstName();
        final String lastName = user.getFirstName();
        final String password = user.getPassword();
        final LocalDate createdAt = user.getCreatingDate();
        final Role role = user.getRole();
        final boolean confirmed = user.isConfirmed();
        final String email = user.getEmail();
        return UserDTO.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .email(email)
                .createdAt(createdAt)
                .role(role)
                .confirmed(confirmed)
                .build();
    }

    public User convertToModel(final UserDTO userDTO) {
        final Long id = userDTO.getId();
        final String firstName = userDTO.getFirstName();
        final String lastName = userDTO.getLastName();
        final String password = userDTO.getPassword();
        final String email = userDTO.getEmail();
        final LocalDate createdAt = userDTO.getCreatedAt();
        final Role role = userDTO.getRole();
        final boolean confirmed = userDTO.isConfirmed();
        return User.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .email(email)
                .creatingDate(createdAt)
                .role(role)
                .confirmed(confirmed)
                .build();
    }
}
