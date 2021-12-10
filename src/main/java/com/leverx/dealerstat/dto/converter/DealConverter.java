package com.leverx.dealerstat.dto.converter;

import com.leverx.dealerstat.dto.DealDTO;
import com.leverx.dealerstat.entity.Deal;
import com.leverx.dealerstat.entity.GameObject;
import com.leverx.dealerstat.entity.User;
import com.leverx.dealerstat.repository.GameObjectRepository;
import com.leverx.dealerstat.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DealConverter {

    private final UserRepository userRepository;
    private final GameObjectRepository gameObjectRepository;

    public DealConverter(UserRepository userRepository, GameObjectRepository gameObjectRepository) {
        this.userRepository = userRepository;
        this.gameObjectRepository = gameObjectRepository;
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
        final Long id = dealDTO.getId();
        final GameObject object = gameObjectRepository.getById(dealDTO.getObjectId());
        final User from = userRepository.getById(dealDTO.getFromId());
        final User to = userRepository.getById(dealDTO.getToId());
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
