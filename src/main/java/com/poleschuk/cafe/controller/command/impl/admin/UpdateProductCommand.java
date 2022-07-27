package com.poleschuk.cafe.controller.command.impl.admin;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.Menu;
import com.poleschuk.cafe.service.impl.MenuServiceImpl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.poleschuk.cafe.controller.Parameter.*;

import static java.lang.Boolean.TRUE;

import static com.poleschuk.cafe.controller.PagePath.*;


/**
 * UpdateProductCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class UpdateProductCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final MenuServiceImpl service = MenuServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(UPDATE_PRODUCT_PAGE);
        Map<String, String> map = new HashMap<>();
        map.put(PRODUCT_NAME, request.getParameter(PRODUCT_NAME));
        logger.log(Level.INFO, request.getParameter(PRODUCT_NAME));
        map.put(PRODUCT_DESCRIPTION, request.getParameter(PRODUCT_DESCRIPTION));
        map.put(PRODUCT_WEIGHT, request.getParameter(PRODUCT_WEIGHT));
        map.put(PRODUCT_LOYAL_SCORE, request.getParameter(PRODUCT_LOYAL_SCORE));
        map.put(PRODUCT_PRICE, request. getParameter(PRODUCT_PRICE));
        map.put(PRODUCT_SECTION, request.getParameter(PRODUCT_SECTION));
        try {
            long id = Long.parseLong(request.getParameter(PRODUCT_ID));
            if (service.updateProduct(id, map).isPresent()) {
                router.setPage(PRODUCT_UPDATED_PAGE);
                router.setRedirect();
            } else {
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
                Optional<Menu> menu = service.findProductById(id);
                if (menu.isEmpty()) {
                    router.setRedirect();
                    router.setPage(ERROR_500_PAGE);
                    return router;
                }
                request.setAttribute(PRODUCT_MENU, menu.get());
            }
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a UpdateProductCommand class ", e);
        }
        return router;
    }
}