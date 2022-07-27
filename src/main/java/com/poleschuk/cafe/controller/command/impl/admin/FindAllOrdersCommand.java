package com.poleschuk.cafe.controller.command.impl.admin;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.controller.command.paginateddata.FindPaginatedDataCommand;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.CompleteOrder;
import com.poleschuk.cafe.model.entity.OrderScoreReport;
import com.poleschuk.cafe.service.OrderService;
import com.poleschuk.cafe.service.impl.OrderServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import static com.poleschuk.cafe.controller.PagePath.ORDERS_PAGE;
import static com.poleschuk.cafe.controller.Parameter.ORDER_LIST;
import static com.poleschuk.cafe.controller.Parameter.ORDER_REPORTS;

/**
 * FindAllOrdersCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class FindAllOrdersCommand implements FindPaginatedDataCommand<CompleteOrder>, Command {
    private final OrderService orderService = OrderServiceImpl.getInstance();
    private final int PAGE_SIZE = 15;
    
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(ORDERS_PAGE);
        
    	fillWithPaginatedData(request, PAGE_SIZE, ORDER_LIST, orderService::findOrderSublist,
    			orderService::readRowCount);
    	
        try {
            List<OrderScoreReport> orderScoreReports = orderService.findAllOrderScoreReports();
            var orderReportsMap = new HashMap<Long, OrderScoreReport>();
            for (OrderScoreReport orderScoreReport : orderScoreReports) {
            	orderReportsMap.put(orderScoreReport.getOrderId(), orderScoreReport);
            }
            request.setAttribute(ORDER_REPORTS, orderReportsMap);
        } catch (ServiceException e) {
            throw new CommandException("Exception in a FindAllOrdersCommand class.", e);
        }
        return router;
    }
}