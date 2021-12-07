package com.leverx.dealerstat.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealDTO {

    @NotBlank
    private Long id;

    @NotBlank
    private LocalDate date;

    @NotBlank
    private Long fromId;

    @NotBlank
    private Long toId;

    @NotBlank
    private Long objectId;
}
