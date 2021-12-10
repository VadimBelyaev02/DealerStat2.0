package com.leverx.dealerstat.service.impl;

import com.leverx.dealerstat.dto.converter.DealConverter;
import com.leverx.dealerstat.dto.DealDTO;
import com.leverx.dealerstat.exception.AlreadyExistsException;
import com.leverx.dealerstat.exception.NotFoundException;
import com.leverx.dealerstat.entity.Deal;
import com.leverx.dealerstat.repository.DealRepository;
import com.leverx.dealerstat.service.DealService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;
    private final DealConverter dealConverter;

    public DealServiceImpl(DealRepository dealRepository, DealConverter dealConverter) {
        this.dealRepository = dealRepository;
        this.dealConverter = dealConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DealDTO> getAll() {
        return dealRepository.findAll().stream()
                .map(dealConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DealDTO getById(Long id) {
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
        return dealConverter.convertToDTO(dealRepository.save(dealConverter.convertToModel(deal)));

    }

    @Override
    @Transactional
    public DealDTO update(DealDTO dealDTO) {
        if (!dealRepository.existsById(dealDTO.getId())) {
            throw new NotFoundException("Deal is not found");
        }
        return dealConverter.convertToDTO(dealRepository.save(dealConverter.convertToModel(dealDTO)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!dealRepository.existsById(id)) {
            throw new NotFoundException("Deals is not found");
        }
        dealRepository.deleteById(id);
    }
}
