
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lost & Found System</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/styles.css" />
</head>
<body>
<div class="navbar">
    <div class="nav-brand">
        <span class="stack">
            <img src="<%= request.getContextPath() %>/assets/ChatGPT Image Aug 29, 2025, 02_34_02 PM.png" alt="Brand"/>
        </span>
        <a href="#" style="color:#fff; text-decoration:none;">Lost &amp; Found</a>
    </div>
    <div class="nav-links">
        <a href="pages/login.jsp">Login</a>
        <a href="pages/register.jsp">Register</a>
    </div>
</div>

<div class="container">
    <div class="card center" style="flex-direction:column; text-align:center; padding:40px; gap:16px;">
        <img src="<%= request.getContextPath() %>/assets/ChatGPT Image Aug 29, 2025, 02_34_02 PM.png" alt="Brand" class="logo-hero"/>
        <h1 class="m-0">Welcome to Lost &amp; Found System</h1>
        <p class="mt-2">Report and discover items with a simple, modern interface.</p>
        <div class="mt-3" style="display:flex; gap:12px;">
            <a class="btn" href="pages/register.jsp">Get Started</a>
            <a class="btn secondary" href="pages/login.jsp">Login</a>
        </div>
    </div>
</div>
</body>
</html>
