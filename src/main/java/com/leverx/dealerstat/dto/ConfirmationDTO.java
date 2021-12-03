package com.leverx.dealerstat.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ConfirmationDTO {

    private String code;
    private Date expirationTime;

}
