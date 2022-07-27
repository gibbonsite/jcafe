package com.poleschuk.cafe.controller.command;

import com.poleschuk.cafe.controller.Parameter;
import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.exception.CommandException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Interface Command for handling commands directed to the web application.
 */
@FunctionalInterface
public interface Command {
	/**
	 * Constant QUESTION_MARK for composing command url.
	 */
    String QUESTION_MARK = "?";
	/**
	 * Constant EQUAL_SIGN for composing command url.
	 */
    String EQUAL_SIGN = "=";	
	/**
	 * Constant AMPERSAND_SIGN for composing command url.
	 */
    String AMPERSAND_SIGN = "&";
    
    /**
     * Execute command and get router.
     *
     * @param request the request
     * @return the router provides url/page as view
     * @throws CommandException the command exception
     */
    Router execute(HttpServletRequest request) throws CommandException;

    /**
     * Create url string.
     *
     * @param request     the request
     * @param commandName the command name
     * @return the string (url)
     */
    static String createURL(HttpServletRequest request, String commandName) {
    	StringBuilder url = new StringBuilder();
    	url.append(request.getContextPath());
    	url.append(request.getServletPath());
    	url.append(QUESTION_MARK);
    	url.append(Parameter.COMMAND);
    	url.append(EQUAL_SIGN);
    	url.append(commandName);
        return url.toString();
    }

    /**
     * Create url string.
     *
     * @param page           the JSP page
     * @param parameterName  the parameter name
     * @param parameterValue the parameter value
     * @return the string (url)
     */
    static String createURL(String page, String parameterName,
    		String parameterValue) {
    	StringBuilder url = new StringBuilder();
    	url.append(page);
    	url.append(QUESTION_MARK);
    	url.append(parameterName);
    	url.append(EQUAL_SIGN);
    	url.append(parameterValue);
        return url.toString();
    }
}
