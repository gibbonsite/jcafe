package com.poleschuk.cafe.controller.command.impl;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * DefaultCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class DefaultCommand implements Command {
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		return null;
	}

}
