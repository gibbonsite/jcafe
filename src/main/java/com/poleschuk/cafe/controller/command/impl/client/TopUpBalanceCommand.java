package com.poleschuk.cafe.controller.command.impl.client;


import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.User;
import com.poleschuk.cafe.service.UserService;
import com.poleschuk.cafe.service.impl.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;


import static com.poleschuk.cafe.controller.Parameter.*;
import static com.poleschuk.cafe.controller.PagePath.TOP_UP_BALANCE_PAGE;
import static com.poleschuk.cafe.controller.PagePath.BALANCE_TOPPED_UP_PAGE;

import static java.lang.Boolean.TRUE;


/**
 * TopUpBalanceCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class TopUpBalanceCommand implements Command {
    private final UserService service = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Router router = new Router(TOP_UP_BALANCE_PAGE);
        User user = (User) session.getAttribute(USER);
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(request.getParameter(TOPPING_UP_AMOUNT)));
        try {
            if (service.topUpBalance(user, amount)) {
                router.setRedirect();
                router.setPage(BALANCE_TOPPED_UP_PAGE);
            } else {
                request.setAttribute(INVALID_TOPPING_UP_AMOUNT, TRUE);
            }
        } catch (ServiceException e) {
            throw new CommandException("Exception in a UpdateUserProfileCommand class", e);
        }
        return router;
    }
}