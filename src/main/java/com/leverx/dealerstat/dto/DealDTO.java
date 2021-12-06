package com.leverx.dealerstat.dto;

import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealDTO extends BaseDTO{

    private Date date;
    private Long fromId;
    private Long toId;
    private Long objectId;
}
