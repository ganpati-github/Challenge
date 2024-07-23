package com.challenge.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.controller.requests.DiscountResponse;
import com.challenge.exception.ApplicationRuntimeException;
import com.challenge.helper.DiscountUtility;
import com.challenge.models.Bill;
import com.challenge.models.ItemType;
import com.challenge.models.User;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Service
@Slf4j
public class DiscountServiceImpl implements DiscountService {
	
	@Autowired
	private DiscountUtility discountUtility;

	@Override
	public DiscountResponse discountCalculation(User user, Bill bill) throws ApplicationRuntimeException {
		log.info("Started processing in DiscountServiceImpl:discountCalculation");
		
		DiscountResponse discountResponse = new DiscountResponse();
		try {
	        BigDecimal totalAmount = discountUtility.calculateTotal(bill.getItems());
	        BigDecimal groceryAmount = discountUtility.calculateTotalPerType(bill.getItems(), ItemType.GROCERY);
	        BigDecimal nonGroceryAmount = totalAmount.subtract(groceryAmount);
	        BigDecimal nonGroceryAmountRef = nonGroceryAmount;
	        BigDecimal userDiscount = discountUtility.getUserDiscount(user);
	        BigDecimal billsDiscount = discountUtility.calculateBillsDiscount(totalAmount, new BigDecimal(100), new BigDecimal(5));
			if (nonGroceryAmount.compareTo(BigDecimal.ZERO) > 0) {
				nonGroceryAmount = discountUtility.calculateDiscount(nonGroceryAmount, userDiscount);
			}
	        BigDecimal finalAmount = (groceryAmount.add(nonGroceryAmount).subtract(billsDiscount));
	        
	        discountResponse.setTotalBill(totalAmount);
	        discountResponse.setUserDiscount(nonGroceryAmountRef.subtract(nonGroceryAmount));
	        discountResponse.setExtraDiscount(billsDiscount);
	        discountResponse.setFinalBill(finalAmount);
		}catch(Exception e) {
			throw new ApplicationRuntimeException(e.getLocalizedMessage());
		}
		log.info("Finished processing in DiscountServiceImpl:discountCalculation");
		return discountResponse;
	}
}
