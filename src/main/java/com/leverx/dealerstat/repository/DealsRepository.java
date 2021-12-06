package com.leverx.dealerstat.repository;

import com.leverx.dealerstat.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealsRepository extends JpaRepository<Deal, Long> {
}
