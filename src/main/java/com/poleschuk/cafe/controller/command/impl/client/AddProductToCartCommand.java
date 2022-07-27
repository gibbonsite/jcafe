package com.poleschuk.cafe.controller.command.impl.client;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.RouterType;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.Menu;
import com.poleschuk.cafe.service.MenuService;
import com.poleschuk.cafe.service.impl.MenuServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.poleschuk.cafe.controller.Parameter.*;
import static com.poleschuk.cafe.controller.CommandPath.*;

/**
 * AddProductToCartCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class AddProductToCartCommand implements Command {
    private final MenuService service = MenuServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Map<Menu, Integer> productMap = (HashMap<Menu, Integer>) session.getAttribute(CART);
        if (productMap == null) {
        	productMap = new HashMap<Menu, Integer>();
        	session.setAttribute(CART, productMap);
        }
        int productNumber = Integer.parseInt(request.getParameter(PRODUCT_NUMBER));
        try {
            if (productNumber >= 1) {
                long id = Long.parseLong(request.getParameter(SELECTED));
                service.addProductToCart(productMap, id, productNumber);
                session.setAttribute(CART, productMap);
            }
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a AddProductToCartCommand class ", e);
        }
        Router router = new Router(RouterType.REDIRECT);
        router.setUrl(FIND_ALL_MENU_URL);
        return router;
    }
}