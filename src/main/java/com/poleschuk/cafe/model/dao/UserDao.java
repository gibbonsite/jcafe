package com.poleschuk.cafe.model.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.poleschuk.cafe.exception.DaoException;
import com.poleschuk.cafe.model.entity.User;

/**
 * UserDao interface consists of operations with user.
 */
public interface UserDao {

    /**
     * Find user by login and password.
     *
     * @param login    the login
     * @param password the password
     * @return the optional
     * @throws DaoException the dao exception
     */
	Optional<User> findUserByLoginAndPassword(String login, String password) throws DaoException;
	
    /**
     * Find password by login.
     *
     * @param login the login
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<String> findPasswordByLogin(String login) throws DaoException;

    /**
     * Update password by login.
     *
     * @param password the password
     * @param login    the login
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updatePasswordByLogin(String password, String login) throws DaoException;

    /**
     * Update user state.
     *
     * @param userId  the user id
     * @param stateId the state id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateUserState(long userId, long stateId) throws DaoException;

    /**
     * Find all admins.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<User> findAllAdmins() throws DaoException;

    /**
     * Find client sublist.
     *
     * @param pageSize the page size
     * @param offset the offset
     * @return the list
     * @throws DaoException the dao exception
     */
    List<User> findClientSublist(int pageSize, int offset) throws DaoException;

    /**
     * Read client row count.
     *
     * @return the int
     * @throws DaoException the dao exception
     */
    int readClientRowCount() throws DaoException;

    /**
     * Find user by login optional.
     *
     * @param login the login
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findUserByLogin(String login) throws DaoException;

    /**
     * Find user by phone number optional.
     *
     * @param phone the phone
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findUserByPhoneNumber(int phone) throws DaoException;

    /**
     * Find user by email optional.
     *
     * @param email the email
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findUserByEmail(String email) throws DaoException;

    /**
     * Find user by order optional.
     *
     * @param orderId the order id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findUserByOrder(long orderId) throws DaoException;

    /**
     * Update cash by id.
     *
     * @param id the id
     * @param amount the amount
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean updateCashById(long id, BigDecimal amount) throws DaoException;

    /**
     * Update loyal score by id.
     *
     * @param id the id
     * @param amount the amount
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean updateLoyalScoreById(long id, BigDecimal amount) throws DaoException;


    /**
     * Update accumulated cash by id.
     *
     * @param id the id
     * @param accumulatedCash the accumulated cash
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean updateAccumulatedCashById(long id, BigDecimal accumulatedCash) throws DaoException;
}
