package com.poleschuk.cafe.model.dao.impl;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.poleschuk.cafe.exception.DaoException;
import com.poleschuk.cafe.model.dao.BaseDao;
import com.poleschuk.cafe.model.dao.BonusDao;
import com.poleschuk.cafe.model.entity.LoyalScoreBonus;
import com.poleschuk.cafe.model.mapper.EntityRowMapper;
import com.poleschuk.cafe.model.mapper.impl.LoyalScoreBonusMapper;

public class BonusDaoImpl extends BaseDao<LoyalScoreBonus> implements BonusDao {
    private static final Logger logger = LogManager.getLogger();
    private static final int ONE_UPDATE = 1;
    private static final String SQL_SELECT_ALL_LOYAL_SCORE_BONUSES = """
            SELECT loyal_score, discount FROM loyal_score_bonuses ORDER BY loyal_score ASC""";
    
    private static final String SQL_SELECT_LOYAL_SCORE_BONUS_BY_LOYAL_SCORE = """
            SELECT loyal_score, discount FROM loyal_score_bonuses WHERE loyal_score <= (?)
            ORDER BY discount DESC LIMIT 1""";
    
    @Override
    public List<LoyalScoreBonus> findAllLoyalScoreBonusesSortedByLoyalScore() throws DaoException {
    	List<LoyalScoreBonus> loyalScoreBonusList = new ArrayList<>();
    	EntityRowMapper<LoyalScoreBonus> mapper = LoyalScoreBonusMapper.getInstance();
	    try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_ALL_LOYAL_SCORE_BONUSES);
	        ResultSet resultSet = statement.executeQuery()) {
	        while (resultSet.next()) {
	            Optional<LoyalScoreBonus> optionalLoyalScoreBonus = mapper.mapRow(resultSet);
	            if (optionalLoyalScoreBonus.isPresent()) {
	                loyalScoreBonusList.add(optionalLoyalScoreBonus.get());
	                logger.log(Level.INFO, "Present");
	            }
	        }
	    } catch (SQLException e) {
	        logger.log(Level.ERROR, "Exception while findAllLoyalScoreBonuses method: " + e.getMessage());
	        throw new DaoException("Exception while findAllLoyalScoreBonuses method ", e);
	    }
	    return loyalScoreBonusList;
    }

    @Override
	public BigDecimal findLoyalScoreDiscount(BigDecimal loyalScore) throws DaoException {
    	EntityRowMapper<LoyalScoreBonus> mapper = LoyalScoreBonusMapper.getInstance();
	    try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_LOYAL_SCORE_BONUS_BY_LOYAL_SCORE)) {
	    	statement.setBigDecimal(1, loyalScore);
	        try (ResultSet resultSet = statement.executeQuery()) {
		        if (resultSet.next()) {
		            Optional<LoyalScoreBonus> optionalLoyalScoreBonus = mapper.mapRow(resultSet);
		            if (optionalLoyalScoreBonus.isPresent()) {
		                return optionalLoyalScoreBonus
		                		.get()
		                		.getDiscount();
		            }
		        }
	        }
	        logger.log(Level.ERROR, "Loyal score bonus is not present");
	    	throw new DaoException("loyal score bonus is not present");
	    } catch (SQLException e) {
	        logger.log(Level.ERROR, "Exception while findAllLoyalScoreBonuses method: " + e.getMessage());
	        throw new DaoException("Exception while findAllLoyalScoreBonuses method ", e);
	    }
    }

	@Override
	public boolean insert(LoyalScoreBonus t) throws DaoException {
		throw new UnsupportedOperationException("insert method is unsupported");
	}

	@Override
	public boolean delete(long id) throws DaoException {
		throw new UnsupportedOperationException("delete method is unsupported");
	}

	@Override
	public Optional<LoyalScoreBonus> findEntityById(long id) throws DaoException {
		throw new UnsupportedOperationException("findEntityById method is unsupported");
	}

	@Override
	public List<LoyalScoreBonus> findAll() throws DaoException {
		throw new UnsupportedOperationException("find all method is unsupported");
	}

	@Override
	public Optional<LoyalScoreBonus> update(LoyalScoreBonus t) throws DaoException {
		throw new UnsupportedOperationException("update method is unsupported");
	}
}