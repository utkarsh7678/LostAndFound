package com.lostfound.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import com.lostfound.dao.FoundItemDAO;
import com.lostfound.dao.LostItemDAO;
import com.lostfound.dao.MatchDAO;

@WebServlet("/lost/add")
public class LostItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // show the lost item form
        req.getRequestDispatcher("/pages/lostItem.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // In real app, userId should come from session. For now, accept from a form field or session.
        String userId = (String) req.getSession().getAttribute("userEmail");
        if (userId == null) {
            // not logged in -> redirect to login
            resp.sendRedirect(req.getContextPath() + "/pages/login.jsp");
            return;
        }

        String itemName = req.getParameter("itemName");
        String description = req.getParameter("description");
        String location = req.getParameter("location");
        String date = req.getParameter("date"); // format: yyyy-mm-dd or any
        String imageUrl = req.getParameter("imageUrl");

        // Save lost item
        LostItemDAO lostDao = new LostItemDAO();
        String lostId = lostDao.insertLostItem(userId, itemName, description, location, date, imageUrl);

        // Try to find matching found items
        FoundItemDAO foundDao = new FoundItemDAO();
        List<Document> matches = foundDao.findPotentialMatches(itemName, location);

        MatchDAO matchDAO = new MatchDAO();
        for (Document f : matches) {
            String foundId = f.getObjectId("_id").toHexString();
            // create a match entry for each potential found item
            matchDAO.createMatch(lostId, foundId);
        }

        // set a message to show matches count on next page
        req.getSession().setAttribute("msg", "Lost item reported. Potential matches found: " + matches.size());
        // redirect to list of lost items
        resp.sendRedirect(req.getContextPath() + "/lost/list");
    }
}
