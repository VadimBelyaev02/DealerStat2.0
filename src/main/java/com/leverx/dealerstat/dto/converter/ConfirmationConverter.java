package com.leverx.dealerstat.dto.converter;

import com.leverx.dealerstat.dto.ConfirmationDTO;
import com.leverx.dealerstat.entity.Confirmation;
import com.leverx.dealerstat.entity.User;
import com.leverx.dealerstat.repository.ConfirmationRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ConfirmationConverter {

    private final ConfirmationRepository confirmationRepository;

    public ConfirmationConverter(ConfirmationRepository confirmationRepository) {
        this.confirmationRepository = confirmationRepository;
    }

    public ConfirmationDTO convertToDTO(final Confirmation confirmation) {
        final Long id = confirmation.getId();
        final String code = confirmation.getCode();
        final LocalDate expirationTime = confirmation.getExpirationTime();
        final Long userId = confirmation.getUser().getId();
        return ConfirmationDTO.builder()
                .id(id)
                .code(code)
                .expirationTime(expirationTime)
                .userId(userId)
                .build();
    }

    public Confirmation convertToModel(final ConfirmationDTO confirmationDTO) {
        final Long id = confirmationDTO.getId();
        final String code = confirmationDTO.getCode();
        final LocalDate expirationTime = confirmationDTO.getExpirationTime();
        final User user = confirmationRepository.getById(confirmationDTO.getUserId()).getUser();
        return Confirmation.builder()
                .id(id)
                .code(code)
                .expirationTime(expirationTime)
                .user(user)
                .build();
    }
}
