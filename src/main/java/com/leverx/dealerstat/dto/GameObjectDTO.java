package com.leverx.dealerstat.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameObjectDTO {

    @NotBlank
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private Long authorId;

    @NotBlank
    private Long gameId;

    @NotBlank
    private LocalDate createdAt;

    @NotBlank
    private LocalDate updatedAt;

    @NotBlank
    private BigDecimal price;
}
