package com.lostfound.servlet;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import com.lostfound.dao.DBUtil;
import com.lostfound.dao.LostItemDAO;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@WebServlet("/lost/list")
public class LostListServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userEmail = (String) req.getSession().getAttribute("userEmail");
		if (userEmail == null) {
			resp.sendRedirect(req.getContextPath() + "/pages/login.jsp");
			return;
		}

		LostItemDAO lostDao = new LostItemDAO();
		List<Document> lostItems = lostDao.getAllLostItems();

		// For each lost item, count matches from matches collection
		MongoDatabase db = DBUtil.getDatabase();
		MongoCollection<Document> matchesCol = db.getCollection("matches");

		List<Document> display = lostItems.stream().map(li -> {
			Document copy = new Document(li);
			String lostId = li.getObjectId("_id").toHexString();
			long matchCount = matchesCol.countDocuments(new Document("lostItemId", lostId));
			copy.append("matchCount", matchCount);
			return copy;
		}).collect(Collectors.toList());

		req.setAttribute("lostItems", display);
		// optional msg from session
		Object msg = req.getSession().getAttribute("msg");
		if (msg != null) {
			req.setAttribute("msg", msg);
			req.getSession().removeAttribute("msg");
		}

		req.getRequestDispatcher("/pages/listLost.jsp").forward(req, resp);
	}
}

