package com.poleschuk.cafe.controller.command.impl.admin;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.RouterType;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.service.MenuService;
import com.poleschuk.cafe.service.impl.MenuServiceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.http.HttpServletRequest;

import static com.poleschuk.cafe.controller.Parameter.*;

import java.util.Optional;

import static com.poleschuk.cafe.controller.CommandPath.FIND_ALL_MENU_URL;


/**
 * DeleteProductCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class DeleteProductCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final MenuService service = MenuServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(RouterType.REDIRECT);
        router.setUrl(FIND_ALL_MENU_URL);
        try {
            long id = Long.parseLong(request.getParameter(PRODUCT_ID));
            service.deleteProductById(id);
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a DeleteProductCommand class ", e);
        }
        return router;
    }
}