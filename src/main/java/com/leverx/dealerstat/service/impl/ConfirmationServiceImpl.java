package com.leverx.dealerstat.service.impl;

import com.leverx.dealerstat.dto.converter.ConfirmationConverter;
import com.leverx.dealerstat.dto.converter.UserConverter;
import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.exception.NotFoundException;
import com.leverx.dealerstat.entity.Confirmation;
import com.leverx.dealerstat.repository.ConfirmationRepository;
import com.leverx.dealerstat.service.ConfirmationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConfirmationServiceImpl implements ConfirmationService {

    private final ConfirmationRepository confirmationRepository;
    private final UserConverter userConverter;

    public ConfirmationServiceImpl(ConfirmationRepository confirmationRepository, ConfirmationConverter confirmationConverter, UserConverter userConverter) {
        this.confirmationRepository = confirmationRepository;
        this.userConverter = userConverter;
    }


    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserByCode(String code) {
        Confirmation confirmation = confirmationRepository.findByCode(code).orElseThrow(() -> {
            throw new NotFoundException("Code is not found");
        });
        return userConverter.convertToDTO(confirmation.getUser());
    }

    @Override
    @Transactional(readOnly = true)
    public String checkCode(String code) {
        return confirmationRepository.findByCode(code).orElseThrow(() -> {
            throw new NotFoundException("The code is not found");
        }).getCode();
    }
}
