package com.leverx.dealerstat.service;

import com.leverx.dealerstat.model.Deal;

import java.util.List;

public interface DealsService {
    List<Deal> findAll();

    Deal findById(Long id);
}
