package com.challenge.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.challenge.models.Item;
import com.challenge.models.ItemType;
import com.challenge.models.User;
import com.challenge.models.UserType;

@Component
public class DiscountUtility {
	
	@Value("${com.challenge.yearforDiscount}")
	private String YEARS_FOR_DISCOUNT;

	@Value("${com.challenge.employee.discount.percentage}")
    private String EMPLOYEE_DISCOUNT_PERCENTAGE;
	
	@Value("${com.challenge.affiliate.discount.percentage}")
    private String AFFILIATE_DISCOUNT_PERCENTAGE;
	
	@Value("${com.challenge.customer.discount.percentage}")
    private String CUSTOMER_DISCOUNT_PERCENTAGE;


    public BigDecimal calculateTotal(List<Item> items) {
        return items.stream().map(i -> i.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateTotalPerType(List<Item> items, ItemType type) {
        BigDecimal sum = new BigDecimal(0);

        if (type != null) {
            sum = items.stream().filter(i -> type.equals(i.getType())).map(i -> i.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        return sum;
    }

    public BigDecimal getUserDiscount(User user) {
        if (user == null) {
            throw new NullPointerException("User cannot be null");
        }

        BigDecimal discount = new BigDecimal(0);

        UserType type = user.getType();

        switch (type) {
            case EMPLOYEE:
                discount = new BigDecimal(EMPLOYEE_DISCOUNT_PERCENTAGE).setScale(2, RoundingMode.HALF_EVEN);
                break;

            case AFFILIATE:
                discount = new BigDecimal(AFFILIATE_DISCOUNT_PERCENTAGE).setScale(2, RoundingMode.HALF_EVEN);
                break;

            case CUSTOMER:
                if (isCustomerSince(user.getRegisterDate(), Long.valueOf(YEARS_FOR_DISCOUNT))) {
                    discount = new BigDecimal(CUSTOMER_DISCOUNT_PERCENTAGE).setScale(2, RoundingMode.HALF_EVEN);
                }
                break;

            default:
                break;
        }

        return discount;
    }

    public boolean isCustomerSince(LocalDate registeredDate, long years) {
        Period period = Period.between(registeredDate, LocalDate.now());
        return period.getYears() >= years;
    }

    public BigDecimal calculateBillsDiscount(BigDecimal totalAmount, BigDecimal amount, BigDecimal discountAmount) {
        int value = totalAmount.divide(amount).intValue();
        return discountAmount.multiply(new BigDecimal(value));
    }

    public BigDecimal calculateDiscount(BigDecimal amount, BigDecimal discount) {
        if (discount.doubleValue() > 1.0) {
            throw new IllegalArgumentException("Discount cannot be more than 100%");
        }

        BigDecimal x = amount.multiply(discount);
        return amount.subtract(x);
    }
}
