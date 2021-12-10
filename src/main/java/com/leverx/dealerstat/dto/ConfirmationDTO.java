package com.leverx.dealerstat.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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

    private Long id;

    @NotBlank
    private String code;

    @NotBlank
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDate expirationTime;

    @NotBlank
    private Long userId;
}
