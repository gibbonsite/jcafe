package com.poleschuk.cafe.controller.command.impl.admin;


import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.User;
import com.poleschuk.cafe.service.UserService;
import com.poleschuk.cafe.service.impl.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

import static com.poleschuk.cafe.controller.Parameter.LIST_USER;
import static com.poleschuk.cafe.controller.PagePath.ADMINS_PAGE;

/**
 * FindAllAdminsCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class FindAllAdminsCommand implements Command {
    private final UserService service = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(ADMINS_PAGE);
        try {	
            List<User> listAdmin = service.findAllAdmins();
            request.setAttribute(LIST_USER, listAdmin);
        } catch (ServiceException e) {
            throw new CommandException("Exception in a FindAllAdminsCommand class. ", e);
        }
        return router;
    }
}