package com.leverx.dealerstat.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AuthenticationRequestDTO {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 5, max = 15)
    private String password;
}
