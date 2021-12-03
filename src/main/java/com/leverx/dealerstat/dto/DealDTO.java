package com.leverx.dealerstat.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class DealDTO {

    private Date date;
    private Long fromId;
    private Long toId;
}
