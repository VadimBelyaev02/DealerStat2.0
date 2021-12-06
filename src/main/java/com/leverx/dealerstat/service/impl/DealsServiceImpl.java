package com.leverx.dealerstat.service.impl;

import com.leverx.dealerstat.exception.NotFoundException;
import com.leverx.dealerstat.model.Deal;
import com.leverx.dealerstat.model.GameObject;
import com.leverx.dealerstat.repository.DealsRepository;
import com.leverx.dealerstat.service.DealsService;
import com.leverx.dealerstat.service.GameObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DealsServiceImpl implements DealsService {

    private final DealsRepository repository;
    private final GameObjectService gameObjectService;

    @Autowired
    public DealsServiceImpl(DealsRepository repository, GameObjectService gameObjectService) {
        this.repository = repository;
        this.gameObjectService = gameObjectService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Deal> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Deal findById(Long id) {
        return repository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("The deal is not found");
        });
    }

    @Override
    public void save(Deal deal) {
        repository.save(deal);
        GameObject gameObject = gameObjectService.findById(deal.getGameObject().getId());
        gameObject.setAuthor(deal.getToUser());
        gameObjectService.save(gameObject);

    }
}
