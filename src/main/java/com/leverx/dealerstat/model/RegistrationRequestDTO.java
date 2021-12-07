package com.leverx.dealerstat.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegistrationRequestDTO {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
