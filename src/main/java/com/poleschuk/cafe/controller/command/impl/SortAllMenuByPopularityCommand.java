package com.poleschuk.cafe.controller.command.impl;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.Menu;
import com.poleschuk.cafe.service.MenuService;
import com.poleschuk.cafe.service.PaginationService;
import com.poleschuk.cafe.service.impl.MenuServiceImpl;
import com.poleschuk.cafe.service.impl.PaginationServiceImpl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;

import static com.poleschuk.cafe.controller.Parameter.*;
import static com.poleschuk.cafe.controller.PagePath.MENU_PAGE;

/**
 * SortAllMenuByPopularityCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class SortAllMenuByPopularityCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final int PAGE_SIZE = 4;
    private final MenuService menuService = MenuServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(MENU_PAGE);
        String currentPageParameter = request.getParameter(PAGINATION_PAGE);
        String sectionId = request.getParameter(SECTION_ID);
        int currentPage = 1;
        if (currentPageParameter != null) {
            currentPage = Integer.parseInt(currentPageParameter);
        }

        PaginationService paginationService = PaginationServiceImpl.getInstance();
        int offset = paginationService.offset(PAGE_SIZE, currentPage);
        int totalRecords;
        try {
            List<Menu> menuSublist;
            StringBuilder builderUrl;
            if (sectionId == null) {
                menuSublist = menuService.findSortedMenuSubListByPopularity(PAGE_SIZE, offset);
                logger.log(Level.INFO, menuSublist);
                if (menuSublist.isEmpty() && currentPage > 1) {
                    currentPage--;
                    offset = paginationService.offset(PAGE_SIZE, currentPage);
                    menuSublist = menuService.findSortedMenuSubListByPopularity(PAGE_SIZE, offset);
                    logger.log(Level.INFO, menuSublist);
                }
                totalRecords = menuService.readRowCount();
                builderUrl = new StringBuilder(Command.createURL(request, request.getParameter(COMMAND)));
            } else {
                long id = Long.parseLong(sectionId);
                logger.log(Level.INFO, "id = " + id);
                menuSublist = menuService.findSortedMenuSectionSubListByPopularity(PAGE_SIZE, offset, id);
                if (menuSublist.isEmpty() && currentPage > 1) {
                    currentPage--;
                    offset = paginationService.offset(PAGE_SIZE, currentPage);
                    menuSublist = menuService.findSortedMenuSectionSubListByPopularity(PAGE_SIZE, offset, id);
                }
                totalRecords = menuService.readRowCountBySection(id);
                builderUrl = new StringBuilder(Command.createURL(request, request.getParameter(COMMAND)));
                builderUrl.append(AMPERSAND_SIGN).append(SECTION_ID).append(EQUAL_SIGN).append(sectionId);
            }

            int pages = paginationService.pages(totalRecords, PAGE_SIZE);
            int lastPage = paginationService.lastPage(pages, PAGE_SIZE, totalRecords);
            request.setAttribute(MENU_LIST, menuSublist);
            request.setAttribute(PAGINATION_PAGE, currentPage);
            request.setAttribute(PAGINATION_LAST_PAGE, lastPage);
            request.setAttribute(URL, builderUrl.toString());
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a SortAllMenuByPriceCommand class. ", e);
        }
        return router;
    }
}