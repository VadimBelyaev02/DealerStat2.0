package com.leverx.dealerstat.repository;

import com.leverx.dealerstat.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamesRepository extends JpaRepository<Game, Long> {

    boolean existsByName(String name);
}
