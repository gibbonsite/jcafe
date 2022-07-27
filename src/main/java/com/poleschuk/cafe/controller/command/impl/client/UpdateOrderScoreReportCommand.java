package com.poleschuk.cafe.controller.command.impl.client;


import static com.poleschuk.cafe.controller.PagePath.ORDER_SCORE_REPORT_FAILED;
import static com.poleschuk.cafe.controller.PagePath.ORDER_SCORE_REPORT_UPDATED;
import static com.poleschuk.cafe.controller.Parameter.ORDER_ID;
import static com.poleschuk.cafe.controller.Parameter.ORDER_REPORT;
import static com.poleschuk.cafe.controller.Parameter.ORDER_SCORE;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.service.OrderService;
import com.poleschuk.cafe.service.impl.OrderServiceImpl;

import jakarta.servlet.http.HttpServletRequest;


/**
 * UpdateOrderScoreReportCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class UpdateOrderScoreReportCommand implements Command {
	private static final Logger logger = LogManager.getLogger();
    private final OrderService service = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(ORDER_SCORE_REPORT_UPDATED);
        boolean result = false;
        try {
            long orderId = Long.parseLong(request.getParameter(ORDER_ID));
            if (service.changeOrderScoreReport(orderId, request.getParameter(ORDER_SCORE),
            		request.getParameter(ORDER_REPORT))) {
                router.setRedirect();
                result = true;
            }
        } catch (ServiceException | NumberFormatException e) {
            logger.info("Exception in a UpdateOrderScoreReportCommand class: " + e.getMessage());
        }
        if (!result) {
        	router.setUrl(Command.createURL(ORDER_SCORE_REPORT_FAILED, ORDER_ID, request.getParameter(ORDER_ID)));
        	router.setRedirect();
        }
        return router;
    }
}