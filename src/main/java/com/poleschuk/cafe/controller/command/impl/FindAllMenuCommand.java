package com.poleschuk.cafe.controller.command.impl;

import static com.poleschuk.cafe.controller.PagePath.MENU_PAGE;
import static com.poleschuk.cafe.controller.Parameter.MENU_LIST;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.controller.command.paginateddata.FindPaginatedDataCommand;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.model.entity.Menu;
import com.poleschuk.cafe.service.MenuService;
import com.poleschuk.cafe.service.impl.MenuServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

/**
 * FindAllMenuCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class FindAllMenuCommand implements FindPaginatedDataCommand<Menu>, Command {
    private static final int PAGE_SIZE = 4;
    private final MenuService menuService = MenuServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
	    Router router = new Router(MENU_PAGE);

    	fillWithPaginatedData(request, PAGE_SIZE, MENU_LIST, menuService::findMenuSublist,
    			menuService::readRowCount);
    	
	    return router;

    }
}