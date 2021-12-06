package com.leverx.dealerstat.repository;

import com.leverx.dealerstat.model.GameObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameObjectsRepository extends JpaRepository<GameObject, Long> {

    List<GameObject> findAllByAuthorId(Long authorId);

}
