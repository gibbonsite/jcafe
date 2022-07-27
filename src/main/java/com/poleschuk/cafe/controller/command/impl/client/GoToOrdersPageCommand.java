package com.poleschuk.cafe.controller.command.impl.client;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.Order;
import com.poleschuk.cafe.model.entity.User;
import com.poleschuk.cafe.service.OrderService;
import com.poleschuk.cafe.service.impl.OrderServiceImpl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import static com.poleschuk.cafe.controller.Parameter.*;
import static com.poleschuk.cafe.controller.PagePath.ORDERS_PAGE;

/**
 * GoToOrdersPageCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class GoToOrdersPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final OrderService service = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(ORDERS_PAGE);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        try {
            logger.log(Level.INFO, "User: " + user);
            List<Order> listOrder = service.findAllUserOrders(user);
            logger.log(Level.INFO, "List order: " + listOrder);
            request.setAttribute(ORDER_LIST, listOrder);
        } catch (ServiceException e) {
            throw new CommandException("Exception in a GoToOrdersPageCommand class. ", e);
        }
        return router;
    }
}