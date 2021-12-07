package com.leverx.dealerstat.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leverx.dealerstat.entity.Role;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotBlank
    private Long id;

    @NotBlank
    @Size(min = 5, max = 30)
    private String firstName;

    @NotBlank
    @Size(min = 5, max = 30)
    private String lastName;

    @JsonIgnore
    private String password;

    @Email
    private String email;

    @NotBlank
    private LocalDate createdAt;

    @NotBlank
    private Role role;

    @NotBlank
    private boolean confirmed;
}
