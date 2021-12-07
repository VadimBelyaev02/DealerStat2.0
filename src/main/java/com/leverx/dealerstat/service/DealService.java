package com.leverx.dealerstat.service;

import com.leverx.dealerstat.dto.DealDTO;
import com.leverx.dealerstat.entity.Deal;

import java.util.List;

public interface DealService {

    List<DealDTO> findAll();

    DealDTO findById(Long id);

    DealDTO save(DealDTO deal);

    DealDTO update(DealDTO dealDTO);

    void delete(Long id);
}
