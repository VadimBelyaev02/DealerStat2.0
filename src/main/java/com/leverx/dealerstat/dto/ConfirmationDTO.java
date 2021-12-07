package com.leverx.dealerstat.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationDTO {

    private Long id;
    private String code;
    private LocalDate expirationTime;
    private UserDTO user;
}
