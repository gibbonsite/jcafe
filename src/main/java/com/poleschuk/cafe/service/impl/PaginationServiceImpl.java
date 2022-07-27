package com.poleschuk.cafe.service.impl;

import com.poleschuk.cafe.service.PaginationService;

/**
 * PaginationServiceImpl class implements PaginationService interface and contains
 * business logic for number of total pages and offset calculation.
 */
public class PaginationServiceImpl implements PaginationService {
    private static final PaginationService instance = new PaginationServiceImpl();
    private PaginationServiceImpl() {}

    public static PaginationService getInstance() {
        return instance;
    }

    @Override
	public int offset(int pageSize, int currentPage) {
        return pageSize * (currentPage - 1);
    }

    @Override
	public int lastPage(int pages, int pageSize, int totalRecords) {
        return pages * pageSize < totalRecords ? pages + 1 : pages;
    }

    @Override
	public int pages(int totalRecords, int pageSize) {
        return totalRecords / pageSize;
    }
}