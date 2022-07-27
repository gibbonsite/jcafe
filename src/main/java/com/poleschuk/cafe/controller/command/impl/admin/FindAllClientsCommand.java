package com.poleschuk.cafe.controller.command.impl.admin;

import static com.poleschuk.cafe.controller.PagePath.CLIENTS_PAGE;
import static com.poleschuk.cafe.controller.Parameter.LIST_USERS;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.controller.command.paginateddata.FindPaginatedDataCommand;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.model.entity.User;
import com.poleschuk.cafe.service.UserService;
import com.poleschuk.cafe.service.impl.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

/**
 * FindAllClientsCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class FindAllClientsCommand implements FindPaginatedDataCommand<User>, Command {

    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = UserServiceImpl.getInstance();
    private final int PAGE_SIZE = 15;

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(CLIENTS_PAGE);
        
    	fillWithPaginatedData(request, PAGE_SIZE, LIST_USERS, userService::findClientSublist,
    			userService::readClientRowCount);

    	return router;
    	
    	/*
    	
    	Router router = new Router(CLIENTS_PAGE);
        try {
            List<User> listUsers = userService.findAllClients();
            request.setAttribute(LIST_USERS, listUsers);
            logger.log(Level.INFO, CLIENTS_PAGE);
        } catch (ServiceException e) {
            throw new CommandException("Exception in a FindAllUsersCommand class ", e);
        }
        return router;
        */
    }
}
