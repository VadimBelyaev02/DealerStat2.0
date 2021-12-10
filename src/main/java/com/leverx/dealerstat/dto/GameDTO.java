package com.leverx.dealerstat.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameDTO {

    private Long id;

    @NotBlank
    private String name;
}
