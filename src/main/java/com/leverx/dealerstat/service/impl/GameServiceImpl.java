package com.leverx.dealerstat.service.impl;

import com.leverx.dealerstat.converter.GameConverter;
import com.leverx.dealerstat.dto.GameDTO;
import com.leverx.dealerstat.exception.AlreadyExistsException;
import com.leverx.dealerstat.exception.NotFoundException;
import com.leverx.dealerstat.entity.Game;
import com.leverx.dealerstat.repository.GameRepository;
import com.leverx.dealerstat.service.GameService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final GameConverter gameConverter;

    public GameServiceImpl(GameRepository gameRepository, GameConverter gameConverter) {
        this.gameRepository = gameRepository;
        this.gameConverter = gameConverter;
    }


    @Override
    public GameDTO findById(Long id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Game is not found");
        });
        return gameConverter.convertToDTO(game);
    }

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
        if (gameRepository.existsById(game.getId())) {
            throw new AlreadyExistsException("Game is already exists");
        }
        return gameConverter.convertToDTO(gameRepository.save(gameConverter.convertToModel(game)));
    }

    @Override
    @Transactional
    public GameDTO update(GameDTO game) {
        if (!gameRepository.existsById(game.getId())) {
            throw new NotFoundException("Game is not found");
        }
        return gameConverter.convertToDTO(gameRepository.save(gameConverter.convertToModel(game)));
     //   game.setId(id);
     //   repository.save(game);
    }

    @Override
    public void delete(Long id) {
        gameRepository.deleteById(id);
    }
}
