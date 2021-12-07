package com.leverx.dealerstat.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameDTO extends BaseDTO {

    private String name;
}
