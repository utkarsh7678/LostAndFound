package com.lostfound.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.Document;

import com.lostfound.dao.FoundItemDAO;

@WebServlet("/found/list")
public class FoundListServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		String userEmail = session != null ? (String) session.getAttribute("userEmail") : null;
		if (userEmail == null) {
			resp.sendRedirect(req.getContextPath() + "/pages/login.jsp");
			return;
		}

		FoundItemDAO dao = new FoundItemDAO();
		List<Document> found = dao.getAllFoundItems();

		Object msg = req.getSession().getAttribute("msg");
		if (msg != null) {
			req.setAttribute("msg", msg);
			req.getSession().removeAttribute("msg");
		}

		req.setAttribute("foundItems", found);
		req.getRequestDispatcher("/pages/listFound.jsp").forward(req, resp);
	}
}

