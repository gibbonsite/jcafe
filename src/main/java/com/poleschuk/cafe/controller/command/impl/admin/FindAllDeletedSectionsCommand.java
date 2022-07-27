package com.poleschuk.cafe.controller.command.impl.admin;


import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.Section;
import com.poleschuk.cafe.service.SectionService;
import com.poleschuk.cafe.service.impl.SectionServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static com.poleschuk.cafe.controller.Parameter.*;
import static com.poleschuk.cafe.controller.PagePath.RESTORE_PAGE;

/**
 * FindAllDeletedSectionsCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class FindAllDeletedSectionsCommand implements Command {
    private final SectionService sectionService = SectionServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(RESTORE_PAGE);
        try {
            List<Section> sectionList = sectionService.findAllDeletedSections();
            request.setAttribute(SECTION_LIST, sectionList);
            request.setAttribute(RESTORE_SECTION, true);
        } catch (ServiceException e) {
            throw new CommandException("Exception in a FindAllRemovingSectionsCommand class ", e);
        }
        return router;
    }
}