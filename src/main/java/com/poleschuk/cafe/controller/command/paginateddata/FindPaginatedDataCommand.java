package com.poleschuk.cafe.controller.command.paginateddata;

import static com.poleschuk.cafe.controller.Parameter.COMMAND;
import static com.poleschuk.cafe.controller.Parameter.PAGINATION_LAST_PAGE;
import static com.poleschuk.cafe.controller.Parameter.PAGINATION_PAGE;
import static com.poleschuk.cafe.controller.Parameter.URL;

import java.util.List;

import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.AbstractEntity;
import com.poleschuk.cafe.service.PaginationService;
import com.poleschuk.cafe.service.impl.PaginationServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

public interface FindPaginatedDataCommand<T extends AbstractEntity> {
	
    default void fillWithPaginatedData(HttpServletRequest request, int pageSize, String dataListAttributeName,
    		DataSublistSupplier<T> findDataSublist, RowCountSupplier getDataRowCount) throws CommandException {
	
	    try {
	        String currentPageParameter = request.getParameter(PAGINATION_PAGE);
	        int currentPage = 1;
	        if (currentPageParameter != null) {
	            currentPage = Integer.parseInt(currentPageParameter);
	        }
	
	        PaginationService paginationService = PaginationServiceImpl.getInstance();
	        int offset = paginationService.offset(pageSize, currentPage);
	        
	        List<T> dataSublist = findDataSublist.findDataSublist(pageSize, offset);
	        if (dataSublist.isEmpty() && currentPage > 1) {
	            currentPage--;
	            offset = paginationService.offset(pageSize, currentPage);
		        dataSublist = findDataSublist.findDataSublist(pageSize, offset);
	        }
	        
	        int totalRecords = getDataRowCount.getDataRowCount();
	        int pages = paginationService.pages(totalRecords, pageSize);
	        int lastPage = paginationService.lastPage(pages, pageSize, totalRecords);
	
	        request.setAttribute(dataListAttributeName, dataSublist);
	        request.setAttribute(PAGINATION_PAGE, currentPage);
	        request.setAttribute(PAGINATION_LAST_PAGE, lastPage);
	        request.setAttribute(URL, Command.createURL(request, request.getParameter(COMMAND)));
	    } catch (ServiceException | NumberFormatException e) {
	        throw new CommandException(String.format("Exception in a %s command", request.getParameter(COMMAND)), e);
	    }
    }
}
