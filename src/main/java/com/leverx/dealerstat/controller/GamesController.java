package com.leverx.dealerstat.controller;

import com.leverx.dealerstat.converter.GamesConverter;
import com.leverx.dealerstat.dto.GameDTO;
import com.leverx.dealerstat.model.Game;
import com.leverx.dealerstat.security.AuthenticatedUserFactory;
import com.leverx.dealerstat.service.GamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GamesController {

    private final GamesService gamesService;
    private final GamesConverter converter;
    private final AuthenticatedUserFactory userFactory;

    @Autowired
    public GamesController(GamesService gamesService, GamesConverter converter,
                           AuthenticatedUserFactory userFactory) {
        this.gamesService = gamesService;
        this.converter = converter;
        this.userFactory = userFactory;
    }

    @GetMapping("/games")
    public ResponseEntity<List<GameDTO>> getAllGames() {
        List<GameDTO> gameDTOS = gamesService.findAll().stream().map(converter::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(gameDTOS);
    }

    @PostMapping("/games")
    public ResponseEntity<?> addGame(@RequestBody GameDTO gameDTO) {
        gamesService.save(converter.convertToModel(gameDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/games/{id}")
    public ResponseEntity<?> updateGame(@RequestBody GameDTO gameDTO,
                                        @PathVariable("id") Long id) {
        gamesService.update(converter.convertToModel(gameDTO), id);
        return ResponseEntity.ok().build();
    }

}
