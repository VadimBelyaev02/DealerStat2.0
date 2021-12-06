package com.leverx.dealerstat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameObjectDTO {

    private String title;
    private String description;
    private Long authorId;
    private Long gameId;
    private BigDecimal price;

}
