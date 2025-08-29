<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String userEmail = (session != null) ? (String) session.getAttribute("userEmail") : null;
    if (userEmail == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/styles.css" />
</head>
<body>
<div class="navbar">
    <div class="nav-brand">
        <span class="stack">
            <img src="<%= request.getContextPath() %>/assets/ChatGPT Image Aug 29, 2025, 02_34_02 PM.png" alt="Brand"/>
        </span>
        <a href="../index.jsp" style="color:#fff; text-decoration:none;">Lost &amp; Found</a>
    </div>
    <div class="nav-links">
        <a href="<%= request.getContextPath() %>/lost/list">Lost Items</a>
        <a href="<%= request.getContextPath() %>/found/list">Found Items</a>
        <a href="<%= request.getContextPath() %>/matches">Matches</a>
        <a href="lostItem.jsp">Report Lost</a>
        <a href="foundItem.jsp">Report Found</a>
        <span style="color:#94a3b8; margin-left:8px;">|</span>
        <span><%= userEmail %></span>
        <form action="../LogoutServlet" method="post" style="margin:0;">
            <button class="btn" type="submit">Logout</button>
        </form>
    </div>
</div>

<div class="container">
    <div class="card">
        <div style="display:flex; align-items:center; gap:12px;">
            <img src="<%= request.getContextPath() %>/assets/ChatGPT Image Aug 29, 2025, 02_34_02 PM.png" alt="Brand" class="logo-header"/>
            <div>
                <h2 class="m-0">Welcome, <%= userEmail %></h2>
                <p class="mt-2">Use the navigation to manage and browse lost and found items.</p>
            </div>
        </div>
        <div class="mt-3">
            <a class="btn" href="<%= request.getContextPath() %>/lost/list">View Lost Items</a>
            <a class="btn secondary" href="<%= request.getContextPath() %>/found/list">View Found Items</a>
        </div>
    </div>
</div>
</body>
</html>
