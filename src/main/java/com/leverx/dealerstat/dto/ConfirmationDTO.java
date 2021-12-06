package com.leverx.dealerstat.dto;

import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationDTO extends BaseDTO {

    private String code;
    private Date expirationTime;
}
