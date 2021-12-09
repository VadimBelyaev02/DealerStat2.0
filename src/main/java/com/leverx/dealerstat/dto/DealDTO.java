package com.leverx.dealerstat.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDate date;

    @NotBlank
    private Long fromId;

    @NotBlank
    private Long toId;

    @NotBlank
    private Long objectId;
}
