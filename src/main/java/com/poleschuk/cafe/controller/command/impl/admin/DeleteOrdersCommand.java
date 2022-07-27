package com.poleschuk.cafe.controller.command.impl.admin;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.RouterType;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.service.OrderService;
import com.poleschuk.cafe.service.impl.OrderServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

import static com.poleschuk.cafe.controller.CommandPath.FIND_ALL_ORDERS_URL;

/**
 * DeleteOrdersCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class DeleteOrdersCommand implements Command {
    private final OrderService service = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(RouterType.REDIRECT);
        router.setUrl(FIND_ALL_ORDERS_URL);
        try {
            service.deleteOldOrders();
        } catch (ServiceException e) {
            throw new CommandException("Exception in a DeleteOrdersCommand class. ", e);
        }
        return router;
    }
}