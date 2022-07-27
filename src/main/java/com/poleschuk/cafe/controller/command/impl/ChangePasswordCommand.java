package com.poleschuk.cafe.controller.command.impl;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.User;
import com.poleschuk.cafe.service.UserService;
import com.poleschuk.cafe.service.impl.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static com.poleschuk.cafe.controller.Parameter.*;
import static com.poleschuk.cafe.controller.PagePath.SETTINGS_PAGE;
import static com.poleschuk.cafe.controller.PagePath.PASSWORD_UPDATED_PAGE;

import static java.lang.Boolean.TRUE;

/**
 * ChangePasswordCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class ChangePasswordCommand implements Command {
    private final UserService service = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(SETTINGS_PAGE);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        Map<String, String> map = new HashMap<>();
        map.put(OLD_PASSWORD, request.getParameter(OLD_PASSWORD));
        map.put(NEW_PASSWORD, request.getParameter(NEW_PASSWORD));
        map.put(REPEAT_PASSWORD, request.getParameter(REPEAT_PASSWORD));
        try {
            boolean result = service.changePasswordByOldPassword(map, user);
            if (result) {
            	router.setPage(PASSWORD_UPDATED_PAGE);
                router.setRedirect();
                session.setAttribute(USER, user);
            } else{
                for (String key: map.keySet()) {
                    String value = map.get(key);
                    switch (value) {
                        case INVALID_NEW_UNIQ_PASSWORD -> request.setAttribute(INVALID_NEW_UNIQ_PASSWORD, TRUE);
                        case INVALID_NEW_PASSWORD -> request.setAttribute(INVALID_NEW_PASSWORD, TRUE);
                        case INVALID_OLD_PASSWORD -> request.setAttribute(INVALID_OLD_PASSWORD, TRUE);
                        case INVALID_REPEAT_PASSWORD -> request.setAttribute(INVALID_REPEAT_PASSWORD, TRUE);
                    }
                }
            }
        } catch (ServiceException e) {
            throw new CommandException("Exception in a ChangePasswordCommand class ", e);
        }
        return router;
    }
}