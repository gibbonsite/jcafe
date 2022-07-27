package com.poleschuk.cafe.model.dao.impl;

import org.apache.logging.log4j.Level;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.poleschuk.cafe.exception.DaoException;
import com.poleschuk.cafe.model.dao.BaseDao;
import com.poleschuk.cafe.model.dao.UserDao;
import com.poleschuk.cafe.model.entity.User;
import com.poleschuk.cafe.model.mapper.EntityRowMapper;
import com.poleschuk.cafe.model.mapper.impl.UserMapper;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * UserDaoImpl class implements UserDao interface and executes requests
 * to the DB.
 */
public class UserDaoImpl extends BaseDao<User> implements UserDao {

    private static final Logger logger = LogManager.getLogger();
	private static final int ONE_UPDATE = 1;
	private static final String PASSWORD = "password";
    private static final String SQL_SELECT_CLIENT_SUBLIST = """
            SELECT user_id, first_name, last_name, login, email, phone, birthday, cash,
            loyal_score, accumulated_cash, state, role FROM users
            JOIN user_states ON user_states.user_state_id = users.user_state_id
            JOIN user_roles ON user_roles.user_role_id = users.user_role_id
            WHERE users.user_role_id = 1 LIMIT ? OFFSET ?""";
    private static final String SQL_SELECT_CLIENT_ROW_COUNT = """
            SELECT COUNT(*) FROM users WHERE user_role_id = 1""";
    private static final String SQL_SELECT_ALL_ADMINS = """
            SELECT user_id, first_name, last_name, login, email, phone, birthday, cash,
            loyal_score, accumulated_cash, state, role FROM users
            JOIN user_states ON user_states.user_state_id = users.user_state_id
            JOIN user_roles ON user_roles.user_role_id = users.user_role_id
            WHERE role = 'ADMIN'""";
    private static final String SQL_INSERT_NEW_USER = """ 
            INSERT INTO users(first_name, last_name, login, password, email, phone, birthday, cash,
            loyal_score, accumulated_cash, user_state_id, user_role_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";
    private static final String SQL_SELECT_USER_BY_ID = """
            SELECT user_id, first_name, last_name, login, email, phone, birthday, cash,
            loyal_score, accumulated_cash, state, role FROM users
            JOIN user_states ON user_states.user_state_id = users.user_state_id
            JOIN user_roles ON user_roles.user_role_id = users.user_role_id
            WHERE user_id = (?)""";
    private static final String SQL_SELECT_USER_BY_LOGIN = """
            SELECT users.user_id, first_name, last_name, login, email, phone, birthday, cash,
            loyal_score, accumulated_cash, state, role FROM users
            JOIN user_states ON user_states.user_state_id = users.user_state_id
            JOIN user_roles ON user_roles.user_role_id = users.user_role_id WHERE login = (?)""";
    private static final String SQL_SELECT_USER_BY_PHONE_NUMBER = """
            SELECT users.user_id, first_name, last_name, login, email, phone, birthday, cash,
            loyal_score, accumulated_cash, state, role FROM users
            JOIN user_states ON user_states.user_state_id = users.user_state_id
            JOIN user_roles ON user_roles.user_role_id = users.user_role_id WHERE phone = (?)""";
    private static final String SQL_SELECT_USER_BY_EMAIL = """
            SELECT users.user_id, first_name, last_name, login, email, phone, birthday, cash,
            loyal_score, accumulated_cash, state, role FROM users
            JOIN user_states ON user_states.user_state_id = users.user_state_id
            JOIN user_roles ON user_roles.user_role_id = users.user_role_id WHERE email = (?)""";
    private static final String SQL_SELECT_USER_BY_ORDER_ID = """
            SELECT users.user_id, first_name, last_name, login, email, phone, birthday, cash,
            loyal_score, users.accumulated_cash, state, role FROM users
            JOIN user_states ON user_states.user_state_id = users.user_state_id
            JOIN user_roles ON user_roles.user_role_id = users.user_role_id
            JOIN orders ON users.user_id = orders.user_id
            WHERE order_id = (?)""";
    private static final String SQL_SELECT_USER_BY_LOGIN_AND_PASSWORD = """
            SELECT users.user_id, first_name, last_name, login, password, email, phone, birthday, cash,
            loyal_score, accumulated_cash, state, role FROM users
            JOIN user_states ON user_states.user_state_id = users.user_state_id
            JOIN user_roles ON user_roles.user_role_id = users.user_role_id WHERE login = (?) AND password = (?)""";
    public static final String SQL_SELECT_PASSWORD_BY_LOGIN = """
            SELECT password FROM users WHERE login = (?)""";
    private static final String SQL_UPDATE_PASSWORD_BY_LOGIN = """
            UPDATE users SET password = (?) WHERE login = (?)""";
    private static final String SQL_UPDATE_USER_STATE_BY_ID = """
            UPDATE users SET user_state_id = (?) WHERE user_id = (?)""";
    private static final String SQL_UPDATE_USER = """
            UPDATE users SET first_name = (?), last_name = (?), login = (?), email = (?),
            phone = (?), birthday = (?), cash = (?), loyal_score = (?), accumulated_cash = (?), user_state_id = (?),
            user_role_id = (?) WHERE user_id = (?)""";
    private static final String SQL_DELETE_USER_BY_ID = """
            DELETE FROM users WHERE user_id = (?)""";
    private static final String SQL_UPDATE_CASH_BY_ID = """
            UPDATE users SET cash = (?) WHERE user_id = (?)""";
    private static final String SQL_UPDATE_LOYAL_SCORE_BY_ID = """
            UPDATE users SET loyal_score = (?) WHERE user_id = (?)""";
    private static final String SQL_UPDATE_ACCUMULATED_CASH_BY_ID = """
            UPDATE users SET accumulated_cash = (?) WHERE user_id = (?)""";
    
    
    @Override
    public boolean insert(User entity) throws DaoException {
        try (PreparedStatement statement = this.proxyConnection
        		.prepareStatement(SQL_INSERT_NEW_USER)) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getLogin());
            statement.setString(4, entity.getPassword());
            statement.setString(5, entity.getEmail());
            statement.setInt(6, entity.getPhoneNumber());
            statement.setDate(7, Date.valueOf(entity.getBirthday().toString()));
            statement.setBigDecimal(8, entity.getCash());
            statement.setBigDecimal(9, entity.getLoyalScore());
            statement.setBigDecimal(10, entity.getAccumulatedCash()); 
            statement.setLong(11, entity.getState().ordinal() + 1);
            statement.setLong(12, entity.getRole().ordinal() + 1);
            logger.log(Level.INFO, "The new row: " + entity);
            return statement.executeUpdate() == ONE_UPDATE;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while create user method: " + e.getMessage());
            throw new DaoException("Exception while create user method ", e);
        }
    }

    @Override
    public List<User> findClientSublist(int pageSize, int offset) throws DaoException {
        List<User> userList = new ArrayList<>();
        EntityRowMapper<User> mapper = UserMapper.getInstance();
        try (PreparedStatement statement = this.proxyConnection
        		.prepareStatement(SQL_SELECT_CLIENT_SUBLIST)) {
            statement.setInt(1, pageSize);
            statement.setInt(2, offset);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Optional<User> optionalUser = mapper.mapRow(resultSet);
                    optionalUser.ifPresent(userList::add);
                }
            }
            logger.log(Level.INFO, "List: " + userList);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in a findClientSublist method: " + e.getMessage());
            throw new DaoException("Exception in a findClientSublist method", e);
        }
        return userList;
    }

    @Override
    public int readClientRowCount() throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_CLIENT_ROW_COUNT)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? resultSet.getInt(1) : 0;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in a readClientRowCount method: " + e.getMessage());
            throw new DaoException("Exception in a readClientRowCount method. ", e);
        }
    }

    @Override
    public List<User> findAllAdmins() throws DaoException {
        EntityRowMapper<User> mapper = UserMapper.getInstance();
        List<User> userList = new ArrayList<>();
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_ALL_ADMINS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Optional<User> optionalUser = mapper.mapRow(resultSet);
                    optionalUser.ifPresent(userList::add);
                }
            }
            logger.log(Level.INFO, "List: " + userList);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while find all admins method ");
            throw new DaoException("Exception in a findAllAdmins method", e);
        }
        return userList;
    }

    @Override
    public Optional<User> findEntityById(long id) throws DaoException {
        EntityRowMapper<User> mapper = UserMapper.getInstance();
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_USER_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapper.mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while find user by id method ");
            throw new DaoException("Exception while find user by id method ", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DaoException {
        EntityRowMapper<User> mapper = UserMapper.getInstance();
        Optional<User> user = Optional.empty();
        try (PreparedStatement statement = this.proxyConnection
        		.prepareStatement(SQL_SELECT_USER_BY_LOGIN)) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = mapper.mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while find user by login method: " + e.getMessage());
            throw new DaoException("Exception while findUserByLogin method ", e);
        }
        return user;
    }

    @Override
    public Optional<User> findUserByPhoneNumber(int phone) throws DaoException {
        EntityRowMapper<User> mapper = UserMapper.getInstance();
        Optional<User> user = Optional.empty();
        try (PreparedStatement statement = this.proxyConnection
        		.prepareStatement(SQL_SELECT_USER_BY_PHONE_NUMBER)) {
            statement.setInt(1, phone);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = mapper.mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while find user by phone number method: " + e.getMessage());
            throw new DaoException("Exception while find user by phone number method ", e);
        }
        return user;
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws DaoException {
        EntityRowMapper<User> mapper = UserMapper.getInstance();
        Optional<User> user = Optional.empty();
        try (PreparedStatement statement = this.proxyConnection
        		.prepareStatement(SQL_SELECT_USER_BY_EMAIL)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = mapper.mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while find user by email method: " + e.getMessage());
            throw new DaoException("Exception while find user by email method ", e);
        }
        return user;
    }

    @Override
    public Optional<User> findUserByOrder(long orderId) throws DaoException {
        EntityRowMapper<User> mapper = UserMapper.getInstance();
        Optional<User> user = Optional.empty();
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_USER_BY_ORDER_ID)) {
            statement.setLong(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = mapper.mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while find user by order method: " + e.getMessage());
            throw new DaoException("Exception while findUserByOrder method ", e);
        }
        return user;
    }

    @Override
    public Optional<User> findUserByLoginAndPassword(String login, String password) throws DaoException {
        EntityRowMapper<User> mapper = UserMapper.getInstance();
        Optional<User> user = Optional.empty();
        try (PreparedStatement statement = this.proxyConnection
        		.prepareStatement(SQL_SELECT_USER_BY_LOGIN_AND_PASSWORD)) {
            statement.setString(1, login);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = mapper.mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while find user by login and password method: " + e.getMessage());
            throw new DaoException("Exception while find user by login and password method ", e);
        }
        logger.log(Level.INFO, "findUserByLoginAndPassword dao method");
        return user;
    }

    @Override
    public Optional<String> findPasswordByLogin(String login) throws DaoException {
        Optional<String> password = Optional.empty();
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_PASSWORD_BY_LOGIN)) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    password = Optional.of(resultSet.getString(PASSWORD));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while find password by login method: " + e.getMessage());
            throw new DaoException("Exception while findPasswordByLogin method ", e);
        }
        return password;
    }

    @Override
    public Optional<User> update(User entity) throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_UPDATE_USER)) {
            Optional<User> user = findEntityById(entity.getUserId());
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getLogin());
            statement.setString(4, entity.getEmail());
            statement.setInt(5, entity.getPhoneNumber());
            statement.setDate(6, Date.valueOf(entity.getBirthday().toString()));
            statement.setBigDecimal(7, entity.getCash());
            statement.setBigDecimal(8, entity.getLoyalScore());
            statement.setBigDecimal(9, entity.getAccumulatedCash());
            statement.setLong(10, entity.getState().ordinal() + 1);
            statement.setLong(11, entity.getRole().ordinal() + 1);
            statement.setLong(12, entity.getUserId());
            return statement.executeUpdate() == ONE_UPDATE ? user : Optional.empty();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while update user method ");
            throw new DaoException("Exception while update user method ", e);
        }
    }

    @Override
    public boolean updatePasswordByLogin(String password, String login) throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_UPDATE_PASSWORD_BY_LOGIN)) {
            statement.setString(1, password);
            statement.setString(2, login);
            return statement.executeUpdate() == ONE_UPDATE;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while update password by login method: " + e.getMessage());
            throw new DaoException("Exception while updatePasswordByLogin method ", e);
        }
    }

    @Override
    public boolean updateUserState(long userId, long stateId) throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_UPDATE_USER_STATE_BY_ID)) {
            statement.setLong(1, stateId);
            statement.setLong(2, userId);
            return statement.executeUpdate() == ONE_UPDATE;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while update user state method ");
            throw new DaoException("Exception while update user state method ", e);
        }
    }

    @Override
    public boolean delete(long id) throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_DELETE_USER_BY_ID)) {
            statement.setLong(1, id);
            return statement.executeUpdate() == ONE_UPDATE;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while delete user by id method: " + e.getMessage());
            throw new DaoException("Exception while delete user by id method ", e);
        }
    }

	@Override
	public List<User> findAll() throws DaoException {
		throw new UnsupportedOperationException("user find all is not supported");
	}

	@Override
    public boolean updateCashById(long id, BigDecimal cash) throws DaoException{
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_UPDATE_CASH_BY_ID)) {
            statement.setBigDecimal(1, cash);
            statement.setLong(2, id);
            return statement.executeUpdate() == ONE_UPDATE;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while update cash by id method: " + e.getMessage());
            throw new DaoException("Exception while update cash by id method ", e);
        }
	}

	@Override
    public boolean updateLoyalScoreById(long id, BigDecimal loyalScoreBonus) throws DaoException{
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_UPDATE_LOYAL_SCORE_BY_ID)) {
            statement.setBigDecimal(1, loyalScoreBonus);
            statement.setLong(2, id);
            return statement.executeUpdate() == ONE_UPDATE;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while update cash by id method: " + e.getMessage());
            throw new DaoException("Exception while update cash by id method ", e);
        }
	}

	@Override
    public boolean updateAccumulatedCashById(long id, BigDecimal accumulatedCash) throws DaoException{
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_UPDATE_ACCUMULATED_CASH_BY_ID)) {
            statement.setBigDecimal(1, accumulatedCash);
            statement.setLong(2, id);
            return statement.executeUpdate() == ONE_UPDATE;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while update accumulated cash by id method: " + e.getMessage());
            throw new DaoException("Exception while update accumulated cash by id method ", e);
        }
	}
}
