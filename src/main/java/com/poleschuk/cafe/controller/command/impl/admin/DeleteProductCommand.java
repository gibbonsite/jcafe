package com.poleschuk.cafe.controller.command.impl.admin;

import static com.poleschuk.cafe.controller.PagePath.PRODUCT_REMOVED_PAGE;
import static com.poleschuk.cafe.controller.Parameter.PRODUCT_ID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.RouterType;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.service.MenuService;
import com.poleschuk.cafe.service.impl.MenuServiceImpl;

import jakarta.servlet.http.HttpServletRequest;


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
        router.setPage(PRODUCT_REMOVED_PAGE);
        try {
            long id = Long.parseLong(request.getParameter(PRODUCT_ID));
            service.deleteProductById(id);
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a DeleteProductCommand class ", e);
        }
        return router;
    }
}