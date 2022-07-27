package com.poleschuk.cafe.controller.command.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.RouterType;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.validator.impl.ValidatorImpl;

import static com.poleschuk.cafe.controller.Parameter.*;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * ChangeLanguageCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class ChangeLanguageCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        Router router = new Router(currentPage, RouterType.REDIRECT);
        String language = request.getParameter(LANGUAGE);
        if (!ValidatorImpl.getInstance()
        		.isCorrectLanguage(language)) {
        	router.setPage(currentPage);
            return router;
        }
        logger.log(Level.INFO, "Language parameter is " + language);
        logger.log(Level.INFO, "Current page is " + currentPage);
        session.setAttribute(LANGUAGE, language);
        return router;

    }
}
