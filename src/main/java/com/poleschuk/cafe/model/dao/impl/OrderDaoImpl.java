package com.poleschuk.cafe.model.dao.impl;

import com.poleschuk.cafe.exception.DaoException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.dao.BaseDao;
import com.poleschuk.cafe.model.dao.OrderDao;
import com.poleschuk.cafe.model.entity.OrderComponent;
import com.poleschuk.cafe.model.entity.OrderScoreReport;
import com.poleschuk.cafe.model.entity.OrderState;
import com.poleschuk.cafe.model.entity.Menu;
import com.poleschuk.cafe.model.entity.Order;
import com.poleschuk.cafe.model.entity.User;
import com.poleschuk.cafe.model.mapper.EntityRowMapper;
import com.poleschuk.cafe.model.mapper.impl.OrderMapper;
import com.poleschuk.cafe.model.mapper.impl.OrderScoreReportMapper;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * OrderDaoImpl class implements OrderDao interface and executes requests
 * to the DB.
 */
public class OrderDaoImpl extends BaseDao<Order> implements OrderDao {
    private static final Logger logger = LogManager.getLogger();
    private static final int ONE_UPDATE = 1;
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String SQL_SELECT_ORDER_SUBLIST = """
            SELECT order_id, order_date, order_state_id, user_comment,
            total_cost, accumulated_cash, user_id, payment_type_id, address FROM orders
            LIMIT ? OFFSET ?""";
    private static final String SQL_SELECT_ORDER_BY_ID = """
            SELECT order_id, order_date, order_state_id, user_comment,
            total_cost, accumulated_cash, user_id, payment_type_id, address FROM orders
            WHERE order_id = (?)""";
    private static final String SQL_SELECT_ORDER_SCORE_REPORT = """
            SELECT order_id, report, score FROM order_reports WHERE order_id = (?)""";
    private static final String SQL_SELECT_ALL_ORDER_SCORE_REPORTS = """
    		SELECT order_id, score, report FROM order_reports""";
    private static final String SQL_SELECT_ALL_ORDER_ROW_COUNT = """
            SELECT COUNT(*) FROM orders""";
    private static final String SQL_SELECT_CANCELLED_ORDER_ROW_COUNT = """
            SELECT COUNT(*) FROM orders WHERE order_state_id = 2""";
    private static final String SQL_INSERT_NEW_ORDER = """
            INSERT INTO orders(order_date, order_state_id, user_comment,
            total_cost, accumulated_cash, user_id, payment_type_id, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)""";
    private static final String SQL_DELETE_ORDER = """
            DELETE FROM orders WHERE order_id = (?)""";
    private static final String SQL_UPDATE_ORDER = """
            UPDATE orders SET order_date = (?), order_state_id = (?),
            user_comment = (?), total_cost = (?), accumulated_cash = (?),
            user_id = (?), payment_type_id = (?), address = (?) WHERE order_id = (?)""";
    private static final String SQL_SELECT_ALL_USER_ORDERS = """
            SELECT order_id, order_date, order_state_id, user_comment,
            total_cost, accumulated_cash, user_id, payment_type_id, address
            FROM orders WHERE user_id = (?)""";
    private static final String SQL_UPDATE_ORDER_STATE_BY_ID = """
            UPDATE orders SET order_state_id = (?)
            WHERE order_id = (?)""";
    private static final String SQL_INSERT_ORDER_SCORE_REPORT = """
            INSERT INTO order_reports(order_id, score, report) VALUES (?, ?, ?)""";
    private static final String SQL_UPDATE_ORDER_SCORE_REPORT_BY_ID = """
            UPDATE order_reports SET score = (?), report = (?)
            WHERE order_id = (?)""";
    private static final String SQL_INSERT_ORDER_MENU_BY_ORDER_ID = """
            INSERT INTO orders_menu(order_id, menu_id, menu_number)
            VALUES (?, ?, ?)""";
    private static final String SQL_SELECT_ALL_ORDER_MENU = """
            SELECT name, menu_number FROM orders
            JOIN orders_menu ON orders_menu.order_id = orders.order_id
            JOIN menu ON menu.menu_id = orders_menu.menu_id
            WHERE orders.order_id = (?)""";
    private static final String SQL_DELETE_OLD_USERS_ORDERS = """
            DELETE FROM orders
            WHERE order_date < (NOW() - INTERVAL '3 YEAR')""";
    private static final String SQL_SELECT_SORTED_ORDERS_BY_DATE = """
            SELECT order_id, order_date, order_state_id, user_comment,
            total_cost, accumulated_cash, user_id, payment_type_id,
            address FROM orders ORDER BY order_date DESC""";
    private static final String SQL_SELECT_CANCELLED_ORDER_SUBLIST = """
            SELECT order_id, order_date, order_state_id, user_comment,
            total_cost, accumulated_cash, user_id, payment_type_id,
            address FROM orders WHERE order_state_id = 2 LIMIT ? OFFSET ?""";


