package com.poleschuk.cafe.controller.command.impl.client;

import static com.poleschuk.cafe.controller.PagePath.ORDER_SCORE_REPORT_PAGE;
import static com.poleschuk.cafe.controller.Parameter.*;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.Order;
import com.poleschuk.cafe.model.entity.OrderScoreReport;
import com.poleschuk.cafe.model.entity.User;
import com.poleschuk.cafe.service.OrderService;
import com.poleschuk.cafe.service.impl.OrderServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class GoToOrderScoreReportPageCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final OrderService service = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(ORDER_SCORE_REPORT_PAGE);
        try {
        	long orderId = Long.parseLong(request.getParameter(ORDER_ID));
            request.setAttribute(ORDER_ID, request.getParameter(ORDER_ID));
        	Optional<OrderScoreReport> optionalOrderScoreReport = service.findOrderScoreReport(orderId);
        	if (optionalOrderScoreReport.isPresent()) {
        		OrderScoreReport orderScoreReport = optionalOrderScoreReport.get();
	            logger.log(Level.INFO, "Order score and report: " + orderScoreReport);
	            request.setAttribute(ORDER_SCORE, orderScoreReport.getScore());
	            request.setAttribute(ORDER_REPORT, orderScoreReport.getReport());
        	}
        	HttpSession session = request.getSession();
        	session.setAttribute(CURRENT_PAGE, ORDER_SCORE_REPORT_PAGE);
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a GoToOrderReportPageCommand class. ", e);
        }
        return router;
    }

}
