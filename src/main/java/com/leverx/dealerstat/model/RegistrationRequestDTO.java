package com.leverx.dealerstat.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegistrationRequestDTO {

    @NotBlank
    @Size(min = 5, max = 30)
    private String firstName;

    @NotBlank
    @Size(min = 5, max = 30)
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 5, max = 15)
    private String password;
}
