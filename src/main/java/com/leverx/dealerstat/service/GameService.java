package com.leverx.dealerstat.service;

import com.leverx.dealerstat.dto.GameDTO;
import com.leverx.dealerstat.entity.Game;

import java.util.List;

public interface GameService {

    List<GameDTO> findAll();

    GameDTO save(GameDTO convertToModel);

    GameDTO update(GameDTO game);
}
