package com.poleschuk.cafe.model.mapper.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.poleschuk.cafe.model.entity.LoyalScoreBonus;
import com.poleschuk.cafe.model.entity.User;
import com.poleschuk.cafe.model.entity.UserRole;
import com.poleschuk.cafe.model.entity.UserState;
import com.poleschuk.cafe.model.mapper.EntityRowMapper;

import static com.poleschuk.cafe.model.mapper.ColumnNames.*;

/**
 * UserMapper class extracts ResultSet row into User object.
 */
public class UserMapper implements EntityRowMapper<User> {
    private static final EntityRowMapper<User> instance = new UserMapper();

	public static EntityRowMapper<User> getInstance() {
		return instance;
	}

	private UserMapper() {
	}
	
    /**
     * Map row.
     *
     * @param resultSet the result set
     * @return the optional
     */
    @Override
    public Optional<User> mapRow(ResultSet resultSet) {
        User user = new User();
        Optional<User> optionalUser;
        try {
            user.setUserId(resultSet.getLong(USER_ID));
            user.setFirstName(resultSet.getString(FIRST_NAME));
            user.setLastName(resultSet.getString(LAST_NAME));
            user.setLogin(resultSet.getString(LOGIN));
            user.setEmail(resultSet.getString(EMAIL));
            user.setPhoneNumber(resultSet.getInt(PHONE_NUMBER));
            user.setBirthday(resultSet.getDate(BIRTHDAY).toLocalDate());
            user.setCash(resultSet.getBigDecimal(CASH));
            user.setLoyalScore(resultSet.getBigDecimal(USER_LOYAL_SCORE));
            user.setAccumulatedCash(resultSet.getBigDecimal(ACCUMULATED_CASH));
            user.setState(UserState.valueOf(resultSet.getString(USER_STATE)
                    .trim().toUpperCase()));
            user.setRole(UserRole.valueOf(resultSet.getString(USER_ROLE)
                    .trim().toUpperCase()));
            optionalUser = Optional.of(user);
        } catch (SQLException e) {
        	optionalUser = Optional.empty();
        }
        return optionalUser;
    }
}
