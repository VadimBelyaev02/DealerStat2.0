package com.leverx.dealerstat.service;

import com.leverx.dealerstat.dto.GameObjectDTO;
import com.leverx.dealerstat.entity.GameObject;

import java.util.List;

public interface GameObjectService {

    GameObjectDTO findById(Long gameObjectId);

    List<GameObjectDTO> findAll();

    GameObjectDTO save(GameObjectDTO gameObject);

    List<GameObjectDTO> findAllByAuthorId(Long id);

    GameObjectDTO update(GameObjectDTO gameObject);

    void delete(Long id);

}
