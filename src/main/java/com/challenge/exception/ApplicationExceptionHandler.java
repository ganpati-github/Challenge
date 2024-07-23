package com.challenge.exception;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.challenge.controller.requests.DiscountResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {
	
	private static final String VALIDATION_ERROR_MESSAGES= "Please check the errors list for validations";
	
	private static final String INTERNAL_SERVER_ERROR_MESSAGES= "INTERNAL_SERVER_ERROR";
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<DiscountResponse> handleInvalidArgument(MethodArgumentNotValidException ex){
		log.info("Started processing in ApplicationExceptionHandler:handleInvalidArgument");
		List<String> erroList = new ArrayList<String>();
		ex.getBindingResult().getFieldErrors().forEach(error->{
			erroList.add(error.getField()+ " : "+error.getDefaultMessage());
		});
		
		ApiError apiError = 
			      new ApiError(HttpStatus.BAD_REQUEST, VALIDATION_ERROR_MESSAGES, erroList);

		DiscountResponse discountResponse = new DiscountResponse();
		discountResponse.setApiError(apiError);
		
		log.info("Finished processing in ApplicationExceptionHandler:handleInvalidArgument");
		return new ResponseEntity<DiscountResponse>(discountResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ApplicationRuntimeException.class)
	public ResponseEntity<DiscountResponse> handleApplicationRuntimeException(ApplicationRuntimeException ex){
		log.info("Started processing in ApplicationExceptionHandler:handleApplicationRuntimeException");
		ApiError apiError = 
			      new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MESSAGES, ex.getMessage());

		DiscountResponse discountResponse = new DiscountResponse();
		discountResponse.setApiError(apiError);
		
		log.info("Finished processing in ApplicationExceptionHandler:handleApplicationRuntimeException");
		return new ResponseEntity<DiscountResponse>(discountResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(DateTimeParseException.class)
	public ResponseEntity<DiscountResponse> handleDateTimeParseException(DateTimeParseException ex){
		log.info("Started processing in ApplicationExceptionHandler:handleDateTimeParseException");
		ApiError apiError = 
			      new ApiError(HttpStatus.BAD_REQUEST, VALIDATION_ERROR_MESSAGES, "RegisterDate format should be yyyy-mm-dd");
		
		DiscountResponse discountResponse = new DiscountResponse();
		discountResponse.setApiError(apiError);
		
		log.info("Finished processing in ApplicationExceptionHandler:handleDateTimeParseException");
		return new ResponseEntity<DiscountResponse>(discountResponse, HttpStatus.BAD_REQUEST);
	}

}
