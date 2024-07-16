package com.challenge;


import com.challenge.helper.DiscountUtility;
import com.challenge.models.*;
import com.challenge.services.DiscountService;
import com.challenge.services.DiscountServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;


public class DiscountTest {

    @Test
    public void testCalculateTotal_GroceriesOnly() {
        List<Item> items = new ArrayList<Item>();
        items.add(new Item(ItemType.GROCERY, new BigDecimal(100.0)));
        items.add(new Item(ItemType.GROCERY, new BigDecimal(100.0)));
        items.add(new Item(ItemType.GROCERY, new BigDecimal(100.0)));

        DiscountUtility helper = new DiscountUtility();
        BigDecimal total = helper.calculateTotalPerType(items, ItemType.GROCERY);
        assertEquals(300.00, total.doubleValue(), 0);
    }

    @Test
    public void testCalculateTotalNonGroceriesOnly() {
        List<Item> items = new ArrayList<Item>();
        items.add(new Item(ItemType.CLOTHES, new BigDecimal(100.0)));
        items.add(new Item(ItemType.OTHER, new BigDecimal(100.0)));
        items.add(new Item(ItemType.TECHNOLOGY, new BigDecimal(100.0)));

        DiscountUtility helper = new DiscountUtility();
        BigDecimal total = helper.calculateTotal(items);
        assertEquals(300.00, total.doubleValue(), 0);
    }

    @Test
    public void testCalculateTotalMix() {
        List<Item> items = new ArrayList<Item>();
        items.add(new Item(ItemType.CLOTHES, new BigDecimal(100.0)));
        items.add(new Item(ItemType.OTHER, new BigDecimal(100.0)));
        items.add(new Item(ItemType.TECHNOLOGY, new BigDecimal(100.0)));
        items.add(new Item(ItemType.GROCERY, new BigDecimal(100.0)));
        items.add(new Item(ItemType.GROCERY, new BigDecimal(100.0)));

        DiscountUtility helper = new DiscountUtility();
        BigDecimal total = helper.calculateTotalPerType(items, ItemType.GROCERY);
        assertEquals(200.00, total.doubleValue(), 0);
    }

    @Test
    public void testCalculateTotal_error() {
        DiscountUtility helper = new DiscountUtility();
        when(helper.getUserDiscount(null)).thenThrow(new NullPointerException());
        assertThrows(NullPointerException.class, ()->helper.getUserDiscount(null));
    }

    @Test
    public void testCalculateDiscount_10pct() {
        DiscountUtility helper = new DiscountUtility();
        BigDecimal total = helper.calculateDiscount(new BigDecimal(1000), new BigDecimal(0.1));
        assertEquals(900.00, total.doubleValue(), 0);
    }

    @Test
    public void testCalculateDiscount_50pct() {
        DiscountUtility helper = new DiscountUtility();
        BigDecimal total = helper.calculateDiscount(new BigDecimal(1000), new BigDecimal(0.5));
        assertEquals(500.00, total.doubleValue(), 0);
    }

    @Test
    public void testCalculateDiscount_0pct() {
        DiscountUtility helper = new DiscountUtility();
        BigDecimal total = helper.calculateDiscount(new BigDecimal(1000),  new BigDecimal(0.0));
        assertEquals(1000.00, total.doubleValue(), 0);
    }

    @Test
    public void testCalculateDiscount_100pct() {
        DiscountUtility helper = new DiscountUtility();
        BigDecimal total = helper.calculateDiscount(new BigDecimal(1000),  new BigDecimal(1.0));
        assertEquals(0.0, total.doubleValue(), 0);
    }

    @Test
    public void testCalculateDiscount_error() {
        DiscountUtility helper = new DiscountUtility();
        when(helper.calculateDiscount(any(),any())).thenThrow(new IllegalArgumentException());
        assertThrows(IllegalArgumentException.class, ()->helper.calculateDiscount(new BigDecimal(1000),  new BigDecimal(2.0)));
    }

    @Test
    public void testGetUserSpecificDiscount_affiliate() {
        User user = new User(UserType.AFFILIATE, LocalDate.now());
        DiscountUtility helper = new DiscountUtility();
        BigDecimal discount = helper.getUserDiscount(user);
        assertEquals(0.1, discount.doubleValue(), 0);
    }

