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
    private UserDTO author;
    private GameDTO game;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private BigDecimal price;

}
