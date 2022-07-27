package com.poleschuk.cafe.model.dao;

import java.math.BigDecimal;
import java.util.List;

import com.poleschuk.cafe.exception.DaoException;
import com.poleschuk.cafe.model.entity.LoyalScoreBonus;

/**
 * BonusDao interface consists of operations with bonuses.
 */
public interface BonusDao {

    /**
     * Find all loyal score bonuses.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<LoyalScoreBonus> findAllLoyalScoreBonusesSortedByLoyalScore() throws DaoException;

	/**
	 * Find loyal score discount.
	 *
	 * @param loyalScore the loyal score
	 * @return BigDecimal, the discount
	 * @throws DaoException the dao exception
	 */
	BigDecimal findLoyalScoreDiscount(BigDecimal loyalScore) throws DaoException;

}
