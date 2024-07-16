package com.challenge.controller.requests;


import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountResponse {

    private BigDecimal totalBill;
    
    private BigDecimal userDiscount;
    
    private BigDecimal extraDiscount;
    
    private BigDecimal finalBill;

}
