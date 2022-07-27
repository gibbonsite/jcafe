package com.poleschuk.cafe.service;


import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.CompleteOrder;
import com.poleschuk.cafe.model.entity.Menu;
import com.poleschuk.cafe.model.entity.Order;
import com.poleschuk.cafe.model.entity.OrderScoreReport;
import com.poleschuk.cafe.model.entity.OrderState;
import com.poleschuk.cafe.model.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interface OrderService provides functions to work with orders.
 */
public interface OrderService {

    /**
     * Creates the order.
     *
     * @param productMap the product map
     * @param orderInfo the order info
     * @param user the user
     * @param totalPrice the total price
     * @param discountedPrice the discounted price
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean createOrder(Map<Menu, Integer> productMap, Map<String, String> orderInfo, User user,
    		BigDecimal totalPrice, BigDecimal discountedPrice) throws ServiceException;

    /**
     * Find all user orders.
     *
     * @param user the user
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Order> findAllUserOrders(User user) throws ServiceException;

    /**
     * Find all orders.
     *
     * @param pageSize the size of a page
     * @param offset the offset
     * @return the list
     * @throws ServiceException the service exception
     */
    List<CompleteOrder> findOrderSublist(int pageSize, int offset) throws ServiceException;

    /**
     * Find cancelled orders.
     *
     * @param pageSize the size of a page
     * @param offset the offset
     * @return the list
     * @throws ServiceException the service exception
     */
    List<CompleteOrder> findCancelledOrderSublist(int pageSize, int offset) throws ServiceException;
    
    /**
     * Read row count.
     *
     * @return the int
     * @throws ServiceException the service exception
     */
    int readRowCount() throws ServiceException;
    
    /**
     * Read cancelled order row count.
     *
     * @return the int
     * @throws ServiceException the service exception
     */
    int readCancelledOrderRowCount() throws ServiceException;

    /**
     * Find all order score reports.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<OrderScoreReport> findAllOrderScoreReports() throws ServiceException;

    /**
     * Change order state by id.
     *
     * @param orderId the order id
     * @param state the state
     * @param userId the user id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> changeOrderStateById(long orderId, String state, long userId) throws ServiceException;

    /**
     * Find order score and report.
     *
     * @param orderId the order id
     * @param score the score
     * @param report the report
     * @return the order score report
     * @throws ServiceException the service exception
     */
    boolean changeOrderScoreReport(long orderId, String score, String report) throws ServiceException;

    /**
     * Delete old orders.
     *
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean deleteOldOrders() throws ServiceException;

    /**
     * Find all sorted orders by date.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Order> findAllSortedOrdersByDate() throws ServiceException;
    
    /**
     * Find order score and report.
     *
     * @param orderId the order id
     * @return the order score report
     * @throws ServiceException the service exception
     */
    Optional<OrderScoreReport> findOrderScoreReport(long orderId) throws ServiceException;

}