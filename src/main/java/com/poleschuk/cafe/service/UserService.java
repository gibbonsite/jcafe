package com.poleschuk.cafe.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.CompleteOrder;
import com.poleschuk.cafe.model.entity.User;
import com.poleschuk.cafe.model.entity.UserRole;
import com.poleschuk.cafe.model.entity.UserState;

/**
 * Interface UserService works with users.
 */
public interface UserService {

    /**
     * Authenticate.
     *
     * @param login the login
     * @param password the password
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> authenticate(String login, String password) throws ServiceException;

    /**
     * Find all admins.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<User> findAllAdmins() throws ServiceException;

    /**
     * Find client sublist.
     *
     * @param pageSize the size of a page
     * @param offset the offset
     * @return the list
     * @throws ServiceException the service exception
     */
    List<User> findClientSublist(int pageSize, int offset) throws ServiceException;
    
    /**
     * Read client row count.
     *
     * @return the int
     * @throws ServiceException the service exception
     */
    int readClientRowCount() throws ServiceException;

    /**
     * User registration.
     *
     * @param mapData the map data
     * @param role the role
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean userRegistration(Map<String,String> mapData, UserRole role) throws ServiceException;
    
    /**
     * Update user profile.
     *
     * @param user the user
     * @param updateData the update data
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> updateUserProfile(User user, Map<String, String> updateData) throws ServiceException;

    /**
     * Change password by old password.
     *
     * @param map the map
     * @param user the user
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean changePasswordByOldPassword(Map<String, String> map, User user) throws ServiceException;

    /**
     * Change user state by id.
     *
     * @param state the state
     * @param id the id
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean changeUserStateById(UserState state, long id) throws ServiceException;

    /**
     * Delete admin.
     *
     * @param id the id
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean deleteAdmin(long id) throws ServiceException;

    /**
     * Top up balance.
     *
     * @param user the user
     * @param amount the amount
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean topUpBalance(User user, BigDecimal amount) throws ServiceException;
    
    /**
     * Decrease loyal score by order id.
     *
     * @param orderId the order id
     * @param amount the amount
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> decreaseLoyalScoreByOrderId(long orderId, BigDecimal amount) throws ServiceException;

}
