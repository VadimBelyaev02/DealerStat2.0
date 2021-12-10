package com.leverx.dealerstat.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameObjectDTO {

    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private Long authorId;

    @NotNull
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDate createdAt;

    @NotNull
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDate updatedAt;

    @NotNull
    private BigDecimal price;
}
