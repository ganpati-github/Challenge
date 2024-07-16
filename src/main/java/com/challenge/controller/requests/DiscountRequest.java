package com.challenge.controller.requests;


import com.challenge.models.Bill;
import com.challenge.models.User;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountRequest {

	@Valid
    private User user;

	@Valid
    private Bill bill;

}
