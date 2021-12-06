package com.leverx.dealerstat.controller;

import com.leverx.dealerstat.converter.DealsConverter;
import com.leverx.dealerstat.dto.DealDTO;
import com.leverx.dealerstat.service.DealsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/deals")
public class DealsController {

    private final DealsService dealsService;
    private final DealsConverter converter;

    @Autowired
    public DealsController(DealsService dealsService, DealsConverter converter) {
        this.dealsService = dealsService;
        this.converter = converter;
    }


    @GetMapping
    public ResponseEntity<List<DealDTO>> getAllDeals() {
        List<DealDTO> dealDTOS = dealsService.findAll().stream().map(converter::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dealDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DealDTO> getDeal(@PathVariable("id") Long id) {
        DealDTO dealDTO = converter.convertToDTO(dealsService.findById(id));
        return ResponseEntity.ok(dealDTO);
    }

    @PostMapping
    public ResponseEntity<?> addDeal(@RequestBody DealDTO dealDTO) {
        dealsService.save(converter.convertToModel(dealDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
