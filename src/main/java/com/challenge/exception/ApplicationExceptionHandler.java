package com.challenge.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String,String> handleInvalidArgument(MethodArgumentNotValidException ex){
		Map<String,String> map = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error->{
			map.put(error.getField(), error.getDefaultMessage());
		});
		return map;
	}

}
