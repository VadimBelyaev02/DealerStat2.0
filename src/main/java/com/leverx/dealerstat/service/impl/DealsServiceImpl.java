package com.leverx.dealerstat.service.impl;

import com.leverx.dealerstat.exception.NotFoundException;
import com.leverx.dealerstat.model.Deal;
import com.leverx.dealerstat.repository.DealsRepository;
import com.leverx.dealerstat.service.DealsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DealsServiceImpl implements DealsService {

    private final DealsRepository repository;

    @Autowired
    public DealsServiceImpl(DealsRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public List<Deal> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Deal findById(Long id) {
        return repository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("The deal is not found");
        });
    }
}
