package com.leverx.dealerstat.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.leverx.dealerstat.entity.enums.Role;
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
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDate createdAt;

    @NotBlank
    private Role role;

    @NotBlank
    private boolean confirmed;
}
