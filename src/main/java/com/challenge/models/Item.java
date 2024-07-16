package com.challenge.models;


import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Item {

	//@NotBlank(message = "Item type should not be blank")
	@NotNull(message = "Item type should not be null")
    private ItemType type;

	//@NotBlank(message = "price should not be blank")
	@NotNull(message = "price should not be null")
    private BigDecimal price;
}
