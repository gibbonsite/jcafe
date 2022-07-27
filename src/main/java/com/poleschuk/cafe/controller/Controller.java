package com.poleschuk.cafe.controller;


import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.controller.command.CommandType;
import com.poleschuk.cafe.exception.CommandException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * Controller class handles requests from clients.
 * Override doGet and doPost methods.
 */
@WebServlet(name = "ControllerServlet", urlPatterns = {"/controller"})
public class Controller extends HttpServlet {
	private static final Logger logger = LogManager.getLogger();
	private static final String COMMAND = "command";
	
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String commandStr = request.getParameter(COMMAND);
		Command command = CommandType.define(commandStr);
		try {
			Router router = command.execute(request);
			if (router.getType() == RouterType.FORWARD) {
				if (!router.getUrl().isPresent()) {
					request.getRequestDispatcher(router.getPage()).forward(request, response);
				} else {
					request.getRequestDispatcher(router.getUrl().get()).forward(request, response);
				}
			} else {
				if (!router.getUrl().isPresent()) {
					response.sendRedirect(request.getContextPath() + router.getPage());
				} else {
					response.sendRedirect(request.getContextPath() + router.getUrl().get());
				}
			}
		} catch (CommandException e) {
			logger.error("Command execution failed: " + e.getMessage());
			response.sendError(500);
		}
	}
}
