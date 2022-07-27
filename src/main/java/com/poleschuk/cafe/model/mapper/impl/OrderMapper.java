package com.poleschuk.cafe.model.mapper.impl;


import static com.poleschuk.cafe.model.mapper.ColumnNames.ORDER_ADDRESS;
import static com.poleschuk.cafe.model.mapper.ColumnNames.ORDER_DATE;
import static com.poleschuk.cafe.model.mapper.ColumnNames.ORDER_ID;
import static com.poleschuk.cafe.model.mapper.ColumnNames.ORDER_PAYMENT_TYPE_ID;
import static com.poleschuk.cafe.model.mapper.ColumnNames.ORDER_STATE_ID;
import static com.poleschuk.cafe.model.mapper.ColumnNames.TOTAL_COST;
import static com.poleschuk.cafe.model.mapper.ColumnNames.ORDER_ACCUMULATED_CASH;
import static com.poleschuk.cafe.model.mapper.ColumnNames.USER_COMMENT;
import static com.poleschuk.cafe.model.mapper.ColumnNames.ORDER_USER_ID;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.poleschuk.cafe.model.entity.Order;
import com.poleschuk.cafe.model.entity.OrderState;
import com.poleschuk.cafe.model.entity.PaymentType;
import com.poleschuk.cafe.model.mapper.EntityRowMapper;


/**
 * OrderMapper class extracts ResultSet row into Order object.
 */
public class OrderMapper implements EntityRowMapper<Order> {
    private static final Logger logger = LogManager.getLogger();
    private static final EntityRowMapper<Order> instance = new OrderMapper();
    

	public static EntityRowMapper<Order> getInstance() {
		return instance;
	}

	private OrderMapper() {
	}
	
    @Override
    public Optional<Order> mapRow(ResultSet resultSet) {
        Order order = new Order();
        Optional<Order> optionalOrder;
        try {
            order.setOrderId(resultSet.getLong(ORDER_ID));
            order.setOrderDate(resultSet.getTimestamp(ORDER_DATE).toLocalDateTime());
            logger.log(Level.INFO, "Order date: " + order.getOrderDate());
            order.setOrderState(OrderState.values()[resultSet.getInt(ORDER_STATE_ID) - 1]);
            order.setTotalCost(resultSet.getBigDecimal(TOTAL_COST));
            order.setAccumulatedCash(resultSet.getBigDecimal(ORDER_ACCUMULATED_CASH));
            order.setUserComment(resultSet.getString(USER_COMMENT));
            order.setPaymentType(PaymentType.values()[resultSet.getInt(ORDER_PAYMENT_TYPE_ID) - 1]);
            order.setAddress(resultSet.getString(ORDER_ADDRESS));
            order.setUserId(resultSet.getLong(ORDER_USER_ID));
            optionalOrder = Optional.of(order);
        } catch (SQLException e) {
            optionalOrder = Optional.empty();
        }
        return optionalOrder;
    }
}