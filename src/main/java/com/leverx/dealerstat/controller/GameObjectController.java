package com.leverx.dealerstat.controller;

import com.leverx.dealerstat.converter.GameObjectConverter;
import com.leverx.dealerstat.dto.GameObjectDTO;
import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.entity.User;
import com.leverx.dealerstat.security.AuthenticatedUserFactory;
import com.leverx.dealerstat.service.GameObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/objects")
public class GameObjectController {

    private final GameObjectService gameObjectService;
    private final AuthenticatedUserFactory userFactory;

    @Autowired
    public GameObjectController(GameObjectService gameObjectService,
                                AuthenticatedUserFactory userFactory) {
        this.gameObjectService = gameObjectService;
        this.userFactory = userFactory;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public GameObjectDTO updateGameObject(@RequestBody GameObjectDTO gameObject) {
        gameObjectService.update(gameObjectConverter.convertToModel(gameObject), id);
        return null;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GameObjectDTO getGameObject(@PathVariable("id") Long id) {
        return gameObjectService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameObjectDTO addGameObject(@RequestBody GameObjectDTO gameObject) {
        return gameObjectService.save(gameObject);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GameObjectDTO> getAllGameObjects() {
        return gameObjectService.findAll();
    }

    @GetMapping("/my")
    @ResponseStatus(HttpStatus.OK)
    public List<GameObjectDTO> getAuthorizedUserGameObjects() {
       UserDTO user = userFactory.currentUser();
       return gameObjectService.findAllByAuthorId(user.getId());
    }


}
