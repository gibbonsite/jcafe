package com.poleschuk.cafe.controller.command.impl.admin;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.service.impl.MenuServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.poleschuk.cafe.controller.Parameter.*;
import static com.poleschuk.cafe.controller.PagePath.ADD_MENU_PAGE;
import static com.poleschuk.cafe.controller.PagePath.PRODUCT_ADDED_PAGE;

import static java.lang.Boolean.TRUE;

/**
 * InsertNewProductCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class InsertNewProductCommand implements Command {
    private static final String DEFAULT_IMAGE = "";
    private final MenuServiceImpl service = MenuServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(ADD_MENU_PAGE);
        Map<String, String> map = new HashMap<>();
        map.put(PRODUCT_NAME, request.getParameter(PRODUCT_NAME));
        map.put(PRODUCT_DESCRIPTION, request.getParameter(PRODUCT_DESCRIPTION));
        map.put(PRODUCT_WEIGHT, request.getParameter(PRODUCT_WEIGHT));
        map.put(PRODUCT_LOYAL_SCORE, request.getParameter(PRODUCT_LOYAL_SCORE));
        map.put(PRODUCT_PRICE, request. getParameter(PRODUCT_PRICE));
        map.put(PRODUCT_SECTION, request.getParameter(PRODUCT_SECTION));
        try {
            if (service.addNewProduct(map, DEFAULT_IMAGE)) {
            	router.setPage(PRODUCT_ADDED_PAGE);
                router.setRedirect();
                return router;
            }
            for (String key : map.keySet()) {
                String value = map.get(key);
                switch (value) {
                    case INVALID_PRODUCT_DESCRIPTION -> request.setAttribute(INVALID_PRODUCT_DESCRIPTION, TRUE);
                    case NOT_UNIQ_PRODUCT_NAME -> request.setAttribute(NOT_UNIQ_PRODUCT_NAME, TRUE);
                    case INVALID_PRODUCT_LOYAL_SCORE -> request.setAttribute(INVALID_PRODUCT_LOYAL_SCORE, TRUE);
                    case INVALID_PRODUCT_NAME -> request.setAttribute(INVALID_PRODUCT_NAME, TRUE);
                    case INVALID_PRODUCT_PRICE -> request.setAttribute(INVALID_PRODUCT_PRICE, TRUE);
                    case INVALID_PRODUCT_WEIGHT -> request.setAttribute(INVALID_PRODUCT_WEIGHT, TRUE);
                    case INVALID_PRODUCT_SECTION -> request.setAttribute(INVALID_PRODUCT_SECTION, TRUE);
                }
            }
        } catch (ServiceException e) {
            throw new CommandException("Exception in a InsertNewProductCommand class ", e);
        }
        return router;
    }
}