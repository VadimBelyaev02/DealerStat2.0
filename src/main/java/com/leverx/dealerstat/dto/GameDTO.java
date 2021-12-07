package com.leverx.dealerstat.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameDTO {

    @NotBlank
    private Long id;

    @NotBlank
    private String name;
}
