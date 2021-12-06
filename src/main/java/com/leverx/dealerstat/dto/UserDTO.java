package com.leverx.dealerstat.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leverx.dealerstat.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String firstName;
    private String lastName;
  //  @JsonIgnore
    private String password;
    private String email;
}
