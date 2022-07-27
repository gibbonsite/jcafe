package com.poleschuk.cafe.controller.filter;


import com.poleschuk.cafe.controller.filter.permission.PagePermission;
import com.poleschuk.cafe.model.entity.User;
import com.poleschuk.cafe.model.entity.UserRole;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

import static com.poleschuk.cafe.controller.Parameter.USER;
import static com.poleschuk.cafe.controller.PagePath.START_PAGE;
import static com.poleschuk.cafe.controller.PagePath.ERROR_403_PAGE;

/**
 * PageSecurityFilter controls access to JSP pages.
 */
public class PageSecurityFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        String requestURI = httpRequest.getServletPath();
        logger.log(Level.INFO, "Page URI: " + requestURI);

        UserRole userRole = UserRole.GUEST;
        User user = (User) session.getAttribute(USER);
        if (user != null) {
            userRole = user.getRole();
            logger.log(Level.INFO, userRole.toString());
        }
        logger.log(Level.INFO, userRole);
        boolean isCorrect;
        Set<String> pages;
        switch (userRole) {
            case ADMIN -> {
                pages = PagePermission.ADMIN.getUserPages();
                logger.log(Level.INFO, pages);
                isCorrect = pages.stream().anyMatch(requestURI::contains);
            }
            case CLIENT -> {
                pages = PagePermission.CLIENT.getUserPages();
                logger.log(Level.INFO, pages);
                isCorrect = pages.stream().anyMatch(requestURI::contains);
            }
            default -> {
                pages = PagePermission.GUEST.getUserPages();
                isCorrect = pages.stream().anyMatch(requestURI::contains);
            }
        }
        if (!isCorrect && user == null) {
            user = new User();
            user.setRole(UserRole.GUEST);
            session.setAttribute(USER, user);
            httpResponse.sendRedirect(httpRequest.getContextPath() + START_PAGE);
            return;
        }else if (!isCorrect) {
        	request.getRequestDispatcher(ERROR_403_PAGE).forward(request, response);
            return;
        }
        chain.doFilter(request, response);
    }
}