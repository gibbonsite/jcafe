package com.poleschuk.cafe.controller.command.impl.admin;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.controller.command.paginateddata.FindPaginatedDataCommand;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.CompleteOrder;
import com.poleschuk.cafe.service.OrderService;
import com.poleschuk.cafe.service.impl.OrderServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import static com.poleschuk.cafe.controller.PagePath.CANCELLED_ORDERS_PAGE;
import static com.poleschuk.cafe.controller.PagePath.CLIENTS_PAGE;
import static com.poleschuk.cafe.controller.Parameter.ORDER_LIST;

/**
 * FindCancelledOrdersCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class FindCancelledOrdersCommand implements FindPaginatedDataCommand<CompleteOrder>, Command {
    private final OrderService orderService = OrderServiceImpl.getInstance();
    private final int PAGE_SIZE = 15;

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(CANCELLED_ORDERS_PAGE);
        
    	fillWithPaginatedData(request, PAGE_SIZE, ORDER_LIST, orderService::findCancelledOrderSublist,
    			orderService::readCancelledOrderRowCount);

    	
    	return router;
    	
    	/*
    	Router router = new Router(CANCELLED_ORDERS_PAGE);
        try {
            List<CompleteOrder> orderList = orderService.findAllCancelledOrders();
            request.setAttribute(ORDER_LIST, orderList);
        } catch (ServiceException e) {
            throw new CommandException("Exception in a FindAllCancelledOrdersCommand class.", e);
        }
        return router;
        */
    }
}