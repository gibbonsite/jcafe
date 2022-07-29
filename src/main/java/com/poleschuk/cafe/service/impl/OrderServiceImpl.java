package com.poleschuk.cafe.service.impl;

import static com.poleschuk.cafe.controller.Parameter.ADDRESS;
import static com.poleschuk.cafe.controller.Parameter.ORDER_DATE;
import static com.poleschuk.cafe.controller.Parameter.ORDER_DISCOUNT;
import static com.poleschuk.cafe.controller.Parameter.PRODUCT_PAYMENT;
import static com.poleschuk.cafe.controller.Parameter.USER_COMMENT;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.poleschuk.cafe.exception.DaoException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.dao.EntityTransaction;
import com.poleschuk.cafe.model.dao.impl.MenuDaoImpl;
import com.poleschuk.cafe.model.dao.impl.OrderDaoImpl;
import com.poleschuk.cafe.model.dao.impl.UserDaoImpl;
import com.poleschuk.cafe.model.entity.CompleteOrder;
import com.poleschuk.cafe.model.entity.Menu;
import com.poleschuk.cafe.model.entity.Order;
import com.poleschuk.cafe.model.entity.OrderComponent;
import com.poleschuk.cafe.model.entity.OrderDiscount;
import com.poleschuk.cafe.model.entity.OrderScoreReport;
import com.poleschuk.cafe.model.entity.OrderState;
import com.poleschuk.cafe.model.entity.PaymentType;
import com.poleschuk.cafe.model.entity.User;
import com.poleschuk.cafe.model.entity.UserRole;
import com.poleschuk.cafe.model.entity.UserState;
import com.poleschuk.cafe.service.BonusService;
import com.poleschuk.cafe.service.OrderService;
import com.poleschuk.cafe.util.mail.Mail;
import com.poleschuk.cafe.validator.Validator;
import com.poleschuk.cafe.validator.impl.ValidatorImpl;

