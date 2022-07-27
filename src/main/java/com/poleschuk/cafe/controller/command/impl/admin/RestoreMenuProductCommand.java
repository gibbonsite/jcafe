package com.poleschuk.cafe.controller.command.impl.admin;


import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.RouterType;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.service.MenuService;
import com.poleschuk.cafe.service.impl.MenuServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.poleschuk.cafe.controller.Parameter.*;

/**
 * RestoreMenuProductCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class RestoreMenuProductCommand implements Command {
    private final MenuService menuService = MenuServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        Router router = new Router(currentPage, RouterType.REDIRECT);
        try {
            long menuId = Long.parseLong(request.getParameter(PRODUCT_ID));
            menuService.restoreMenuProductById(menuId);
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a RestoreMenuProductCommand class ", e);
        }
        return router;
    }
}