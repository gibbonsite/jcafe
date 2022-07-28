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
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import static com.poleschuk.cafe.controller.Parameter.*;

import static com.poleschuk.cafe.controller.PagePath.*;

import static java.lang.Boolean.TRUE;

/**
 * UpdateSectionNameCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class UpdateSectionNameCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final SectionService service = SectionServiceImpl.getInstance();
    private final Validator validator = ValidatorImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        Router router = new Router(currentPage);
        String sectionName = request.getParameter(NEW_SECTION_NAME);
        try {
            boolean result = true;
            if (request.getParameter(PRODUCT_SECTION) == null) {
                request.setAttribute(INVALID_PRODUCT_SECTION, TRUE);
                result = false;
            }

            if (!validator.isCorrectSectionName(sectionName)) {
                request.setAttribute(INVALID_NEW_SECTION_NAME, TRUE);
                result = false;
            }

            long sectionId = Long.parseLong(request.getParameter(PRODUCT_SECTION));
            if (service.findSectionByName(sectionName).isPresent()) {
                Section findSection = service.findSectionByName(sectionName).get();
                if (findSection.getSectionId() != sectionId) {
                    request.setAttribute(NOT_UNIQ_NEW_SECTION_NAME, TRUE);
                    result = false;
                }
            }
            if (!result) {
                return router;
            }
            Optional<Section> oldSection = service.updateSectionName(new Section(sectionId, sectionName, true));
            router.setPage(SECTION_UPDATED_PAGE);
            router.setRedirect();
            if (oldSection.isPresent()) {
                List<Section> sectionList = service.findAllSections();
                context.setAttribute(SECTION_LIST, sectionList);
            }else {
                logger.log(Level.WARN, "Incorrect update section name. Section id = " + sectionId);
                router.setPage(ERROR_500_PAGE);
                return router;
            }
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a UpdateSectionNameCommand class. ", e);
        }
        return router;
    }
}