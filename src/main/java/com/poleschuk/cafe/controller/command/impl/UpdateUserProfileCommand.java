package com.poleschuk.cafe.controller.command.impl;


import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.RouterType;
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
import java.util.Optional;


import static com.poleschuk.cafe.controller.Parameter.*;
import static com.poleschuk.cafe.controller.PagePath.SETTINGS_UPDATED_PAGE;
import static java.lang.Boolean.TRUE;


/**
 * UpdateUserProfileCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class UpdateUserProfileCommand implements Command {
    private final UserService service = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Map<String, String> updateProfileData = new HashMap<>();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        updateProfileData.put(USER_FIRST_NAME, request.getParameter(USER_FIRST_NAME));
        updateProfileData.put(USER_LAST_NAME, request.getParameter(USER_LAST_NAME));
        updateProfileData.put(USER_EMAIL, request.getParameter(USER_EMAIL));
        updateProfileData.put(USER_PHONE_NUMBER, request.getParameter(USER_PHONE_NUMBER));
        updateProfileData.put(USER_BIRTHDAY, request.getParameter(USER_BIRTHDAY));
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        Router router = new Router(currentPage);
        try {
            Optional<User> optionalUser = service.updateUserProfile(user, updateProfileData);
            if (optionalUser.isPresent()) {
                session.setAttribute(USER, optionalUser.get());
                router.setPage(SETTINGS_UPDATED_PAGE);
                router.setRedirect();
            }else{
                for (String key : updateProfileData.keySet()) {
                    String currentValue = updateProfileData.get(key);
                    switch (currentValue) {
                        case INVALID_BIRTHDAY -> request.setAttribute(INVALID_BIRTHDAY, TRUE);
                        case INVALID_FIRST_NAME -> request.setAttribute(INVALID_FIRST_NAME, TRUE);
                        case INVALID_EMAIL -> request.setAttribute(INVALID_EMAIL, TRUE);
                        case INVALID_LAST_NAME -> request.setAttribute(INVALID_LAST_NAME, TRUE);
                        case INVALID_PHONE_NUMBER -> request.setAttribute(INVALID_PHONE_NUMBER, TRUE);
                        case NOT_UNIQ_EMAIL -> request.setAttribute(INVALID_EMAIL, TRUE);
                        case NOT_UNIQ_PHONE -> request.setAttribute(INVALID_PHONE_NUMBER, TRUE);
                    }
                }
            }
        } catch (ServiceException e) {
            throw new CommandException("Exception in a UpdateUserProfileCommand class", e);
        }
        return router;
    }
}