package com.leverx.dealerstat.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameObjectDTO {

    private Long id;
    private String title;
    private String description;
    private Long authorId;
    private Long gameId;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private BigDecimal price;

}
