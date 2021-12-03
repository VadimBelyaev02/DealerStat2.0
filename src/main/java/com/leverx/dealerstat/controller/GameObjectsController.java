package com.leverx.dealerstat.controller;

import com.leverx.dealerstat.converter.GameObjectsConverter;
import com.leverx.dealerstat.dto.GameObjectDTO;
import com.leverx.dealerstat.model.User;
import com.leverx.dealerstat.security.AuthenticatedUserFactory;
import com.leverx.dealerstat.service.GameObjectService;
import com.leverx.dealerstat.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GameObjectsController {

    private final GameObjectsConverter gameObjectsConverter;
    private final UsersService usersService;
    private final GameObjectService gameObjectService;
    private final AuthenticatedUserFactory userFactory;

    @Autowired
    public GameObjectsController(GameObjectsConverter gameObjectsConverter,
                                 UsersService usersService,
                                 GameObjectService gameObjectService,
                                 AuthenticatedUserFactory userFactory) {
        this.gameObjectsConverter = gameObjectsConverter;
        this.usersService = usersService;
        this.gameObjectService = gameObjectService;
        this.userFactory = userFactory;
    }


    @PutMapping("/objects/{id}")
    public ResponseEntity<GameObjectDTO> editGameObject(@RequestBody GameObjectDTO gameObject,
                                                        @PathVariable("id") Long id) {
        User user = userFactory.currentUser();
        if (!gameObjectService.findById(id).getAuthor().getId().equals(user.getId())) {
            return ResponseEntity.notFound().build();
        }
        gameObjectService.update(gameObjectsConverter.convertToModel(gameObject), id);
        return null;
    }

    @GetMapping("/objects/{id}")
    public ResponseEntity<GameObjectDTO> getGameObject(@PathVariable("id") Long id) {
        GameObjectDTO gameObjectDTO = gameObjectsConverter.convertToDTO(gameObjectService.findById(id));
        return ResponseEntity.ok(gameObjectDTO);
    }

    @PostMapping("/objects")
    public ResponseEntity<GameObjectDTO> addGameObject(@RequestBody GameObjectDTO gameObject) {
        gameObjectService.save(gameObjectsConverter.convertToModel(gameObject));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/objects")
    public ResponseEntity<List<GameObjectDTO>> getGameObjects() {
        List<GameObjectDTO> gameObjectsDTO = gameObjectService.findAll().stream()
                .map(gameObjectsConverter::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(gameObjectsDTO);
    }

    @GetMapping("/objects/my")
    public ResponseEntity<List<GameObjectDTO>> getAuthorizedUserGameObjects() {
       User user = userFactory.currentUser();
        List<GameObjectDTO> gameObjectDTOS = gameObjectService.findAllByAuthorId(user.getId())
                .stream().map(gameObjectsConverter::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(gameObjectDTOS);
    }


}
