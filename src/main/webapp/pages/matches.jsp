
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List"%>
<%@ page import="org.bson.Document"%>
<html>
<head>
    <title>Matches</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/styles.css" />
</head>
<body>
<div class="navbar">
    <div class="nav-brand">
        <img src="https://cdn.jsdelivr.net/gh/twitter/twemoji@14.0.2/assets/svg/1f50d.svg" alt="Logo"/>
        <a href="<%= request.getContextPath() %>/pages/dashboard.jsp" style="color:#fff; text-decoration:none;">Lost &amp; Found</a>
    </div>
    <div class="nav-links">
        <a href="<%= request.getContextPath() %>/lost/list">Lost Items</a>
        <a href="<%= request.getContextPath() %>/found/list">Found Items</a>
        <a href="<%= request.getContextPath() %>/matches">Matches</a>
    </div>
</div>

<div class="container">
    <h2 class="m-0">Matches</h2>
    <%
        List<Document> matches = (List<Document>) request.getAttribute("matches");
        if (matches == null || matches.isEmpty()) {
    %>
    <div class="card mt-3">No matches found.</div>
    <%
        } else {
    %>
    <table class="table mt-3">
        <thead>
        <tr><th>Matched (Lost)</th><th>Matched (Found)</th><th>Status</th><th>Action</th></tr>
        </thead>
        <tbody>
        <%
            for (Document m : matches) {
                Document lost = (Document) m.get("lost");
                Document found = (Document) m.get("found");
        %>
        <tr>
            <td>
                <b><%= lost != null ? lost.getString("itemName") : "N/A" %></b><br/>
                <%= lost != null ? lost.getString("location") : "" %>
            </td>
            <td>
                <b><%= found != null ? found.getString("itemName") : "N/A" %></b><br/>
                <%= found != null ? found.getString("location") : "" %>
            </td>
            <td><%= m.getString("status") %></td>
            <td>
                <a class="btn" href="${pageContext.request.contextPath}/chat?matchId=<%= m.getString("matchId") %>">Open Chat</a>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <%
        }
    %>
</div>
</body>
</html>
