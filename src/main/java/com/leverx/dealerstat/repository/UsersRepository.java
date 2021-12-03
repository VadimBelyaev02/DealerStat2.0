package com.leverx.dealerstat.repository;

import com.leverx.dealerstat.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository{

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    void save(User user);

    Optional<User> findById(Long id);

    List<User> findAll();

}
