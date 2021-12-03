package com.leverx.dealerstat.controller;

import com.leverx.dealerstat.converter.DealsConverter;
import com.leverx.dealerstat.dto.DealDTO;
import com.leverx.dealerstat.service.DealsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DealsController {

    private final DealsService dealsService;
    private final DealsConverter converter;

    @Autowired
    public DealsController(DealsService dealsService, DealsConverter converter) {
        this.dealsService = dealsService;
        this.converter = converter;
    }


    @GetMapping("/deals")
    public ResponseEntity<List<DealDTO>> getAllDeals() {
        List<DealDTO> dealDTOS = dealsService.findAll().stream().map(converter::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dealDTOS);
    }

    @GetMapping("/deals/{id}")
    public ResponseEntity<DealDTO> getDeal(@PathVariable("id") Long id) {
        DealDTO dealDTO = converter.convertToDTO(dealsService.findById(id));
        return ResponseEntity.ok(dealDTO);
    }



}
