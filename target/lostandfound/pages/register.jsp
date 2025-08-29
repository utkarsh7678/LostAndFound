<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/styles.css" />
</head>
<body>
<div class="navbar">
    <div class="nav-brand">
        <img src="https://cdn.jsdelivr.net/gh/twitter/twemoji@14.0.2/assets/svg/1f50d.svg" alt="Logo"/>
        <a href="<%= request.getContextPath() %>/index.jsp" style="color:#fff; text-decoration:none;">Lost &amp; Found</a>
    </div>
    <div class="nav-links">
        <a href="login.jsp">Login</a>
    </div>
</div>

<div class="container">
    <div class="card" style="max-width:480px; margin: 40px auto;">
        <h2 class="m-0">Create your account</h2>
        <form class="form mt-3" action="../RegisterServlet" method="post">
            <div>
                <label>Name</label>
                <input class="input" type="text" name="name" required />
            </div>
            <div>
                <label>Email</label>
                <input class="input" type="email" name="email" required />
            </div>
            <div>
                <label>Password</label>
                <input class="input" type="password" name="password" required />
            </div>
            <button class="btn" type="submit">Register</button>
        </form>
    </div>
</div>
</body>
</html>

