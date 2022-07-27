package com.poleschuk.cafe.controller.command.impl.admin;


import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.Command;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.service.MenuService;
import com.poleschuk.cafe.service.impl.MenuServiceImpl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

import static com.poleschuk.cafe.controller.Parameter.*;

import static com.poleschuk.cafe.controller.PagePath.ERROR_500_PAGE;


/**
 * UploadProductPhotoCommand class implements Command interface and 
 * handles command of the web application. 
 */
public class UploadProductPhotoCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static String imagesPath;
    private static final String FILE_NAME = "config/application.properties";
    private final MenuService service = MenuServiceImpl.getInstance();

    static {
        final Properties properties = new Properties();
        final String IMAGES_PATH_PROPERTY = "images_path";
        String fileProperties = "";

        try {
            ClassLoader loader = UploadProductPhotoCommand.class.getClassLoader();
            URL resource = loader.getResource(FILE_NAME);
            if (resource == null) {
                logger.log(Level.ERROR, "Resource is null! " + FILE_NAME);
                throw new IllegalArgumentException();
            }
            fileProperties = resource.getFile();
            properties.load(new FileReader(fileProperties));
            imagesPath = properties.getProperty(IMAGES_PATH_PROPERTY);
        } catch (IOException e) {
            logger.log(Level.FATAL, "File properties exception: " + fileProperties);
            throw new RuntimeException("File properties exception." + e.getMessage());
        }
    }
	    
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String current_page = (String) session.getAttribute(CURRENT_PAGE);
        Router router = new Router(current_page);
        logger.log(Level.INFO, "Upload photo current page is " + current_page);
        try (InputStream inputStream = request.getPart(PICTURE_PATH).getInputStream()) {
            String submittedFileName = request.getPart(PICTURE_PATH).getSubmittedFileName();
            String path = imagesPath + submittedFileName;
            Path imagePath = new File(path).toPath();
            long bytes = Files.copy(
                    inputStream,
                    imagePath,
                    StandardCopyOption.REPLACE_EXISTING);
            logger.log(Level.INFO, "Upload result is successfully " + bytes + " " + path);
            String name = request.getParameter(PRODUCT_NAME);

            if (!service.updateProductPhoto(path, name)) {
                logger.log(Level.INFO, "Update product photo is failed");
                router.setPage(ERROR_500_PAGE);
                return router;
            }
        } catch (IOException | ServletException | ServiceException e) {
            throw new CommandException("Upload photo failed ", e);
        }
        return router;
    }
}