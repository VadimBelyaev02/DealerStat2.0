package com.leverx.dealerstat.controller;

import com.leverx.dealerstat.converter.GameConverter;
import com.leverx.dealerstat.dto.GameDTO;
import com.leverx.dealerstat.security.AuthenticatedUserFactory;
import com.leverx.dealerstat.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService, GameConverter converter,
                          AuthenticatedUserFactory userFactory) {
        this.gameService = gameService;
        this.userFactory = userFactory;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GameDTO> getAllGames() {
        return gameService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameDTO addGame(@RequestBody GameDTO gameDTO) {
        return gameService.save(gameDTO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public GameDTO updateGame(@RequestBody GameDTO gameDTO){
        return gameService.update(gameDTO);
    }

}
