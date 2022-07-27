package com.poleschuk.cafe.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.poleschuk.cafe.exception.DaoException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.dao.EntityTransaction;
import com.poleschuk.cafe.model.dao.impl.BonusDaoImpl;
import com.poleschuk.cafe.model.entity.LoyalScoreBonus;
import com.poleschuk.cafe.service.BonusService;
import com.poleschuk.cafe.validator.Validator;
import com.poleschuk.cafe.validator.impl.ValidatorImpl;

/**
 * BonusServiceImpl class implements BonusService interface and
 * contains business logic for discount calculations.
 */
public class BonusServiceImpl implements BonusService {
    private static final Logger logger = LogManager.getLogger();
    private static final BonusService instance = new BonusServiceImpl();
    private static final BigDecimal MAX_DISCOUNT = BigDecimal.valueOf(3).divide(BigDecimal.TEN);
    private final Validator validator = ValidatorImpl.getInstance();

	public static BonusService getInstance() {
		return instance;
	}
    
	@Override
	public List<LoyalScoreBonus> getLoyalScoreBonusesSortedByLoyalScore() throws ServiceException {
        BonusDaoImpl bonusDao = new BonusDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(bonusDao);
        try {
            return bonusDao.findAllLoyalScoreBonusesSortedByLoyalScore();
        } catch (DaoException e) {
            throw new ServiceException("Exception in a getLoyalScoreBonuses method. ", e);
        }finally {
            transaction.end();
        }
	}

	@Override
	public BigDecimal getLoyalScoreDiscount(BigDecimal loyalScore) throws ServiceException {
        BonusDaoImpl bonusDao = new BonusDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(bonusDao);
        try {
            return bonusDao.findLoyalScoreDiscount(loyalScore);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a getLoyalScoreBonuses method. ", e);
        }finally {
            transaction.end();
        }

	}

	@Override
	public BigDecimal getDiscountedPrice(BigDecimal price, BigDecimal loyalScore,
			BigDecimal accumulatedCash) throws ServiceException {
		BigDecimal loyalScoreDiscount = getLoyalScoreDiscount(loyalScore);
		BigDecimal accumulativeDiscount = BigDecimal.valueOf(accumulatedCash.doubleValue() / price.doubleValue())
				.min(MAX_DISCOUNT);
		return price.subtract(price.multiply(accumulativeDiscount.max(loyalScoreDiscount)));
	}

	@Override
	public BigDecimal getSuggestedDiscount(BigDecimal price, BigDecimal loyalScore, BigDecimal accumulatedCash)
			throws ServiceException {
		BigDecimal loyalScoreDiscount = getLoyalScoreDiscount(loyalScore);
		BigDecimal accumulativeDiscount = BigDecimal.valueOf(accumulatedCash.doubleValue() / price.doubleValue())
				.min(MAX_DISCOUNT);
		return accumulativeDiscount.max(loyalScoreDiscount);
	}

	@Override
	public BigDecimal getAccumulatedCashDecrease(BigDecimal price, BigDecimal loyalScore, BigDecimal accumulatedCash)
			throws ServiceException {
		BigDecimal loyalScoreDiscount = getLoyalScoreDiscount(loyalScore);
		BigDecimal accumulativeDiscount = BigDecimal.valueOf(accumulatedCash.doubleValue() / price.doubleValue())
				.min(MAX_DISCOUNT);
		if (accumulativeDiscount.compareTo(loyalScoreDiscount) == 1) {
			return accumulativeDiscount.subtract(loyalScoreDiscount).multiply(price);
		}
		return BigDecimal.ZERO;
	}

	
}
