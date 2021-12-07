package com.leverx.dealerstat.model;

import lombok.Data;

@Data
public class ResetPasswordRequestDTO {

    private String code;
    private String email;
    private String password;
}
