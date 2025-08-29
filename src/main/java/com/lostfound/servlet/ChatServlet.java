package com.lostfound.servlet;

import com.lostfound.dao.DBUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/chat")
public class ChatServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String matchId = req.getParameter("matchId");
        if (matchId == null) {
            resp.sendRedirect(req.getContextPath() + "/matches");
            return;
        }
        MongoDatabase db = DBUtil.getDatabase();
        MongoCollection<Document> col = db.getCollection("matches");
        Document m = col.find(new Document("_id", new ObjectId(matchId))).first();
        req.setAttribute("match", m);
        req.getRequestDispatcher("/pages/chat.jsp").forward(req, resp);
    }

    // Save chat message (AJAX POST)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String matchId = req.getParameter("matchId");
        String message = req.getParameter("message");
        String userEmail = (String) req.getSession().getAttribute("userEmail");
        if (matchId == null || message == null || userEmail == null) {
            resp.setStatus(400);
            return;
        }

        MongoDatabase db = DBUtil.getDatabase();
        MongoCollection<Document> col = db.getCollection("matches");
        Document match = col.find(new Document("_id", new ObjectId(matchId))).first();
        if (match == null) {
            resp.setStatus(404);
            return;
        }
        List<Document> chat = match.getList("chatHistory", Document.class);
        if (chat == null) chat = new ArrayList<>();
        Document chatMsg = new Document("sender", userEmail)
                .append("message", message)
                .append("timestamp", System.currentTimeMillis());
        chat.add(chatMsg);
        match.put("chatHistory", chat);
        col.replaceOne(new Document("_id", new ObjectId(matchId)), match);

        resp.getWriter().write("ok");
    }
}

