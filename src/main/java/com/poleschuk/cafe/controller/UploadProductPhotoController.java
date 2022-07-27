package com.poleschuk.cafe.controller;

import static com.poleschuk.cafe.controller.Parameter.PICTURE_PATH;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.controller.command.CommandType;
import com.poleschuk.cafe.controller.command.impl.admin.UploadProductPhotoCommand;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.model.pool.ConnectionPool;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "UploadProductPhotoControllerServlet", urlPatterns = {"/upload_product_photo"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
maxFileSize = 1024 * 1024 * 5,
maxRequestSize = 1024 * 1024 * 25)
public class UploadProductPhotoController extends HttpServlet {
	private static final Logger logger = LogManager.getLogger();
	private static final String COMMAND = "command";
	
	@Override
	public void init() throws ServletException {
		ConnectionPool.getInstance();
		logger.log(Level.INFO, "Connection pool is created");
	}

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Command command = new UploadProductPhotoCommand();
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
