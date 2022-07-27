package com.poleschuk.cafe.controller.command.impl.admin;


import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.UserState;
import com.poleschuk.cafe.service.UserService;
import com.poleschuk.cafe.service.impl.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;


import static com.poleschuk.cafe.controller.Parameter.*;
import static com.poleschuk.cafe.controller.PagePath.CLIENT_BLOCKED_PAGE;
import static com.poleschuk.cafe.controller.CommandPath.FIND_ALL_CLIENTS_URL;

import java.util.Optional;

/**
 * BlockUserByIdCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class BlockUserByIdCommand implements Command {
    private final UserService service = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setUrl(FIND_ALL_CLIENTS_URL);
        try {
            long id = Long.parseLong(request.getParameter(USER_ID));
            if (service.changeUserStateById(UserState.BLOCKED, id)) {
            	router.setUrl(null);
            	router.setPage(CLIENT_BLOCKED_PAGE);
            	router.setRedirect();
            }
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a BlockUserByIdCommand class ", e);
        }
        return router;
    }
}