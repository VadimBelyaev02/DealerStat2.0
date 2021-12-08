package com.leverx.dealerstat.controller;

import com.leverx.dealerstat.dto.DealDTO;
import com.leverx.dealerstat.exception.NotValidException;
import com.leverx.dealerstat.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/deals")
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
    public DealDTO addDeal(@RequestBody @Valid DealDTO dealDTO, BindingResult result) {
        if (result.hasErrors()) {
            result.getFieldError();
            throw new NotValidException(result.getAllErrors().toString());
        }
        return dealService.save(dealDTO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public DealDTO updateDeal(@RequestBody @Valid DealDTO dealDTO, BindingResult result) {
        if (result.hasErrors()) {
            result.getFieldError();
            throw new NotValidException(result.getAllErrors().toString());
        }
        return dealService.update(dealDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteDeal(@PathVariable("id") Long id) {
        dealService.delete(id);
    }
}
