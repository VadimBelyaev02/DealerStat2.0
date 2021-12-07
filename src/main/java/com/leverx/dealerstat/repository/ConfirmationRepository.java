package com.leverx.dealerstat.repository;

import com.leverx.dealerstat.entity.Confirmation;
import com.leverx.dealerstat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationRepository extends JpaRepository<Confirmation, Long> {

    Optional<Confirmation> findByCode(String code);

    boolean existsByUserId(Long userId);

    void deleteByUserId(Long userId);

}
