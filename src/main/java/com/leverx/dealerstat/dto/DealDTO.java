package com.leverx.dealerstat.dto;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealDTO {

    private Long id;
    private Date date;
    private UserDTO from;
    private UserDTO to;
    private GameObjectDTO object;
}
