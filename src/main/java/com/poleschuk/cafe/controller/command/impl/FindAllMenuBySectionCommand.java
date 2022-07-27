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

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;


import static com.poleschuk.cafe.controller.Parameter.*;

import static com.poleschuk.cafe.controller.PagePath.MENU_PAGE;

/**
 * FindAllMenuBySectionCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class FindAllMenuBySectionCommand implements Command {
    private static final int PAGE_SIZE = 4;
    private final MenuService menuService = MenuServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(MENU_PAGE);
        try {
            long sectionId = Long.parseLong(request.getParameter(SECTION_ID));
            String currentPageParameter = request.getParameter(PAGINATION_PAGE);
            int currentPage = 1;

            if (currentPageParameter != null) {
                currentPage = Integer.parseInt(currentPageParameter);
            }

            int totalRecords = menuService.readRowCountBySection(sectionId);
            PaginationService paginationService = PaginationServiceImpl.getInstance();
            int offset = paginationService.offset(PAGE_SIZE, currentPage);
            List<Menu> menuSublist = menuService.findMenuSublistBySectionId(PAGE_SIZE, offset, sectionId);

            if (menuSublist.isEmpty() && currentPage > 1) {
                currentPage--;
                offset = paginationService.offset(PAGE_SIZE, currentPage);
                menuSublist = menuService.findMenuSublistBySectionId(PAGE_SIZE, offset, sectionId);
            }

            int pages = paginationService.pages(totalRecords, PAGE_SIZE);
            int lastPage = paginationService.lastPage(pages, PAGE_SIZE, totalRecords);

            request.setAttribute(MENU_LIST, menuSublist);
            request.setAttribute(PAGINATION_PAGE, currentPage);
            request.setAttribute(PAGINATION_LAST_PAGE, lastPage);
            StringBuilder builderUrl = new StringBuilder(Command.createURL(request, request.getParameter(COMMAND)));
            builderUrl.append(AMPERSAND_SIGN).append(SECTION_ID).append(EQUAL_SIGN).append(sectionId);
            request.setAttribute(URL, builderUrl.toString());
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a FindAllMenuCommand class", e);
        }
        return router;
    }
}