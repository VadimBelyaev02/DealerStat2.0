package com.leverx.dealerstat.converter;

import com.leverx.dealerstat.dto.ConfirmationDTO;
import com.leverx.dealerstat.model.Confirmation;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ConfirmationsConverter {

    public ConfirmationDTO convertToDTO(final Confirmation confirmation) {
        final String code = confirmation.getCode();
        final Date expirationTime = confirmation.getExpirationTime();
        return ConfirmationDTO.builder()
                .code(code)
                .expirationTime(expirationTime)
                .build();
    }

    public Confirmation convertToModel(final ConfirmationDTO confirmationDTO) {
        final String code = confirmationDTO.getCode();
        final Date expirationTime = confirmationDTO.getExpirationTime();
        return Confirmation.builder()
                .code(code)
                .expirationTime(expirationTime)
                .build();
    }
}
