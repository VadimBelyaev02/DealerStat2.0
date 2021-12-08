package com.leverx.dealerstat.service;

import com.leverx.dealerstat.dto.GameDTO;
import com.leverx.dealerstat.entity.Game;

import java.util.List;
import java.util.Optional;

public interface GameService {

    GameDTO getById(Long id);

    List<GameDTO> getAll();

    GameDTO save(GameDTO convertToModel);

    GameDTO update(GameDTO game);

    void delete(Long id);
}