    @Test
    public void testGetUserSpecificDiscount_employee() {
        User user = new User(UserType.EMPLOYEE, LocalDate.now());
        DiscountUtility helper = new DiscountUtility();
        BigDecimal discount = helper.getUserDiscount(user);
        assertEquals(0.3, discount.doubleValue(), 0);
    }

    @Test
    public void testGetUserSpecificDiscount_customer_old() {
        LocalDate joinDate = LocalDate.of(2016, 2, 23);
        User user = new User(UserType.CUSTOMER, joinDate);
        DiscountUtility helper = new DiscountUtility();
        BigDecimal discount = helper.getUserDiscount(user);
        assertEquals(0.05, discount.doubleValue(), 0);
    }

    @Test
    public void testGetUserSpecificDiscount_customer_new() {
        User user = new User(UserType.CUSTOMER, LocalDate.now());
        DiscountUtility helper = new DiscountUtility();
        BigDecimal discount = helper.getUserDiscount(user);
        assertEquals(0.0, discount.doubleValue(), 0);
    }

    @Test
    public void testGetUserSpecificDiscount_customer_null_user() {
        DiscountUtility helper = new DiscountUtility();
        when(helper.getUserDiscount(null)).thenThrow(new NullPointerException());
        assertThrows(NullPointerException.class, ()->helper.getUserDiscount(null));
    }

    @Test
    public void testIsCustomerSince() {
        DiscountUtility helper = new DiscountUtility();
        LocalDate joinDate = LocalDate.now();
        boolean isTwoYearsJoined = helper.isCustomerSince(joinDate, 2);
        assertFalse(isTwoYearsJoined);
    }

    @Test
    public void testIsCustomerSince_1year() {
        DiscountUtility helper = new DiscountUtility();
        LocalDate joinDate = LocalDate.now().minusYears(1);
        boolean isTwoYearsJoined = helper.isCustomerSince(joinDate, 2);
        assertFalse(isTwoYearsJoined);
    }

    @Test
    public void testIsCustomerSince_2years() {
        DiscountUtility helper = new DiscountUtility();
        LocalDate joinDate = LocalDate.now().minusYears(2);
        boolean isTwoYearsJoined = helper.isCustomerSince(joinDate, 2);
        assertTrue(isTwoYearsJoined);
    }

    @Test
    public void testIsCustomerSince_3years() {
        DiscountUtility helper = new DiscountUtility();
        LocalDate joinDate = LocalDate.now().minusYears(3);
        boolean isTwoYearsJoined = helper.isCustomerSince(joinDate, 2);
        assertTrue(isTwoYearsJoined);
    }

    @Test
    public void testCalculateBillsDiscount() {
        DiscountUtility helper = new DiscountUtility();
        BigDecimal amount = helper.calculateBillsDiscount(new BigDecimal(1000),  new BigDecimal(100),  new BigDecimal(5));
        assertEquals(50, amount.doubleValue(), 0);
    }

    @Test
    public void testCalculateBillsDiscount_2() {
        DiscountUtility helper = new DiscountUtility();
        BigDecimal amount = helper.calculateBillsDiscount(new BigDecimal(1000),  new BigDecimal(50),  new BigDecimal(5));
        assertEquals(100, amount.doubleValue(), 0);
    }

    @Test
    public void testCalculateBillsDiscount_3() {
        DiscountUtility helper = new DiscountUtility();
        BigDecimal amount = helper.calculateBillsDiscount( new BigDecimal(5632), new BigDecimal(100), new BigDecimal(5));
        assertEquals(280, amount.doubleValue(), 0);
    }

    @Test
	public void testDiscountServiceCalculate() {
		List<Item> items = new ArrayList<Item>();
		items.add(new Item(ItemType.GROCERY, new BigDecimal(50.0)));
		items.add(new Item(ItemType.TECHNOLOGY, new BigDecimal(200.0)));
		items.add(new Item(ItemType.GROCERY, new BigDecimal(10.0)));

		Bill bill = new Bill();
		bill.setItems(items);

		DiscountService discountService = new DiscountServiceImpl();

		discountService.discountCalculation(new User(UserType.CUSTOMER, LocalDate.now()), bill);
		DiscountUtility helper = new DiscountUtility();
		BigDecimal amount = helper.calculateBillsDiscount(new BigDecimal(5632), new BigDecimal(100), new BigDecimal(5));
		assertEquals(280, amount.doubleValue(), 0);
	}

}
