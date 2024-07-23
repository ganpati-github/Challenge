package com.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.controller.requests.DiscountRequest;
import com.challenge.controller.requests.DiscountResponse;
import com.challenge.exception.ApplicationRuntimeException;
import com.challenge.services.DiscountService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("challenge/v1/discounts")
@Slf4j
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @GetMapping
    public ResponseEntity<DiscountResponse> createDiscount(@Valid @RequestBody DiscountRequest request) throws ApplicationRuntimeException {
    	log.info("Strtaed processing in DiscountController:createDiscount");
    	
        DiscountResponse discountResponse = discountService.discountCalculation(request.getUser(), request.getBill());
        
        log.info("Finished processing in DiscountController:createDiscount");
        return ResponseEntity.ok(discountResponse);
    }

}
