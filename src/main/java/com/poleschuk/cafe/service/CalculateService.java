package com.poleschuk.cafe.service;

import java.math.BigDecimal;
import java.util.Map;

import com.poleschuk.cafe.model.entity.Menu;

/**
 * Interface for calculating total price of an order.
 */
public interface CalculateService {

    /**
     * Calculate total price for order.
     *
     * @param map          the map with products from menu
     * @return BigDecimal  calculated total price
     */
	BigDecimal calculateTotalPrice(Map<Menu, Integer> map);

}