package com.poleschuk.cafe.model.mapper.impl;

import static com.poleschuk.cafe.model.mapper.ColumnNames.DISCOUNT;
import static com.poleschuk.cafe.model.mapper.ColumnNames.LOYAL_SCORE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.poleschuk.cafe.exception.DaoException;
import com.poleschuk.cafe.model.entity.LoyalScoreBonus;
import com.poleschuk.cafe.model.mapper.EntityRowMapper;

/**
 * LoyalScoreBonusMapper class extracts ResultSet row into LoyalScoreBonus object.
 */
public class LoyalScoreBonusMapper implements EntityRowMapper<LoyalScoreBonus>{
    private static final Logger logger = LogManager.getLogger();
    private static final LoyalScoreBonusMapper instance = new LoyalScoreBonusMapper();
    

	public static EntityRowMapper<LoyalScoreBonus> getInstance() {
		return instance;
	}

	private LoyalScoreBonusMapper() {
	}
	
    @Override
    public Optional<LoyalScoreBonus> mapRow(ResultSet resultSet) throws DaoException {
    	LoyalScoreBonus loyalScoreBonus = new LoyalScoreBonus();
        Optional<LoyalScoreBonus> optionalLoyalScoreBonus;
        try{
            loyalScoreBonus.setLoyalScore(resultSet.getBigDecimal(LOYAL_SCORE));
            loyalScoreBonus.setDiscount(resultSet.getBigDecimal(DISCOUNT));
            optionalLoyalScoreBonus = Optional.of(loyalScoreBonus);
        } catch (SQLException e) {
            logger.log(Level.WARN, "Not found loyal score bonus item! ");
            optionalLoyalScoreBonus = Optional.empty();
        }
        return optionalLoyalScoreBonus;
    }
}
