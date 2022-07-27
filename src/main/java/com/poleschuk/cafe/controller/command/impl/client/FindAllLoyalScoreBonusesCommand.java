package com.poleschuk.cafe.controller.command.impl.client;

import static com.poleschuk.cafe.controller.PagePath.ORDERS_PAGE;
import static com.poleschuk.cafe.controller.Parameter.ORDER_LIST;

import java.math.BigDecimal;
import java.util.List;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.CompleteOrder;
import com.poleschuk.cafe.model.entity.LoyalScoreBonus;
import com.poleschuk.cafe.model.entity.User;
import com.poleschuk.cafe.service.BonusService;
import com.poleschuk.cafe.service.OrderService;
import com.poleschuk.cafe.service.impl.BonusServiceImpl;
import com.poleschuk.cafe.service.impl.OrderServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.poleschuk.cafe.controller.PagePath.LOYAL_SCORE_BONUSES_PAGE;
import static com.poleschuk.cafe.controller.Parameter.LOYAL_SCORE_BONUS_LIST;
import static com.poleschuk.cafe.controller.Parameter.LOYAL_SCORE_DISCOUNT;
import static com.poleschuk.cafe.controller.Parameter.USER;

/**
 * FindAllLoyalScoreBonusesCommand class implements Command interface and 
 * handles command of the web application. 
 */

public class FindAllLoyalScoreBonusesCommand implements Command {
	
    private final BonusService bonusService = BonusServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(LOYAL_SCORE_BONUSES_PAGE);
        try {
            List<LoyalScoreBonus> loyalScoreBonusList = bonusService.getLoyalScoreBonusesSortedByLoyalScore();
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER);
            BigDecimal loyalScoreDiscount = bonusService.getLoyalScoreDiscount(user.getLoyalScore());
            request.setAttribute(LOYAL_SCORE_BONUS_LIST, loyalScoreBonusList);
            request.setAttribute(LOYAL_SCORE_DISCOUNT, loyalScoreDiscount);
        } catch (ServiceException e) {
            throw new CommandException("Exception in a FindAllLoyalScoreBonusesCommand class.", e);
        }
        return router;
    }
}
