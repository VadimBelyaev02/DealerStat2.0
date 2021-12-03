package com.leverx.dealerstat.repository;

import com.leverx.dealerstat.model.GameObject;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameObjectsRepository {

    List<GameObject> findAllByAuthorId(Long authorId);

    Optional<GameObject> findById(Long gameObjectId);

    List<GameObject> findAll();

    void save(GameObject gameObject);
}
