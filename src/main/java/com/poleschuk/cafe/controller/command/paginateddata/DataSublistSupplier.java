package com.poleschuk.cafe.controller.command.paginateddata;

import java.util.List;

import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.AbstractEntity;

@FunctionalInterface
public interface DataSublistSupplier<T extends AbstractEntity> {
	List<T> findDataSublist(int pageSize, int offset) throws ServiceException;
}
