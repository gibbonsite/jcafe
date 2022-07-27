package com.poleschuk.cafe.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.poleschuk.cafe.controller.Parameter.IMAGE_PATH;

/**
 * DownloadProductPhotoController class handles image download requests
 * from client.
 */
@WebServlet(urlPatterns = {"/download_image"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 25)
public class DownloadProductPhotoController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        String path = request.getParameter(IMAGE_PATH);
        byte[] imageBytes = Files.readAllBytes(Paths.get(path));
        response.setContentLength(imageBytes.length);
        response.getOutputStream().write(imageBytes);
    }
}