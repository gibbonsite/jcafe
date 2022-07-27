package com.poleschuk.cafe.controller.command.impl.admin;


import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.RouterType;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.service.UserService;
import com.poleschuk.cafe.service.impl.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

import static com.poleschuk.cafe.controller.Parameter.*;
import static com.poleschuk.cafe.controller.CommandPath.*;

import java.util.Optional;

/**
 * DeleteAdminCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class DeleteAdminCommand implements Command {
    private final UserService service = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            long userId = Long.parseLong(request.getParameter(USER_ID));
            Router router = new Router(RouterType.REDIRECT);
            router.setUrl(FIND_ALL_ADMINS_URL);
            service.deleteAdmin(userId);
            return router;
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a DeleteUserCommand class ", e);
        }
    }
}