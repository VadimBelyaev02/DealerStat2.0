package com.leverx.dealerstat.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationDTO extends BaseDTO {

    private String code;
    private LocalDate expirationTime;
}
