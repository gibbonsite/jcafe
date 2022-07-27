package com.poleschuk.cafe.controller.filter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

import static com.poleschuk.cafe.controller.Parameter.*;

/**
 * CurrentPageFilter class extracts current page url from request.
 */
public class CurrentPageFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();
    private static final String CONTROLLER = "/controller?";
    private static final String QUESTION = "?";
    private static final String JSP = ".jsp";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    		throws ServletException, IOException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpSession session = servletRequest.getSession();
        String requestURI = servletRequest.getRequestURI();
        logger.log(Level.INFO, "request URI: " + requestURI);
        String query = servletRequest.getQueryString();
        if (query == null) {
        	query = "";
        }
        if (servletRequest.getParameter(COMMAND) != null) {
            requestURI = CONTROLLER + query;
        } else {
            requestURI = servletRequest.getServletPath() + (
            		!servletRequest.getServletPath().endsWith(JSP) ? QUESTION + query : "");
        }
        logger.log(Level.INFO, query);
        if (!(servletRequest.getParameter(COMMAND) != null &&
        			CurrentPageTransientCommands.transientCommands.contains(
        			servletRequest.getParameter(COMMAND).toUpperCase()))) {
        	session.setAttribute(CURRENT_PAGE, requestURI);
        }
        chain.doFilter(request, response);
    }
}