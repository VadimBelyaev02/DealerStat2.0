package com.leverx.dealerstat.converter;

import com.leverx.dealerstat.dto.DealDTO;
import com.leverx.dealerstat.entity.Deal;
import com.leverx.dealerstat.entity.User;
import com.leverx.dealerstat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DealConverter {

    private final UserService userService;

    @Autowired
    public DealConverter(UserService userService) {
        this.userService = userService;
    }

    public DealDTO convertToDTO(final Deal deal) {
        final Date date = deal.getDate();
        final Long fromID = deal.getFromUser().getId();
        final Long toId = deal.getToUser().getId();
        return DealDTO.builder()
                .date(date)
                .fromId(fromID)
                .toId(toId)
                .build();
    }

    public Deal convertToModel(final DealDTO dealDTO) {
        final Date date = new Date();
        final User fromUser = userService.findById(dealDTO.getFromId());
        final User toUser = userService.findById(dealDTO.getToId());
        return Deal.builder()
                .date(date)
                .fromUser(fromUser)
                .toUser(toUser)
                .build();
    }
}
