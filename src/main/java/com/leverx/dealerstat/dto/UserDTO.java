package com.leverx.dealerstat.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

    private String firstName;
    private String lastName;
    private String password;
    private String email;

}
