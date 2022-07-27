package com.poleschuk.cafe.controller.command.impl.admin;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.Section;
import com.poleschuk.cafe.service.SectionService;
import com.poleschuk.cafe.service.impl.SectionServiceImpl;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import static com.poleschuk.cafe.controller.Parameter.*;
import static java.lang.Boolean.TRUE;

/**
 * DeleteSectionCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class DeleteSectionCommand implements Command {
    private final SectionService service = SectionServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        Router router = new Router(currentPage);

        if (request.getParameter(PRODUCT_SECTION) == null) {
            request.setAttribute(INVALID_DELETE_PRODUCT_SECTION, TRUE);
            return router;
        }

        try {
            long sectionId = Long.parseLong(request.getParameter(PRODUCT_SECTION));
            service.deleteSectionById(sectionId);
            List<Section> listSection = service.findAllSections();
            context.setAttribute(SECTION_LIST, listSection);
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a DeleteSectionCommand class. ", e);
        }
        return router;
    }
}