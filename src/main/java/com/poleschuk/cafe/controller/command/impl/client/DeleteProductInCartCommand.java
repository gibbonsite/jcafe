package com.poleschuk.cafe.controller.command.impl.client;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.Menu;
import com.poleschuk.cafe.service.MenuService;
import com.poleschuk.cafe.service.impl.MenuServiceImpl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import static com.poleschuk.cafe.controller.Parameter.*;
import static com.poleschuk.cafe.controller.Parameter.PRODUCT_ID;

/**
 * DeleteProductInCartCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class DeleteProductInCartCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final MenuService service = MenuServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        Router router = new Router(currentPage);
        Map<Menu, Integer> mapMenu = (HashMap<Menu, Integer>) session.getAttribute(CART);
        try {
            long id = Long.parseLong(request.getParameter(PRODUCT_ID));
            if (service.deleteProductFromCart(mapMenu, id)) {
                session.setAttribute(CART, mapMenu);
            }else{
                logger.log(Level.WARN, "Can't delete product from cart. ");
            }
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a DeleteProductInBasketCommand class ", e);
        }
        return router;
    }
}