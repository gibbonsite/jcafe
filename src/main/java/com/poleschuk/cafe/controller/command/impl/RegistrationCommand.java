package com.poleschuk.cafe.controller.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.User;
import com.poleschuk.cafe.model.entity.UserRole;
import com.poleschuk.cafe.service.UserService;
import com.poleschuk.cafe.service.impl.UserServiceImpl;

import static com.poleschuk.cafe.controller.PagePath.*;
import static com.poleschuk.cafe.controller.Parameter.*;
import static java.lang.Boolean.TRUE;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * RegistrationCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class RegistrationCommand implements Command {
    private final UserService service = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Map<String, String> mapData = new HashMap<>();
        mapData.put(USER_FIRST_NAME, request.getParameter(USER_FIRST_NAME));
        mapData.put(USER_LAST_NAME, request.getParameter(USER_LAST_NAME));
        mapData.put(LOGIN, request.getParameter(LOGIN));
        mapData.put(PASSWORD, request.getParameter(PASSWORD));
        mapData.put(USER_EMAIL, request.getParameter(USER_EMAIL));
        mapData.put(USER_PHONE_NUMBER, request.getParameter(USER_PHONE_NUMBER));
        mapData.put(USER_BIRTHDAY, request.getParameter(USER_BIRTHDAY));
        mapData.put(REPEAT_PASSWORD, request.getParameter(REPEAT_PASSWORD));
        Router router = new Router(SIGN_PAGE);
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER);
            UserRole role = user != null && user.getRole() == UserRole.ADMIN ?
                    UserRole.ADMIN : UserRole.CLIENT;

            if (service.userRegistration(mapData, role)) {
                router.setRedirect();
                if (role == UserRole.ADMIN) {
                	StringBuilder url = new StringBuilder();
                	url.append(USER_REGISTERED_PAGE);
                	url.append(Command.QUESTION_MARK);
                	url.append(NEXT_PAGE);
                	url.append(Command.EQUAL_SIGN);
                	url.append(session.getAttribute(CURRENT_PAGE));
                	router.setPage(url.toString());
                }else {
                    router.setPage(USER_REGISTERED_PAGE);
                }
            } else {
                for (String key : mapData.keySet()) {
                    String currentValue = mapData.get(key);
                    switch (currentValue) {
                        case INVALID_BIRTHDAY -> request.setAttribute(INVALID_BIRTHDAY, TRUE);
                        case INVALID_FIRST_NAME -> request.setAttribute(INVALID_FIRST_NAME, TRUE);
                        case INVALID_EMAIL -> request.setAttribute(INVALID_EMAIL, TRUE);
                        case INVALID_LAST_NAME -> request.setAttribute(INVALID_LAST_NAME, TRUE);
                        case INVALID_LOGIN -> request.setAttribute(INVALID_LOGIN, TRUE);
                        case INVALID_PASSWORD -> request.setAttribute(INVALID_PASSWORD, TRUE);
                        case INVALID_PHONE_NUMBER -> request.setAttribute(INVALID_PHONE_NUMBER, TRUE);
                        case NOT_UNIQ_EMAIL -> request.setAttribute(INVALID_EMAIL, TRUE);
                        case NOT_UNIQ_LOGIN -> request.setAttribute(INVALID_LOGIN, TRUE);
                        case NOT_UNIQ_PHONE -> request.setAttribute(INVALID_PHONE_NUMBER, TRUE);
                        case INVALID_REPEAT_PASSWORD -> request.setAttribute(INVALID_REPEAT_PASSWORD, TRUE);
                    }
                }
                router.setPage(REGISTRATION_PAGE);
            }
        } catch (ServiceException e) {
            throw new CommandException("Error while registration command", e);
        }
        return router;
    }
}
