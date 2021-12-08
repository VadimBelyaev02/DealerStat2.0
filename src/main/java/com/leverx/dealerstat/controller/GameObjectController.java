package com.leverx.dealerstat.controller;

import com.leverx.dealerstat.dto.GameObjectDTO;
import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.security.AuthenticatedUserFactory;
import com.leverx.dealerstat.service.GameObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/objects")
public class GameObjectController {

    private final GameObjectService gameObjectService;

    @Autowired
    public GameObjectController(GameObjectService gameObjectService) {
        this.gameObjectService = gameObjectService;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public GameObjectDTO updateGameObject(@RequestBody @Valid GameObjectDTO gameObject, BindingResult result) {
        return gameObjectService.update(gameObject);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GameObjectDTO getGameObject(@PathVariable("id") Long id) {
        return gameObjectService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameObjectDTO addGameObject(@RequestBody @Valid GameObjectDTO gameObject, BindingResult result) {
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
  //     UserDTO user = userFactory.currentUser();
         //  return gameObjectService.findAllByAuthorId(user.getId());
    return null
            ;
    }


}
