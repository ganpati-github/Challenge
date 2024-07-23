package com.challenge.controller.requests;


import java.math.BigDecimal;

import com.challenge.exception.ApiError;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiscountResponse {

    private BigDecimal totalBill;
    
    private BigDecimal userDiscount;
    
    private BigDecimal extraDiscount;
    
    private BigDecimal finalBill;
    
    private ApiError apiError;
}
