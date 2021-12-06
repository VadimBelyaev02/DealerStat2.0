package com.leverx.dealerstat.service;

import com.leverx.dealerstat.entity.Deal;

import java.util.List;

public interface DealService {
    List<Deal> findAll();

    Deal findById(Long id);

    void save(Deal deal);
}