    @Override
    public List<Order> findOrderSublist(int pageSize, int offset) throws DaoException {
    	EntityRowMapper<Order> mapper = OrderMapper.getInstance();
        List<Order> orderList = new ArrayList<>();
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_ORDER_SUBLIST)) {
            statement.setInt(1, pageSize);
            statement.setInt(2, offset);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Optional<Order> optionalOrder = mapper.mapRow(resultSet);
                    optionalOrder.ifPresent(orderList::add);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while find all orders: " + e.getMessage());
            throw new DaoException("Exception while find all orders ", e);
        }
        return orderList;
    }

    @Override
    public Optional<Order> findEntityById(long id) throws DaoException{
    	EntityRowMapper<Order> mapper = OrderMapper.getInstance();
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_ORDER_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapper.mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while find order by id: " + e.getMessage());
            throw new DaoException("Exception while find order by id ", e);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(long id) throws DaoException{
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_DELETE_ORDER)) {
            statement.setLong(1, id);
            return statement.executeUpdate() == ONE_UPDATE;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while delete order by id: " + e.getMessage());
            throw new DaoException("Exception while delete order by id ", e);
        }
    }

    @Override
    public boolean insert(Order entity) throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_INSERT_NEW_ORDER)) {
            statement.setDate(1, Date.valueOf(entity.getOrderDate().toString()));
            statement.setInt(2, entity.getOrderState().ordinal() + 1);
            statement.setString(3, entity.getUserComment());
            statement.setBigDecimal(4, entity.getTotalCost());
            statement.setLong(5, entity.getUserId());
            statement.setLong(6, entity.getPaymentType().ordinal() + 1);
            statement.setString(7, entity.getAddress());
            return statement.executeUpdate() == ONE_UPDATE;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while create order: " + e.getMessage());
            throw new DaoException("Exception while create order ", e);
        }
    }

    @Override
    public Optional<Order> update(Order entity) throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_UPDATE_ORDER)) {
            Optional<Order> order = findEntityById(entity.getOrderId());
            statement.setTimestamp(1, Timestamp.valueOf(entity.getOrderDate()));
            statement.setInt(2, entity.getOrderState().ordinal() + 1);
            statement.setString(3, entity.getUserComment());
            statement.setBigDecimal(4, entity.getTotalCost());
            statement.setBigDecimal(5, entity.getAccumulatedCash());
            statement.setLong(6, entity.getUserId());
            statement.setLong(7, entity.getPaymentType().ordinal() + 1);
            statement.setString(8, entity.getAddress());
            statement.setLong(9, entity.getOrderId());
            return statement.executeUpdate() == ONE_UPDATE ? order : Optional.empty();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while update order: " + e.getMessage());
            throw new DaoException("Exception while update order ", e);
        }
    }

    @Override
    public long createOrder(Order order) throws DaoException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_INSERT_NEW_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setTimestamp(1, Timestamp.valueOf(order.getOrderDate().format(formatter)));
            statement.setInt(2, order.getOrderState().ordinal() + 1);
            statement.setString(3, order.getUserComment());
            statement.setBigDecimal(4, order.getTotalCost());
            statement.setBigDecimal(5, order.getAccumulatedCash());            
            statement.setLong(6, order.getUserId());
            statement.setLong(7, order.getPaymentType().ordinal() + 1);
            statement.setString(8, order.getAddress());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                return resultSet.getLong(1);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while create order: " + e.getMessage());
            throw new DaoException("Exception while create order ", e);
        }
    }

    @Override
    public List<Order> findAllUserOrders(User user) throws DaoException {
    	EntityRowMapper<Order> mapper = OrderMapper.getInstance();
        List<Order> orderList = new ArrayList<>();
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_ALL_USER_ORDERS)) {
            statement.setLong(1, user.getUserId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Optional<Order> optionalOrder = mapper.mapRow(resultSet);
                    optionalOrder.ifPresent(orderList::add);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while find all user orders: " + e.getMessage());
            throw new DaoException("Exception while find all user orders ", e);
        }
        return orderList;
    }


    @Override
    public boolean updateOrderStateById(long orderId, OrderState orderState) throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_UPDATE_ORDER_STATE_BY_ID)) {
            statement.setInt(1, orderState.ordinal() + 1);
            statement.setLong(2, orderId);
            return statement.executeUpdate() == ONE_UPDATE;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while update order state by id: " + e.getMessage());
            throw new DaoException("Exception while update order state by id ", e);
        }
    }

    @Override
    public List<OrderScoreReport> findAllOrderScoreReports() throws DaoException {
    	EntityRowMapper<OrderScoreReport> mapper = OrderScoreReportMapper.getInstance();
        List<OrderScoreReport> orderScoreReportList = new ArrayList<>();
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_ALL_ORDER_SCORE_REPORTS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                	Optional<OrderScoreReport> optionalOrderScoreReport = mapper.mapRow(resultSet);
                	optionalOrderScoreReport.ifPresent(orderScoreReportList::add);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while find all orders: " + e.getMessage());
            throw new DaoException("Exception while find all orders ", e);
        }
        return orderScoreReportList;
    }

    @Override
    public boolean insertOrderScoreReport(long orderId, int score, String report) throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_INSERT_ORDER_SCORE_REPORT)) {
            statement.setLong(1, orderId);
            statement.setInt(2, score);
            statement.setString(3, report);
            return statement.executeUpdate() == ONE_UPDATE;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while insert order score, report: " + e.getMessage());
            throw new DaoException("Exception while insert order score, report ", e);
        }
    }


    @Override
    public boolean updateOrderScoreReport(long orderId, int score, String report) throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_UPDATE_ORDER_SCORE_REPORT_BY_ID)) {
            statement.setInt(1, score);
            statement.setString(2, report);
            statement.setLong(3, orderId);
            return statement.executeUpdate() == ONE_UPDATE;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while update order score, report by id: " + e.getMessage());
            throw new DaoException("Exception while update order score, report by id ", e);
        }
    }

    @Override
    public boolean createOrderMenu(long orderId, Map<Menu, Integer> mapOrderProduct)  throws DaoException{
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_INSERT_ORDER_MENU_BY_ORDER_ID)) {
            for (Menu item: mapOrderProduct.keySet()) {
                int value = mapOrderProduct.get(item);
                statement.setLong(1, orderId);
                statement.setLong(2, item.getMenuId());
                statement.setInt(3, value);
                statement.addBatch();
            }
            return statement.executeBatch().length >= ONE_UPDATE;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while create order-menu data: " + e.getMessage());
            throw new DaoException("Exception in a createOrderMenu method. ", e);
        }
    }

    @Override
    public int readRowCount() throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_ALL_ORDER_ROW_COUNT)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? resultSet.getInt(1) : 0;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while read row count from order table method: " + e.getMessage());
            throw new DaoException("Exception in a readRowCount method. ", e);
        }
    }

    @Override
    public int readCancelledOrderRowCount() throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_CANCELLED_ORDER_ROW_COUNT)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? resultSet.getInt(1) : 0;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in a readCancelledOrderRowCount method: " + e.getMessage());
            throw new DaoException("Exception in a readCancelledOrderRowCount method. ", e);
        }
    }

    @Override
	public List<OrderComponent> findAllOrderMenu(long orderId) throws DaoException {
        List<OrderComponent> componentOrders = new ArrayList<>();
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_ALL_ORDER_MENU)) {
            statement.setLong(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String name = resultSet.getString(1);
                    int amount = resultSet.getInt(2);
                    componentOrders.add(new OrderComponent(name, amount));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while find all menu order: " + e.getMessage());
            throw new DaoException("Exception in a findAllMenuOrder method. ", e);
        }
        return componentOrders;
    }

    @Override
    public boolean deleteOrders() throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_DELETE_OLD_USERS_ORDERS)) {
            return statement.executeUpdate() >= ONE_UPDATE;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while delete old orders : " + e.getMessage());
            throw new DaoException("Exception in a deleteOrders method. ", e);
        }
    }

    @Override
    public List<Order> findAllSortedOrdersByDate() throws DaoException {
    	EntityRowMapper<Order> mapper = OrderMapper.getInstance();
        List<Order> orderList = new ArrayList<>();
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_SORTED_ORDERS_BY_DATE);
            ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Optional<Order> optionalOrder = mapper.mapRow(resultSet);
                optionalOrder.ifPresent(orderList::add);
            }
        }catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while find all sorted orders by date : " + e.getMessage());
            throw new DaoException("Exception while find all sorted orders by date ", e);
        }
        return orderList;
    }

    @Override
    public List<Order> findCancelledOrderSublist(int pageSize, int offset) throws DaoException {
    	EntityRowMapper<Order> mapper = OrderMapper.getInstance();
        List<Order> orderList = new ArrayList<>();
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_CANCELLED_ORDER_SUBLIST)) {
            statement.setInt(1, pageSize);
            statement.setInt(2, offset);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Optional<Order> optionalOrder = mapper.mapRow(resultSet);
                    optionalOrder.ifPresent(orderList::add);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while find cancelled order sublist: " + e.getMessage());
            throw new DaoException("Exception while find cancelled order sublist ", e);
        }
        return orderList;
    }

    @Override
    public Optional<OrderScoreReport> findOrderScoreReport(long orderId) throws DaoException {
    	EntityRowMapper<OrderScoreReport> mapper = OrderScoreReportMapper.getInstance();
    	Optional<OrderScoreReport> optionalOrderScoreReport = Optional.empty();
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_ORDER_SCORE_REPORT)) {
        	statement.setLong(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    optionalOrderScoreReport = mapper.mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in findOrderScoreReport method: " + e.getMessage());
            throw new DaoException("Exception in findOrderScoreReport method ", e);
        }
        return optionalOrderScoreReport;
    }

	@Override
	public List<Order> findAll() throws DaoException {
		throw new UnsupportedOperationException("method findAll for order is not supported");
	}


}