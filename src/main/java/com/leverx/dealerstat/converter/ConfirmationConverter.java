package com.leverx.dealerstat.converter;

import com.leverx.dealerstat.dto.ConfirmationDTO;
import com.leverx.dealerstat.entity.Confirmation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Component
public class ConfirmationConverter {

    public ConfirmationDTO convertToDTO(final Confirmation confirmation) {
        final String code = confirmation.getCode();
        final LocalDate expirationTime = confirmation.getExpirationTime();
        return ConfirmationDTO.builder()
                .code(code)
                .expirationTime(expirationTime)
                .build();
    }

    public Confirmation convertToModel(final ConfirmationDTO confirmationDTO) {
        final String code = confirmationDTO.getCode();
        final LocalDate expirationTime = confirmationDTO.getExpirationTime();
        return Confirmation.builder()
                .code(code)
                .expirationTime(expirationTime)
                .build();
    }
}
