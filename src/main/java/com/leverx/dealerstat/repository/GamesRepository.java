package com.leverx.dealerstat.repository;

import com.leverx.dealerstat.model.Game;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GamesRepository {

    boolean existsByName(String name);

    Game save(Game game);

    List<Game> findAll();

    Optional<Game> findById(Long id);
}
