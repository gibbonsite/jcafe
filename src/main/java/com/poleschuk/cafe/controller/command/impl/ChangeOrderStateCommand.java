package com.poleschuk.cafe.controller.command.impl;


import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.RouterType;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.OrderState;
import com.poleschuk.cafe.model.entity.User;
import com.poleschuk.cafe.model.entity.UserRole;
import com.poleschuk.cafe.service.OrderService;
import com.poleschuk.cafe.service.impl.OrderServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.poleschuk.cafe.controller.Parameter.*;
import static com.poleschuk.cafe.controller.CommandPath.*;
import static com.poleschuk.cafe.controller.PagePath.ORDER_STATE_CHANGED_PAGE;
import static com.poleschuk.cafe.controller.PagePath.ORDER_STATE_NOT_CHANGED_PAGE;

import java.util.Optional;

/**
 * ChangeOrderStateCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class ChangeOrderStateCommand implements Command {
    private final OrderService service = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
    	HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        Router router = new Router();
        try {
            long id = Long.parseLong(request.getParameter(ORDER_ID));
            Optional<User> optionalUser = service.changeOrderStateById(id, request.getParameter(STATE),
            		user.getUserId());
            if (optionalUser.isPresent()) {
            	session.setAttribute(USER, optionalUser.get());
    	        router.setRedirect();
            	router.setPage(Command.createURL(ORDER_STATE_CHANGED_PAGE, USER_ROLE, user.getRole()
            			.toString()));
            } else {
    	        router.setRedirect();
            	router.setPage(Command.createURL(ORDER_STATE_NOT_CHANGED_PAGE, USER_ROLE, user.getRole()
            			.toString()));
            }
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a ChangeOrderStateCommand class. ", e);
        }
        return router;
    }
}