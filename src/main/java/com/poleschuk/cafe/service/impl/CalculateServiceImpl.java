package com.poleschuk.cafe.service.impl;


import com.poleschuk.cafe.model.entity.Menu;
import com.poleschuk.cafe.service.CalculateService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/**
 * CalculateServiceImpl class implements CalculateService interface and
 * contains business logic for calculating total price of an order.
 */
public class CalculateServiceImpl implements CalculateService {
    private static final CalculateService instance = new CalculateServiceImpl();
    private CalculateServiceImpl() {}

    /**
     * Get instance of CalculateServiceImpl class.
     *
     * @return the CalculateService object
     */
    public static CalculateService getInstance() {
        return instance;
    }

    @Override
	public BigDecimal calculateTotalPrice(Map<Menu, Integer> map) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Menu item: map.keySet()) {
            int numberProduct = map.get(item);
            BigDecimal itemPrice = item.getPrice();
            totalPrice = totalPrice.add(itemPrice.multiply(BigDecimal.valueOf(numberProduct)));
        }
        return totalPrice.setScale(2, RoundingMode.HALF_UP);
    }
}