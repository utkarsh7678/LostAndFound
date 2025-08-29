<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Report Found Item</title>
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
	<div class="card" style="max-width:640px; margin: 20px auto;">
		<h2 class="m-0">Report Found Item</h2>
		<form class="form mt-3" action="${pageContext.request.contextPath}/found/add" method="post" enctype="multipart/form-data">
			<div>
				<label>Item Name</label>
				<input class="input" type="text" name="itemName" required/>
			</div>
			<div>
				<label>Description</label>
				<textarea class="input" name="description" rows="4" required></textarea>
			</div>
			<div>
				<label>Location</label>
				<input class="input" type="text" name="location" required/>
			</div>
			<div>
				<label>Date (when found)</label>
				<input class="input" type="date" name="date" required/>
			</div>
			<div>
				<label>Image URL (optional)</label>
				<input class="input" type="url" name="imageUrl" placeholder="https://..."/>
			</div>
			<button class="btn" type="submit">Report Found Item</button>
		</form>
	</div>
</div>
</body>
</html>

