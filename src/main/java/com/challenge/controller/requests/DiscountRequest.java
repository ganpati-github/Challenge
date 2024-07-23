package com.challenge.controller.requests;


import com.challenge.models.Bill;
import com.challenge.models.User;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountRequest {

	@NotNull
    private User user;

	@NotNull
    private Bill bill;

}
