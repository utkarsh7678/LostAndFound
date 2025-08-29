package com.lostfound.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@WebServlet("/upload/image")
@MultipartConfig
public class ImageUploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part filePart = req.getPart("image");
        if (filePart == null || filePart.getSize() == 0) {
            resp.getWriter().write("NOFILE");
            return;
        }

        String uploadsDir = getServletContext().getRealPath("/uploads");
        File uploads = new File(uploadsDir);
        if (!uploads.exists()) uploads.mkdirs();

        String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();
        File file = new File(uploads, fileName);

        try (InputStream in = filePart.getInputStream()) {
            Files.copy(in, file.toPath());
        }

        // return relative URL to caller
        String url = req.getContextPath() + "/uploads/" + fileName;
        resp.getWriter().write(url);
    }
}
