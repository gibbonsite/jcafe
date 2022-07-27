package com.poleschuk.cafe.service;

/**
 * Interface PaginationService represents business logic of data pagination.
 */
public interface PaginationService {

	/**
	 * Calculate offset.
	 *
	 * @param pageSize the page size
	 * @param currentPage the current page
	 * @return the int
	 */
	int offset(int pageSize, int currentPage);

	/**
	 * Calculate last page.
	 *
	 * @param pages the pages
	 * @param pageSize the page size
	 * @param totalRecords the total records
	 * @return the int
	 */
	int lastPage(int pages, int pageSize, int totalRecords);

	/**
	 * Number of pages.
	 *
	 * @param totalRecords the total records
	 * @param pageSize the page size
	 * @return the int
	 */
	int pages(int totalRecords, int pageSize);

}