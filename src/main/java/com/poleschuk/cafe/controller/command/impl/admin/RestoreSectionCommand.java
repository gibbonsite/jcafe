package com.poleschuk.cafe.controller.command.impl.admin;


import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.RouterType;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.Section;
import com.poleschuk.cafe.service.SectionService;
import com.poleschuk.cafe.service.impl.SectionServiceImpl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import static com.poleschuk.cafe.controller.Parameter.*;


/**
 * RestoreSectionCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class RestoreSectionCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final SectionService sectionService = SectionServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        Router router = new Router(currentPage, RouterType.REDIRECT);
        try {
            logger.log(Level.INFO, request.getParameter(SECTION_ID));
            long sectionId = Long.parseLong(request.getParameter(SECTION_ID));
            logger.log(Level.INFO, "Section ID = " + sectionId);
            if (sectionService.restoreSectionById(sectionId)) {
                ServletContext context = request.getServletContext();
                List<Section> sectionList = sectionService.findAllSections();
                logger.log(Level.INFO, "Section list: " + sectionList);
                context.setAttribute(SECTION_LIST, sectionList);
            }
        } catch (ServiceException e) {
            throw new CommandException("Exception in a RestoreSectionCommand class ", e);
        }
        return router;
    }
}