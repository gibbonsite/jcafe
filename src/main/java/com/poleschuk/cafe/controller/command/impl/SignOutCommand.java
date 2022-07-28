package com.poleschuk.cafe.controller.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;

import static com.poleschuk.cafe.controller.Parameter.*;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.RouterType;

import static com.poleschuk.cafe.controller.PagePath.*;

/**
 * SignOutCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class SignOutCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(SIGNED_OUT_PAGE, RouterType.REDIRECT);
        HttpSession session = request.getSession();
        String language = (String) session.getAttribute(LANGUAGE);
        session.invalidate();
        session = request.getSession(true);
        session.setAttribute(LANGUAGE, language);
        return router;
    }
}
