package com.leverx.dealerstat.dto;

import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameObjectDTO extends BaseDTO {

    private String title;
    private String description;
    private Long authorId;
    private Long gameId;
    private BigDecimal price;

}
