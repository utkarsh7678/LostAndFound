
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.bson.Document" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Found Items</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/styles.css" />
</head>
<body>
<div class="navbar">
	<div class="nav-brand">
		<span class="stack">
			<img src="<%= request.getContextPath() %>/assets/ChatGPT Image Aug 29, 2025, 02_34_02 PM.png" alt="Brand"/>
		</span>
		<a href="<%= request.getContextPath() %>/pages/dashboard.jsp" style="color:#fff; text-decoration:none;">Lost &amp; Found</a>
	</div>
	<div class="nav-links">
		<a href="<%= request.getContextPath() %>/lost/list">Lost Items</a>
		<a href="<%= request.getContextPath() %>/found/list">Found Items</a>
		<a href="<%= request.getContextPath() %>/matches">Matches</a>
		<a href="<%= request.getContextPath() %>/pages/lostItem.jsp">Report Lost</a>
		<a href="<%= request.getContextPath() %>/pages/foundItem.jsp">Report Found</a>
	</div>
</div>

<div class="container">
	<div class="card" style="display:flex; align-items:center; gap:12px;">
		<img src="<%= request.getContextPath() %>/assets/ChatGPT Image Aug 29, 2025, 02_34_02 PM.png" alt="Brand" class="logo-header"/>
		<h2 class="m-0">Found Items</h2>
	</div>
	<c:if test="${not empty msg}">
		<div class="card mt-3" style="border-left:4px solid #10b981">${msg}</div>
	</c:if>

	<%
		List<Document> items = (List<Document>) request.getAttribute("foundItems");
		if (items == null || items.isEmpty()) {
	%>
		<div class="card mt-3">No found items reported yet.</div>
	<%
		} else {
	%>
	<table class="table mt-3">
		<thead>
		<tr><th>Item</th><th>Description</th><th>Location</th><th>Date</th><th>Image</th></tr>
		</thead>
		<tbody>
		<%
			for (Document d : items) {
		%>
			<tr>
				<td><%= d.getString("itemName") %></td>
				<td><%= d.getString("description") %></td>
				<td><%= d.getString("location") %></td>
				<td><%= d.getString("date") %></td>
				<td>
					<%
						String img = d.getString("imageUrl");
						if (img != null && !img.isEmpty()) {
					%>
						<img class="w-80" src="<%= img %>" alt="img"/>
					<%
						} else {
							out.print("No image");
						}
					%>
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
