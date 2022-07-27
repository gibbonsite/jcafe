package com.poleschuk.cafe.controller.filter;

import com.poleschuk.cafe.controller.command.CommandType;
import com.poleschuk.cafe.controller.filter.permission.UserPermission;
import com.poleschuk.cafe.model.entity.User;
import com.poleschuk.cafe.model.entity.UserRole;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import static com.poleschuk.cafe.controller.Parameter.*;

import static com.poleschuk.cafe.controller.PagePath.ERROR_404_PAGE;
import static com.poleschuk.cafe.controller.PagePath.ERROR_403_PAGE;

/**
 * The CommandSecurityFilter class determines what commands
 * a client can use.
 */
public class CommandSecurityFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.log(Level.INFO, "CommandSecurityFilter - doFilter");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        String command = httpServletRequest.getParameter(COMMAND);
        if (command == null) {
            request.getRequestDispatcher(ERROR_404_PAGE).forward(httpServletRequest, httpServletResponse);
            return;
        }
        UserRole role = UserRole.GUEST;
        Set<String> commands;

        User user = (User) session.getAttribute(USER);
        if (user != null) {
            role = user.getRole();
        }

        switch (role) {
            case ADMIN -> commands = UserPermission.ADMIN.getCommands();
            case CLIENT -> commands = UserPermission.CLIENT.getCommands();
            default -> commands = UserPermission.GUEST.getCommands();
        }

        boolean isCorrect = Arrays.stream(CommandType.values())
                        .anyMatch(commandType -> command.equalsIgnoreCase(commandType.toString()));

        if (!isCorrect) {
            logger.log(Level.INFO, "command = " + command);
            request.getRequestDispatcher(ERROR_404_PAGE)
                    .forward(httpServletRequest, httpServletResponse);
            return;        	
        }
        if (!commands.contains(command.toUpperCase())) {
            logger.log(Level.INFO, "command = " + command);
            request.getRequestDispatcher(ERROR_403_PAGE)
                    .forward(httpServletRequest, httpServletResponse);
            return;
        }

        logger.log(Level.INFO, "Chain continue");
        chain.doFilter(request, response);
    }

}