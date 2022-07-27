package com.poleschuk.cafe.controller.command.impl.admin;


import java.math.BigDecimal;
import java.util.Optional;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.RouterType;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.User;
import com.poleschuk.cafe.service.UserService;
import com.poleschuk.cafe.service.impl.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.poleschuk.cafe.controller.CommandPath.FIND_CANCELLED_ORDERS_URL;

import static com.poleschuk.cafe.controller.Parameter.*;
import static java.lang.Boolean.TRUE;

/**
 * UpdateLoyalScoreCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class UpdateLoyalScoreCommand implements Command {
    private final UserService service = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setUrl(FIND_CANCELLED_ORDERS_URL);
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(request.getParameter(CHANGE_LOYAL_SCORE)));
        HttpSession session = request.getSession();
        User loggedUser = (User) session.getAttribute(USER);
        try {
            long orderId = Long.parseLong(request.getParameter(ORDER_ID));
            Optional<User> optionalUser = service.decreaseLoyalScoreByOrderId(orderId, amount);
            if (optionalUser.isPresent()) {
            	User user = optionalUser.get();
            	if (loggedUser.getUserId() == user.getUserId()) {
            		loggedUser.setLoyalScore(amount.add(loggedUser.getLoyalScore()));
            	}
                router.setRedirect();
            } else {
                request.setAttribute(INVALID_LOYAL_SCORE_AMOUNT, TRUE);
            }
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a UpdateLoyalScoreCommand class", e);
        }
        return router;
    }
}