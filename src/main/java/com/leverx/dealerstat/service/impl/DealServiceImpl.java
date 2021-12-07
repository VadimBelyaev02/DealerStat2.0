package com.leverx.dealerstat.service.impl;

import com.leverx.dealerstat.converter.DealConverter;
import com.leverx.dealerstat.dto.DealDTO;
import com.leverx.dealerstat.exception.AlreadyExistsException;
import com.leverx.dealerstat.exception.NotFoundException;
import com.leverx.dealerstat.entity.Deal;
import com.leverx.dealerstat.entity.GameObject;
import com.leverx.dealerstat.repository.DealRepository;
import com.leverx.dealerstat.service.DealService;
import com.leverx.dealerstat.service.GameObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;
    private final GameObjectService gameObjectService;
    private final DealConverter dealConverter;

    @Autowired
    public DealServiceImpl(DealRepository repository, GameObjectService gameObjectService) {
        this.repository = repository;
        this.gameObjectService = gameObjectService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DealDTO> findAll() {
        return dealRepository.findAll().stream()
                .map(dealConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DealDTO findById(Long id) {
        Deal deal = dealRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("The deal is not found");
        });
        return dealConverter.convertToDTO(deal);
    }

    @Override
    @Transactional
    public DealDTO save(DealDTO deal) {
        if (dealRepository.existsById(deal.getId())) {
            throw new AlreadyExistsException("Deal is not found");
        }
        return dealConverter.convertToDTO(dealRepository.save(dealConverter.convertToModel(deal));
        //    GameObject gameObject = gameObjectService.findById(deal.getGameObject().getId());
        //    gameObject.setAuthor(deal.getToUser());
        //    gameObjectService.save(gameObject);

    }

    @Override
    @Transactional
    public DealDTO update(DealDTO dealDTO) {
        Deal deal = dealRepository.findById(dealDTO.getId()).orElseThrow(() -> {
            throw new NotFoundException("Deal is not found");
        });
        return dealConverter.convertToDTO(dealRepository.save(dealConverter.convertToModel(dealDTO)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        dealRepository.deleteById(id);
    }
}
