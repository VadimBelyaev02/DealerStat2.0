package com.leverx.dealerstat.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationDTO {

    @NotBlank
    private Long id;

    @NotBlank
    private String code;

    @NotBlank
    private LocalDate expirationTime;

    @NotBlank
    private Long userId;
}
