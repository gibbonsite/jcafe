package com.poleschuk.cafe.model.dao;


import com.poleschuk.cafe.exception.DaoException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * OrderDao interface consists of operations with order.
 */
public interface OrderDao {

    /**
     * Find order sublist.
     *
     * @param pageSize the page size
     * @param offset the offset
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Order> findOrderSublist(int pageSize, int offset) throws DaoException;

    /**
     * Read row count.
     *
     * @return the int
     * @throws DaoException the dao exception
     */
    int readRowCount() throws DaoException;

    /**
     * Read cancelled order row count.
     *
     * @return the int
     * @throws DaoException the dao exception
     */
    public int readCancelledOrderRowCount() throws DaoException;

	/**
     * Find all user orders.
     *
     * @param user the user
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Order> findAllUserOrders(User user) throws DaoException;

    /**
     * Update order state by id.
     *
     * @param orderId    the order id
     * @param orderState the order state
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateOrderStateById(long orderId, OrderState orderState) throws DaoException;

    /**
     * Find all order score reports.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<OrderScoreReport> findAllOrderScoreReports() throws DaoException;

    /**
     * Insert order score, report.
     *
     * @param orderId the order id
     * @param score the score
     * @param report the report
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean insertOrderScoreReport(long orderId, int score, String report) throws DaoException;

    /**
     * Update order score, report.
     *
     * @param orderId the order id
     * @param score the score
     * @param report the report
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean updateOrderScoreReport(long orderId, int score, String report) throws DaoException;

    /**
     * Create order menu.
     *
     * @param orderId         the order id
     * @param mapOrderProduct the map order product
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean createOrderMenu(long orderId, Map<Menu, Integer> mapOrderProduct) throws DaoException;

    /**
     * Create order.
     *
     * @param order the order
     * @return the long
     * @throws DaoException the dao exception
     */
    long createOrder(Order order) throws DaoException;

    /**
     * Find all menu order.
     *
     * @param orderId the order id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<OrderComponent> findAllOrderMenu(long orderId) throws DaoException;

    /**
     * Delete orders.
     *
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean deleteOrders() throws DaoException;

    /**
     * Find all sorted orders by date.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Order> findAllSortedOrdersByDate() throws DaoException;
    
    /**
     * Find cancelled order sublist.
     *
     * @param pageSize the page size
     * @param offset the offset
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Order> findCancelledOrderSublist(int pageSize, int offset) throws DaoException;

    
    /**
     * Find order score and report.
     *
     * @param orderId the order id
     * @return the order score report
     * @throws DaoException the dao exception
     */
    Optional<OrderScoreReport> findOrderScoreReport(long orderId) throws DaoException;

}