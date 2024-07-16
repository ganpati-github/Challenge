package com.challenge.models;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
	
	//@NotBlank(message = "UserType should not be blank")
	@NotNull(message = "UserType should not be null")
	private UserType type;

	//@NotBlank(message = "registerDate should not be blank")
	@NotNull(message = "registerDate should not be null")
	private LocalDate registerDate;
}
