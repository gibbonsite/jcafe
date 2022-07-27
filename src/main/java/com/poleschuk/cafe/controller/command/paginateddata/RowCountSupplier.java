package com.poleschuk.cafe.controller.command.paginateddata;

import com.poleschuk.cafe.exception.ServiceException;

@FunctionalInterface
public interface RowCountSupplier {
	int getDataRowCount() throws ServiceException;
}
