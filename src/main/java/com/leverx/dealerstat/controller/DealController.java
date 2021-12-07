package com.leverx.dealerstat.controller;

import com.leverx.dealerstat.dto.DealDTO;
import com.leverx.dealerstat.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deals")
public class DealController {

    private final DealService dealService;

    @Autowired
    public DealController(DealService dealService) {
        this.dealService = dealService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DealDTO> getAllDeals() {
        return dealService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DealDTO getDeal(@PathVariable("id") Long id) {
        return dealService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DealDTO addDeal(@RequestBody DealDTO dealDTO) {
        return dealService.save(dealDTO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public DealDTO updateDeal(@RequestBody DealDTO dealDTO) {
        return dealService.update(dealDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteDeal(@PathVariable("id") Long id) {
        dealService.delete();
    }
}