/**
 * OrderServiceImpl class implements OrderService interface and contains
 * business logic for order processing.
 */
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LogManager.getLogger();
    private static final String EMAIL_ORDER_MESSAGE = "The order has been placed successfully.";
    private static final String EMAIL_SUBJECT = "JCafe Order";
    private static final OrderServiceImpl instance = new OrderServiceImpl();
    private final Validator validator = ValidatorImpl.getInstance();
    private OrderServiceImpl() {}

    public static OrderServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean createOrder(Map<Menu, Integer> productMap, Map<String, String> orderInfo, User user,
    		BigDecimal totalPrice, BigDecimal discountedPrice) throws ServiceException {
        if (!validator.checkOrderInfo(orderInfo, totalPrice, user.getCash())) {
            return false;
        }
        OrderDaoImpl orderDao = new OrderDaoImpl();
        UserDaoImpl userDao = new UserDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(orderDao, userDao);
        boolean isCreate = true;

        try{
            String address = orderInfo.get(ADDRESS);
	        PaymentType paymentType = PaymentType.valueOf(orderInfo.get(PRODUCT_PAYMENT));
	        OrderDiscount orderDiscount = OrderDiscount.valueOf(orderInfo.get(ORDER_DISCOUNT));
            String comment = orderInfo.get(USER_COMMENT);
            LocalDateTime orderDate = LocalDateTime.parse(orderInfo.get(ORDER_DATE));
            BigDecimal amountToPay = (orderDiscount == OrderDiscount.SUGGESTED_DISCOUNT) ? discountedPrice
            		: totalPrice;
            amountToPay = amountToPay.setScale(2, RoundingMode.HALF_UP);
            logger.log(Level.INFO, "amountToPay = " + amountToPay);
        	BonusService bonusService = BonusServiceImpl.getInstance();
            BigDecimal accumulatedCashChange = (orderDiscount == OrderDiscount.SUGGESTED_DISCOUNT) ?
            		bonusService.getAccumulatedCashDecrease(totalPrice,	user.getLoyalScore(),
            		user.getAccumulatedCash()).negate() : totalPrice.subtract(discountedPrice);
            if (paymentType == PaymentType.CASH) {
            	userDao.updateCashById(user.getUserId(), user.getCash().subtract(amountToPay));
            	user.setCash(user.getCash().subtract(amountToPay));
            }
        	userDao.updateAccumulatedCashById(user.getUserId(), user.getAccumulatedCash()
        			.add(accumulatedCashChange));
        	user.setAccumulatedCash(user.getAccumulatedCash()
        			.add(accumulatedCashChange));
            Order newOrder = new Order(orderDate, OrderState.RECEIVED, amountToPay, accumulatedCashChange,
            		comment, user.getUserId(), paymentType, address);
            logger.log(Level.INFO, newOrder);
            long generatedKey = orderDao.createOrder(newOrder);
            if (generatedKey == 0) {
                isCreate = false;
            }
            logger.log(Level.INFO, "generatedKey = " + generatedKey);
            orderDao.createOrderMenu(generatedKey, productMap);
            logger.log(Level.INFO, "createOrderMenu");
            userDao.updateUserState(user.getUserId(), UserState.ACTIVE.ordinal() + 1);
            logger.log(Level.INFO, "updateUserState");
            Mail.createMail(user.getEmail(), EMAIL_SUBJECT, EMAIL_ORDER_MESSAGE);
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException("Exception in a createOrder method. ", e);
        } finally {
            transaction.endTransaction();
        }
        return isCreate;
    }

    @Override
    public List<Order> findAllUserOrders(User user) throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(orderDao);
        try {
            return orderDao.findAllUserOrders(user);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a findAllUserOrders method. ", e);
        }finally {
            transaction.end();
        }
    }

    @Override
    public List<CompleteOrder> findOrderSublist(int pageSize, int offset) throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        UserDaoImpl userDao = new UserDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(orderDao, userDao);
        List<CompleteOrder> completeOrders = new ArrayList<>();
        try {
            List<Order> orderList = orderDao.findOrderSublist(pageSize, offset);
            for (Order order: orderList) {
                Optional<User> optionalUser = userDao.findUserByOrder(order.getOrderId());
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    List<OrderComponent> menuList = orderDao.findAllOrderMenu(order.getOrderId());
                    logger.log(Level.INFO, "Menu list size " + menuList.size());
                    logger.log(Level.INFO, "Order date: " + order.getOrderDate());
                    completeOrders.add(new CompleteOrder(user, order, menuList));
                }else {
                    logger.log(Level.INFO, "The user doesn't exist. Order ID is " + order.getOrderId());
                }
            }
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException("Exception in a findAllOrders method. ", e);
        } finally {
            transaction.endTransaction();
        }
        return completeOrders;
    }

    @Override
    public List<CompleteOrder> findCancelledOrderSublist(int pageSize, int offset) throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        UserDaoImpl userDao = new UserDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(orderDao, userDao);
        List<CompleteOrder> completeOrders = new ArrayList<>();
        try {
            List<Order> orderList = orderDao.findCancelledOrderSublist(pageSize, offset);
            for (Order order: orderList) {
                Optional<User> optionalUser = userDao.findUserByOrder(order.getOrderId());
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    List<OrderComponent> menuList = orderDao.findAllOrderMenu(order.getOrderId());
                    logger.log(Level.INFO, "Menu list size " + menuList.size());
                    logger.log(Level.INFO, "Order date: " + order.getOrderDate());
                    completeOrders.add(new CompleteOrder(user, order, menuList));
                }else {
                    logger.log(Level.INFO, "The user doesn't exist. Order ID is " + order.getOrderId());
                }
            }
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException("Exception in a findCancelledOrderSublist method. ", e);
        } finally {
            transaction.endTransaction();
        }
        return completeOrders;
    }

    @Override
    public int readRowCount() throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(orderDao);
        try {
            return orderDao.readRowCount();
        } catch (DaoException e) {
            throw new ServiceException("Exception in a readRowCount service method. ", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public int readCancelledOrderRowCount() throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(orderDao);
        try {
            return orderDao.readCancelledOrderRowCount();
        } catch (DaoException e) {
            throw new ServiceException("Exception in a readCancelledOrderRowCount service method. ", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public Optional<User> changeOrderStateById(long orderId, String state, long userId) throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        UserDaoImpl userDao = new UserDaoImpl();
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(orderDao, userDao, menuDao);
        boolean result = true;
        Optional<User> optionalUser = Optional.empty();
        try{
        	Optional<User> user = userDao.findEntityById(userId);
        	Optional<Order> optionalOrder = orderDao.findEntityById(orderId);
        	if (user.isPresent() && optionalOrder.isPresent() && (user.get().getRole() == UserRole.ADMIN || 
        			user.get().getRole() == UserRole.CLIENT && optionalOrder.get().getUserId() == userId)) {
                BigDecimal earnedLoyalScore = BigDecimal.ZERO;
                OrderState orderState = OrderState.valueOf(state);
                switch (orderState) {
                case CANCELLED -> {
    				Order order = optionalOrder.get();
    	        	Optional<User> optionalOrderUser = userDao.findEntityById(order.getUserId());
	    			if (order.getOrderDate()
        					.isAfter(LocalDateTime.now()) && optionalOrderUser.isPresent()) {
	    				User orderUser = optionalOrderUser.get();
		                if (order.getPaymentType() == PaymentType.CASH) {
		                	userDao.updateCashById(order.getUserId(), orderUser.getCash().add(order.getTotalCost()));
		                	orderUser.setCash(orderUser.getCash().add(order.getTotalCost()));
		                }
		                BigDecimal accumulatedCashChange = order.getAccumulatedCash().compareTo(
		                		BigDecimal.ZERO) == -1 ? order.getAccumulatedCash().negate() : 
		                		order.getAccumulatedCash().min(orderUser.getAccumulatedCash()).negate();
		            	userDao.updateAccumulatedCashById(order.getUserId(), orderUser.getAccumulatedCash()
		            			.add(accumulatedCashChange));
		            	orderUser.setAccumulatedCash(orderUser.getAccumulatedCash()
		            			.add(accumulatedCashChange));
	    			} else {
        				result = false;
	    			}
                }
                case COMPLETED -> {
                    List<OrderComponent> menuList = orderDao.findAllOrderMenu(orderId);
                    for (OrderComponent orderComponent : menuList) {
                    	String menuName = orderComponent.getName();
                    	Optional<Menu> optionalMenu = menuDao.findMenuByName(menuName);
                    	if (optionalMenu.isPresent()) {
                    		earnedLoyalScore = earnedLoyalScore.add(optionalMenu.get()
                    				.getLoyalScore()
                    				.multiply(BigDecimal.valueOf(orderComponent.getAmount())));
                    	}
                    }
                }
                default -> {}
                }
        		if (orderState != OrderState.COMPLETED && optionalOrder.get()
        				.getOrderState() == OrderState.COMPLETED ||
        				orderState != OrderState.CANCELLED_FINISHED && (optionalOrder.get()
        				.getOrderState() == OrderState.CANCELLED_FINISHED ||
        				optionalOrder.get().getOrderState() == OrderState.CANCELLED)) {
        			result = false;
        		}
    			result = result && orderDao.updateOrderStateById(orderId, orderState);
    			if (orderState == OrderState.COMPLETED) {
    	        	Optional<User> orderUser = userDao.findEntityById(optionalOrder.get()
    	        			.getUserId());
    	        	result = result && orderUser.isPresent();
    				result = result && userDao.updateLoyalScoreById(orderUser.get()
    	        			.getUserId(), orderUser.get()
    						.getLoyalScore()
    						.add(earnedLoyalScore));
    			}
        	} else {
        		result = false;
        	}
        	if (result) {
				optionalUser = userDao.findEntityById(userId);
        		transaction.commit();
        	} else {
        		transaction.rollback();
        	}
        } catch (DaoException | IllegalArgumentException | NullPointerException e) {
            transaction.rollback();
            throw new ServiceException("Exception in a changeOrderStateById method. ", e);
        } finally {
            transaction.endTransaction();
        }
        return optionalUser;
    }

    @Override
    public boolean changeOrderScoreReport(long orderId, String score, String report) throws ServiceException {
        if (!validator.isCorrectOrderReport(report) || !validator.isCorrectOrderScore(score)) {
            return false;
        }

        OrderDaoImpl orderDao = new OrderDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(orderDao);
        boolean result = false;
        try{
        	Optional<Order> order = orderDao.findEntityById(orderId);
        	if (order.isPresent() && (order.get()
        			.getOrderState() == OrderState.COMPLETED || order.get()
        			.getOrderState() == OrderState.CANCELLED_FINISHED)) {
        		Optional<OrderScoreReport> orderScoreReport = orderDao.findOrderScoreReport(orderId);
        		int integerScore = Integer.parseInt(score);
        		result = orderScoreReport.isEmpty() ? orderDao.insertOrderScoreReport(orderId, integerScore, report) :
	        			orderDao.updateOrderScoreReport(orderId, integerScore, report);
        	}
        } catch (DaoException e) {
            throw new ServiceException("Exception in a changeOrderStateById method. ", e);
        } finally {
            transaction.end();
        }
    	return result;
    }

    @Override
    public boolean deleteOldOrders() throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(orderDao);
        try {
            return orderDao.deleteOrders();
        } catch (DaoException e) {
            throw new ServiceException("Exception in deleteOldOrders service method. ", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public List<Order> findAllSortedOrdersByDate() throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(orderDao);
        try {
            return orderDao.findAllSortedOrdersByDate();
        } catch (DaoException e) {
            throw new ServiceException("Exception in a findAllSortedOrdersByDate service method", e);
        } finally {
            transaction.end();
        }
    }
    
    public Optional<OrderScoreReport> findOrderScoreReport(long orderId) throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(orderDao);
        try {
            return orderDao.findOrderScoreReport(orderId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a findAllUserOrders method. ", e);
        }finally {
            transaction.end();
        }
    	
    }

	@Override
	public List<OrderScoreReport> findAllOrderScoreReports() throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(orderDao);
        try {
            return orderDao.findAllOrderScoreReports();
        } catch (DaoException e) {
            throw new ServiceException("Exception in a findAllOrderScoreReports service method. ", e);
        } finally {
            transaction.end();
        }
	}

    
}