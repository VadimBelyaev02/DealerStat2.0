package com.leverx.dealerstat.dto.converter;

import com.leverx.dealerstat.dto.GameDTO;
import com.leverx.dealerstat.entity.Game;
import org.springframework.stereotype.Component;

@Component
public class GameConverter {

    public GameDTO convertToDTO(final Game game) {
        final Long id = game.getId();
        final String name = game.getName();
        return GameDTO.builder()
                .id(id)
                .name(name)
                .build();
    }

    public Game convertToModel(final GameDTO gameDTO) {
        final Long id = gameDTO.getId();
        final String name = gameDTO.getName();
        return Game.builder()
                .id(id)
                .name(name)
                .build();
    }
}
