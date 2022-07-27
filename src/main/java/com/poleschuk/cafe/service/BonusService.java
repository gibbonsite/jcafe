package com.poleschuk.cafe.service;

import java.math.BigDecimal;
import java.util.List;

import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.LoyalScoreBonus;

/**
 * Interface BonusService provides functions to work with bonuses.
 */
public interface BonusService {

	/**
	 * Gets loyal score bonuses sorted by loyal score.
	 *
	 * @return list of loyal score bonuses
	 * @throws ServiceException the service exception
	 */
	List<LoyalScoreBonus> getLoyalScoreBonusesSortedByLoyalScore() throws ServiceException;

	/**
	 * Gets the loyal score discount.
	 *
	 * @param loyalScore the loyal score
	 * @return the loyal score discount
	 * @throws ServiceException the service exception
	 */
	BigDecimal getLoyalScoreDiscount(BigDecimal loyalScore) throws ServiceException;

	/**
	 * Gets discounted price.
	 *
	 * @param price the price
	 * @param loyalScore the loyal score
	 * @param accumulatedCash accumulated user cash
	 * @return discounted price
	 * @throws ServiceException the service exception
	 */
	BigDecimal getDiscountedPrice(BigDecimal price, BigDecimal loyalScore, BigDecimal accumulatedCash)
			throws ServiceException;

	/**
	 * Gets suggested discount.
	 *
	 * @param price the price
	 * @param loyalScore the loyal score
	 * @param accumulatedCash the accumulated cash
	 * @return the suggested discount
	 * @throws ServiceException the service exception
	 */
	BigDecimal getSuggestedDiscount(BigDecimal price, BigDecimal loyalScore, BigDecimal accumulatedCash)
			throws ServiceException;

	/**
	 * Gets the accumulated cash decrease.
	 *
	 * @param price the price
	 * @param loyalScore the loyal score
	 * @param accumulatedCash the accumulated cash
	 * @return the accumulated cash decrease
	 * @throws ServiceException the service exception
	 */
	BigDecimal getAccumulatedCashDecrease(BigDecimal price, BigDecimal loyalScore, BigDecimal accumulatedCash)
			throws ServiceException;
}