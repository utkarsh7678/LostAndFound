package com.lostfound.servlet;

import com.lostfound.dao.FoundItemDAO;
import com.lostfound.dao.LostItemDAO;
import com.lostfound.dao.MatchDAO;
import org.bson.Document;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/found/add")
@MultipartConfig
public class FoundItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/pages/foundItem.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = (String) req.getSession().getAttribute("userEmail");
        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/pages/login.jsp");
            return;
        }

        String itemName = req.getParameter("itemName");
        String description = req.getParameter("description");
        String location = req.getParameter("location");
        String date = req.getParameter("date");

        // image handling: optional - we will use ImageUploadServlet by separate request or handle here
        String imageUrl = req.getParameter("imageUrl"); // if using separate upload, pass the URL

        FoundItemDAO foundDao = new FoundItemDAO();
        String foundId = foundDao.insertFoundItem(userId, itemName, description, location, date, imageUrl);

        // try to match with existing lost items
        LostItemDAO lostDao = new LostItemDAO();
        List<Document> potential = lostDao.search(itemName, location);

        MatchDAO matchDao = new MatchDAO();
        for (Document l : potential) {
            String lostId = l.getObjectId("_id").toHexString();
            matchDao.createMatch(lostId, foundId);
        }

        req.getSession().setAttribute("msg", "Found item reported. Potential matches created: " + potential.size());
        resp.sendRedirect(req.getContextPath() + "/found/list");
    }
}

