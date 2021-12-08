package com.leverx.dealerstat.service;

import com.leverx.dealerstat.dto.GameObjectDTO;
import com.leverx.dealerstat.entity.GameObject;

import java.util.List;

public interface GameObjectService {

    GameObjectDTO getById(Long gameObjectId);

    List<GameObjectDTO> getAll();

    GameObjectDTO save(GameObjectDTO gameObject);

    List<GameObjectDTO> getAllByCurrentUser();

    GameObjectDTO update(GameObjectDTO gameObject);

    void delete(Long id);

}
