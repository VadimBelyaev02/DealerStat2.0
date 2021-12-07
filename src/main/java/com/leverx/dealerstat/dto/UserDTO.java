package com.leverx.dealerstat.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leverx.dealerstat.entity.Role;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String firstName;
    private String lastName;
    @JsonIgnore
    private String password;
    private String email;
    private LocalDate createdAt;
    private Role role;
    private boolean confirmed;
}
