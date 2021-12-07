package com.leverx.dealerstat.service.impl;

import com.leverx.dealerstat.converter.GameConverter;
import com.leverx.dealerstat.dto.GameDTO;
import com.leverx.dealerstat.exception.NotFoundException;
import com.leverx.dealerstat.entity.Game;
import com.leverx.dealerstat.repository.GameRepository;
import com.leverx.dealerstat.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final GameConverter gameConverter;


    @Override
    @Transactional(readOnly = true)
    public List<GameDTO> findAll() {
        return gameRepository.findAll().stream()
                .map(gameConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GameDTO save(GameDTO game) {
        return gameConverter.convertToDTO(gameRepository.save(gameConverter.convertToModel(game)));
    }

    @Override
    @Transactional
    public GameDTO update(GameDTO game) {
        if (!gameRepository.existsById(game.getId())) {
            throw new NotFoundException("Game is not found");
        }
     //   game.setId(id);
     //   repository.save(game);
    }
}
