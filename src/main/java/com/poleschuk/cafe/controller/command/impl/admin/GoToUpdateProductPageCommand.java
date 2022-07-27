package com.poleschuk.cafe.controller.command.impl.admin;


import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.Menu;
import com.poleschuk.cafe.service.MenuService;
import com.poleschuk.cafe.service.impl.MenuServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

import static com.poleschuk.cafe.controller.Parameter.*;
import static com.poleschuk.cafe.controller.PagePath.*;

/**
 * GoToUpdateProductPageCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class GoToUpdateProductPageCommand implements Command {
    private final MenuService service = MenuServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(UPDATE_PRODUCT_PAGE);
        try {
            long menuId = Long.parseLong(request.getParameter(PRODUCT_ID));
            Optional<Menu> menu = service.findProductById(menuId);
            if (menu.isEmpty()) {
                router.setRedirect();
                router.setPage(ERROR_500_PAGE);
                return router;
            }
            request.setAttribute(PRODUCT_MENU, menu.get());
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a GoToUpdateProductPageCommand class", e);
        }
        return router;
    }
}