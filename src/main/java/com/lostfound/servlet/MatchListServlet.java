package com.lostfound.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import com.lostfound.dao.DBUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

@WebServlet("/matches")
public class MatchListServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userEmail = (String) req.getSession().getAttribute("userEmail");
		if (userEmail == null) {
			resp.sendRedirect(req.getContextPath() + "/pages/login.jsp");
			return;
		}

		MongoDatabase db = DBUtil.getDatabase();
		MongoCollection<Document> matchesCol = db.getCollection("matches");
		MongoCollection<Document> lostCol = db.getCollection("lostitems");
		MongoCollection<Document> foundCol = db.getCollection("founditems");

		List<Document> out = new ArrayList<>();
		for (Document m : matchesCol.find()) {
			String lostId = m.getString("lostItemId");
			String foundId = m.getString("foundItemId");
			Document lost = null;
			Document found = null;
			try {
				lost = lostCol.find(Filters.eq("_id", new org.bson.types.ObjectId(lostId))).first();
			} catch (Exception ignored) {}
			try {
				found = foundCol.find(Filters.eq("_id", new org.bson.types.ObjectId(foundId))).first();
			} catch (Exception ignored) {}

			Document copy = new Document();
			copy.append("matchId", m.getObjectId("_id").toHexString());
			copy.append("lost", lost);
			copy.append("found", found);
			copy.append("status", m.getString("status"));
			out.add(copy);
		}

		req.setAttribute("matches", out);
		req.getRequestDispatcher("/pages/matches.jsp").forward(req, resp);
	}
}

