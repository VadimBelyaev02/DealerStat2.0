package com.leverx.dealerstat.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leverx.dealerstat.entity.Role;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends BaseDTO {

    private String firstName;
    private String lastName;
    @JsonIgnore
    private String password;
    private String email;
    private Role role;
}
