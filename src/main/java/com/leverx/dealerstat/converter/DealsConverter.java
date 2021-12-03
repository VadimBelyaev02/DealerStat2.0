package com.leverx.dealerstat.converter;

import com.leverx.dealerstat.dto.DealDTO;
import com.leverx.dealerstat.model.Deal;
import com.leverx.dealerstat.model.User;
import com.leverx.dealerstat.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DealsConverter {

    private final UsersService usersService;

    @Autowired
    public DealsConverter(UsersService usersService) {
        this.usersService = usersService;
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
        final User fromUser = usersService.findById(dealDTO.getFromId());
        final User toUser = usersService.findById(dealDTO.getToId());
        return Deal.builder()
                .date(date)
                .fromUser(fromUser)
                .toUser(toUser)
                .build();
    }
}
