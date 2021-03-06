package com.leverx.dealerstat.repository;

import com.leverx.dealerstat.entity.GameObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameObjectRepository extends JpaRepository<GameObject, Long> {

    boolean existsByTitle(String title);

    List<GameObject> findAllByAuthorId(Long authorId);
}
