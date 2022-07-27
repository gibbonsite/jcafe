package com.poleschuk.cafe.controller.command.impl.client;

import static com.poleschuk.cafe.controller.PagePath.ACCUMULATIVE_DISCOUNT_PAGE;
import static com.poleschuk.cafe.controller.Parameter.ACCUMULATIVE_DISCOUNT_CASH;
import static com.poleschuk.cafe.controller.Parameter.USER;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.model.entity.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class GoToAccumulativeDiscountPageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(ACCUMULATIVE_DISCOUNT_PAGE);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        request.setAttribute(ACCUMULATIVE_DISCOUNT_CASH, user.getAccumulatedCash());
        return router;
    }

}
