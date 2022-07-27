package com.poleschuk.cafe.controller.command.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.RouterType;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.*;
import com.poleschuk.cafe.service.SectionService;
import com.poleschuk.cafe.service.UserService;
import com.poleschuk.cafe.service.impl.SectionServiceImpl;
import com.poleschuk.cafe.service.impl.UserServiceImpl;

import static com.poleschuk.cafe.controller.Parameter.*;
import static com.poleschuk.cafe.controller.PagePath.*;
import static java.lang.Boolean.TRUE;

/**
 * SignInCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class SignInCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final UserService userService = UserServiceImpl.getInstance();
    private static final SectionService sectionService = SectionServiceImpl.getInstance();
    
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        Router router = new Router(HOME_PAGE, RouterType.FORWARD);
        String login = request.getParameter(LOGIN);
        String pass = request.getParameter(PASSWORD);
        logger.log(Level.INFO, String.format("login and pass: %s %s", login, pass));
        try {
            Optional<User> optionalUser = userService.authenticate(login, pass);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                logger.log(Level.INFO, "Sign in user with role " + user.getRole());
                switch (user.getRole()) {
                    case ADMIN -> {
                        session.setAttribute(USER, user);
                        List<Section> sectionList = sectionService.findAllSections();
                        context.setAttribute(SECTION_LIST, sectionList);
                        router.setRedirect();
                    }
                    case CLIENT -> {
                        if (user.getState() == UserState.BLOCKED) {
                            request.setAttribute(USER_STATUS_BLOCKED, TRUE);
                            router.setPage(SIGN_PAGE);
                        }else {
                            logger.log(Level.INFO, "Client page");
                            session.setAttribute(USER, user);
                            session.setAttribute(CART, new HashMap<Menu, Integer>());
                            List<Section> sectionList = sectionService.findAllSections();
                            context.setAttribute(SECTION_LIST, sectionList);
                            router.setRedirect();
                        }
                    }
                }
            } else {
                logger.log(Level.INFO, "SignInCommand incorrect login or password");
                request.setAttribute(INCORRECT_LOGIN_PASSWORD, TRUE);
                router.setPage(SIGN_PAGE);
            }
        } catch (ServiceException e) {
            throw new CommandException("Error during sign in", e);
        }
        logger.log(Level.INFO, "SignInCommand");
        return router;
    }
}
