package com.poleschuk.cafe.controller.command.impl;


import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;

import jakarta.servlet.http.HttpServletRequest;

import static com.poleschuk.cafe.controller.PagePath.*;

/**
 * GoToSettingsCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class GoToSettingsCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(SETTINGS_PAGE);
        return router;
    }
}