package com.poleschuk.cafe.controller.command.impl.admin;


import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.Section;
import com.poleschuk.cafe.service.SectionService;
import com.poleschuk.cafe.service.impl.SectionServiceImpl;
import com.poleschuk.cafe.validator.Validator;
import com.poleschuk.cafe.validator.impl.ValidatorImpl;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import static com.poleschuk.cafe.controller.Parameter.*;
import static com.poleschuk.cafe.controller.PagePath.*;
import static java.lang.Boolean.TRUE;


/**
 * InsertNewSectionCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class InsertNewSectionCommand implements Command {
    private final SectionService service = SectionServiceImpl.getInstance();
    private final Validator validator = ValidatorImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ServletContext context = request.getServletContext();
        String sectionName = request.getParameter(SECTION_NAME);
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        Router router = new Router(currentPage);
        try {
            if (!validator.isCorrectSectionName(sectionName)) {
                request.setAttribute(INVALID_SECTION_NAME, TRUE);
                return router;
            }

            if (service.findSectionByName(sectionName).isPresent()) {
                request.setAttribute(NOT_UNIQ_SECTION_NAME, TRUE);
                return router;
            }

            service.addNewSection(sectionName);
            List<Section> sectionList = service.findAllSections();
            if (!sectionList.isEmpty()) {
                context.setAttribute(SECTION_LIST, sectionList);
            }

            router.setPage(SECTION_ADDED_PAGE);
            router.setRedirect();
        } catch (ServiceException e) {
            throw new CommandException("Exception in a InsertNewSectionCommand class. ", e);
        }
        return router;
    }
}