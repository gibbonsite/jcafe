package com.poleschuk.cafe.controller.command.impl.client;


import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.Menu;
import com.poleschuk.cafe.model.entity.User;
import com.poleschuk.cafe.service.BonusService;
import com.poleschuk.cafe.service.CalculateService;
import com.poleschuk.cafe.service.impl.BonusServiceImpl;
import com.poleschuk.cafe.service.impl.CalculateServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.poleschuk.cafe.controller.Parameter.*;

import static com.poleschuk.cafe.controller.PagePath.*;


/**
 * GoToCartPageCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class GoToCartPageCommand implements Command {
    private final CalculateService calculateService = CalculateServiceImpl.getInstance();
    private final BonusService bonusService = BonusServiceImpl.getInstance();
    
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(CART_PAGE);
        HttpSession session = request.getSession();
        Map<Menu, Integer> productMap = (HashMap<Menu, Integer>) session.getAttribute(CART);
        if (productMap == null) {
            productMap = new HashMap<Menu, Integer>();
            session.setAttribute(CART, productMap);
        }
        User user = (User) session.getAttribute(USER);
        try {
	        if (!productMap.isEmpty()) {
	            BigDecimal totalPrice = calculateService.calculateTotalPrice(productMap);
	            session.setAttribute(TOTAL_PRICE, totalPrice);
	            BigDecimal discountedPrice = bonusService.getDiscountedPrice(totalPrice, user.getLoyalScore(),
	            		user.getAccumulatedCash());
	            session.setAttribute(DISCOUNTED_PRICE, discountedPrice);
	            BigDecimal suggestedDiscount = bonusService.getSuggestedDiscount(totalPrice, user.getLoyalScore(),
	            		user.getAccumulatedCash());
	            request.setAttribute(SUGGESTED_DISCOUNT, suggestedDiscount.doubleValue());
	        }
	    } catch (ServiceException e) {
	        throw new CommandException("Exception in a GoToCartPageCommand class. ", e);
	    }

        return router;
    }
}