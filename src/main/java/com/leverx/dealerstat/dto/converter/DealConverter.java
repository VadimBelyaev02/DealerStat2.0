package com.leverx.dealerstat.dto.converter;

import com.leverx.dealerstat.dto.DealDTO;
import com.leverx.dealerstat.entity.Deal;
import com.leverx.dealerstat.entity.GameObject;
import com.leverx.dealerstat.entity.User;
import com.leverx.dealerstat.repository.DealRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DealConverter {

    private final DealRepository dealRepository;

    public DealConverter(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

    public DealDTO convertToDTO(final Deal deal) {
        final Long id = deal.getId();
        final Long objectId = deal.getGameObject().getId();
        final LocalDate date = deal.getDate();
        final Long fromID = deal.getFromUser().getId();
        final Long toId = deal.getToUser().getId();
        return DealDTO.builder()
                .id(id)
                .objectId(objectId)
                .date(date)
                .fromId(fromID)
                .toId(toId)
                .build();
    }

    public Deal convertToModel(final DealDTO dealDTO) {
        final Deal deal  = dealRepository.getById(dealDTO.getId());
        final Long id = dealDTO.getId();
        final GameObject object = deal.getGameObject();
        final User from = deal.getFromUser();
        final User to = deal.getToUser();
        final LocalDate date = dealDTO.getDate();
        return Deal.builder()
                .id(id)
                .date(date)
                .fromUser(from)
                .toUser(to)
                .gameObject(object)
                .build();
    }
}
