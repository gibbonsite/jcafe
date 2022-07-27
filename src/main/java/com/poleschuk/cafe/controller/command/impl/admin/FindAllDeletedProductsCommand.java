package com.poleschuk.cafe.controller.command.impl.admin;


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

import java.util.List;

import static com.poleschuk.cafe.controller.Parameter.*;
import static com.poleschuk.cafe.controller.PagePath.RESTORE_PAGE;

/**
 * FindAllDeletedProductsCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class FindAllDeletedProductsCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final MenuService menuService = MenuServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(RESTORE_PAGE);
        try {
            List<Menu> menuList = menuService.findAllDeletedMenu();
            logger.log(Level.INFO, "Deleted products: " + menuList);
            request.setAttribute(MENU_LIST, menuList);
            request.setAttribute(RESTORE_MENU, true);
        } catch (ServiceException e) {
            throw new CommandException("Exception in a FindAllRemovingProductsCommand class ", e);
        }
        return router;
    }
}