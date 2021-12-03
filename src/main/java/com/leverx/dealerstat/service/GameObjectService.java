package com.leverx.dealerstat.service;

import com.leverx.dealerstat.model.GameObject;

import java.util.List;

public interface GameObjectService {
    GameObject findById(Long gameObjectId);

    List<GameObject> findAll();

    void save(GameObject gameObject);

    List<GameObject> findAllByAuthorId(Long id);

    void update(GameObject gameObject, Long id);
}
