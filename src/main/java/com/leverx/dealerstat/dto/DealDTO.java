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
    private Long fromId;
    private Long toId;
    private Long objectId;
}
