package com.lostfound.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lostfound.dao.UserDAO;
import com.lostfound.models.User;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Register Servlet Test</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Register Servlet is working!</h1>");
            out.println("<p>This is a test page to verify the servlet is accessible.</p>");
            out.println("<p>Database status: " + (com.lostfound.dao.DBUtil.isConnected() ? "Connected" : "Disconnected") + "</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            try {
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                // Validate input parameters
                if (name == null || name.trim().isEmpty() || 
                    email == null || email.trim().isEmpty() || 
                    password == null || password.trim().isEmpty()) {
                    out.println("<!DOCTYPE html>");
                    out.println("<html><head><title>Registration Error</title></head>");
                    out.println("<body><h1>Registration Error</h1>");
                    out.println("<p>All fields are required.</p>");
                    out.println("<a href='pages/register.jsp'>Go back to registration</a>");
                    out.println("</body></html>");
                    return;
                }

                User user = new User(name.trim(), email.trim(), password);
                UserDAO dao = new UserDAO();
                dao.registerUser(user);

                // Success - redirect to login
                response.sendRedirect("pages/login.jsp");
                
            } catch (Exception e) {
                // Log the error
                System.err.println("❌ Error in RegisterServlet: " + e.getMessage());
                e.printStackTrace();
                
                // Show user-friendly error page
                out.println("<!DOCTYPE html>");
                out.println("<html><head><title>Registration Error</title></head>");
                out.println("<body><h1>Registration Error</h1>");
                out.println("<p>Sorry, an error occurred during registration.</p>");
                out.println("<p>Error: " + e.getMessage() + "</p>");
                out.println("<a href='pages/register.jsp'>Go back to registration</a>");
                out.println("</body></html>");
            }
        }
    }
}
