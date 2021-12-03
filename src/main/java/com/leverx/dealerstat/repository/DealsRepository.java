package com.leverx.dealerstat.repository;

import com.leverx.dealerstat.model.Deal;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DealsRepository {
    List<Deal> findAll();

    Optional<Deal> findById(Long id);
}
