package com.challenge.services;


import com.challenge.controller.requests.DiscountResponse;
import com.challenge.exception.ApplicationRuntimeException;
import com.challenge.models.Bill;
import com.challenge.models.User;

/**
 * This is the Discount interface file
 */
public interface DiscountService {

    /**
     * This method calculate discount for given user and bill
     * @param user  - User
     * @param bill - Bill
     * @return Double - payable amount for the user
     */
	DiscountResponse discountCalculation(User user, Bill bill) throws ApplicationRuntimeException;
}
