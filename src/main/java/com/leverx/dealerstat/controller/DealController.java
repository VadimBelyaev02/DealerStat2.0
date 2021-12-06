package com.leverx.dealerstat.controller;

import com.leverx.dealerstat.converter.DealConverter;
import com.leverx.dealerstat.dto.DealDTO;
import com.leverx.dealerstat.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/deals")
public class DealController {

    private final DealService dealService;
    private final DealConverter converter;

    @Autowired
    public DealController(DealService dealService, DealConverter converter) {
        this.dealService = dealService;
        this.converter = converter;
    }

    @GetMapping
    public ResponseEntity<List<DealDTO>> getAllDeals() {
        List<DealDTO> dealDTOS = dealService.findAll().stream().map(converter::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dealDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DealDTO> getDeal(@PathVariable("id") Long id) {
        DealDTO dealDTO = converter.convertToDTO(dealService.findById(id));
        return ResponseEntity.ok(dealDTO);
    }

    @PostMapping
    public ResponseEntity<?> addDeal(@RequestBody DealDTO dealDTO) {
        dealService.save(converter.convertToModel(dealDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
