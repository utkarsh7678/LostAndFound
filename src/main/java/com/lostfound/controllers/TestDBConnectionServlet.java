package com.lostfound.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lostfound.dao.DBUtil;

@WebServlet("/TestDBConnection")
public class TestDBConnectionServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Database Connection Test</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Database Connection Test</h1>");
            
            try {
                out.println("<p>Testing MongoDB connection...</p>");
                out.println("<p>Step 1: Attempting to get database connection...</p>");
                
                // Test the connection step by step
                out.println("<p>Step 2: Calling DBUtil.getDatabase()...</p>");
                var database = DBUtil.getDatabase();
                
                out.println("<p>Step 3: Testing ping command...</p>");
                var pingResult = database.runCommand(new org.bson.Document("ping", 1));
                
                out.println("<p>✅ <strong>SUCCESS!</strong> MongoDB connection is working!</p>");
                out.println("<p>Database name: " + database.getName() + "</p>");
                out.println("<p>Ping result: " + pingResult.toJson() + "</p>");
                
                out.println("<p>Step 4: Listing collections...</p>");
                out.println("<ul>");
                for (String collectionName : database.listCollectionNames()) {
                    out.println("<li>" + collectionName + "</li>");
                }
                out.println("</ul>");
                
            } catch (Exception e) {
                out.println("<p>❌ <strong>ERROR!</strong> MongoDB connection failed!</p>");
                out.println("<p>Error: " + e.getMessage() + "</p>");
                out.println("<p>Error type: " + e.getClass().getSimpleName() + "</p>");
                
                if (e.getCause() != null) {
                    out.println("<p>Caused by: " + e.getCause().getMessage() + "</p>");
                    out.println("<p>Cause type: " + e.getCause().getClass().getSimpleName() + "</p>");
                }
                
                out.println("<h3>Stack Trace:</h3>");
                out.println("<pre>");
                e.printStackTrace(out);
                out.println("</pre>");
            }
            
            out.println("<hr>");
            out.println("<p><a href='index.jsp'>Back to Home</a></p>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
