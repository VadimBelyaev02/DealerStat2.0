package com.leverx.dealerstat.controller;

import com.leverx.dealerstat.dto.GameDTO;
import com.leverx.dealerstat.exception.NotValidException;
import com.leverx.dealerstat.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GameDTO findById(@PathVariable("id") Long id) {
        return gameService.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GameDTO> getAllGames() {
        return gameService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameDTO addGame(@RequestBody @Valid GameDTO gameDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new NotValidException(result.getAllErrors().toString());
        }
        return gameService.save(gameDTO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public GameDTO updateGame(@RequestBody @Valid GameDTO gameDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new NotValidException(result.getAllErrors().toString());
        }
        return gameService.update(gameDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGame(@PathVariable("id") Long id) {
        gameService.delete(id);
    }
}
