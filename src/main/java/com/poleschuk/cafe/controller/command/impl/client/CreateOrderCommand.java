package com.poleschuk.cafe.controller.command.impl.client;


import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.RouterType;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.Menu;
import com.poleschuk.cafe.model.entity.User;
import com.poleschuk.cafe.service.OrderService;
import com.poleschuk.cafe.service.impl.OrderServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.poleschuk.cafe.controller.Parameter.*;

import static com.poleschuk.cafe.controller.PagePath.*;

import static java.lang.Boolean.TRUE;

/**
 * CreateOrderCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class CreateOrderCommand implements Command {
    private final OrderService service = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(SUCCESS_PAGE);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        Map<Menu, Integer> orderProduct = (HashMap<Menu, Integer>) session.getAttribute(CART);
        Map<String, String> orderInfo = new HashMap<>();
        orderInfo.put(USER_COMMENT, request.getParameter(USER_COMMENT));
        orderInfo.put(ADDRESS, request.getParameter(ADDRESS));
        orderInfo.put(PRODUCT_PAYMENT, request.getParameter(PRODUCT_PAYMENT));
        orderInfo.put(ORDER_DATE, request.getParameter(ORDER_DATE));
        orderInfo.put(ORDER_DISCOUNT, request.getParameter(ORDER_DISCOUNT));

        try {
            BigDecimal totalPrice = (BigDecimal) session.getAttribute(TOTAL_PRICE);
            BigDecimal discountedCost = (BigDecimal) session.getAttribute(DISCOUNTED_PRICE);
            if (service.createOrder(orderProduct, orderInfo, user, totalPrice, discountedCost)) {
                orderProduct.clear();
                session.setAttribute(CART, orderProduct);
                session.setAttribute(DISCOUNTED_PRICE, BigDecimal.ZERO);
                router.setRedirect();
                return router;
            }else {
                String currentPage = (String) session.getAttribute(CURRENT_PAGE);
                router.setPage(currentPage);
                for (String key: orderInfo.keySet()) {
                    String value = orderInfo.get(key);
                    switch (value) {
                        case INVALID_ORDER_COMMENT -> request.setAttribute(INVALID_ORDER_COMMENT, TRUE);
                        case INVALID_ORDER_ADDRESS -> request.setAttribute(INVALID_ORDER_ADDRESS, TRUE);
                        case INVALID_ORDER_PAYMENT -> request.setAttribute(INVALID_ORDER_PAYMENT, TRUE);
                        case INVALID_ORDER_DISCOUNT -> request.setAttribute(INVALID_ORDER_DISCOUNT, TRUE);
                        case OUT_OF_CASH -> request.setAttribute(OUT_OF_CASH, TRUE);
                        case INVALID_ORDER_DATE -> request.setAttribute(INVALID_ORDER_DATE, TRUE);
                    }
                }
                request.setAttribute(USER_COMMENT, orderInfo.get(USER_COMMENT));
                request.setAttribute(ADDRESS, orderInfo.get(ADDRESS));
                request.setAttribute(ORDER_DATE, orderInfo.get(ORDER_DATE));
                request.setAttribute(PRODUCT_PAYMENT, orderInfo.get(PRODUCT_PAYMENT));
                request.setAttribute(ORDER_DISCOUNT, orderInfo.get(ORDER_DISCOUNT));
            }
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a CreateOrderCommand class. ", e);
        }
        return router;
    }
}