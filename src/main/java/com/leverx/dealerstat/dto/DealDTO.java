package com.leverx.dealerstat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealDTO {

    private Date date;
    private Long fromId;
    private Long toId;
    private Long objectId;
}
