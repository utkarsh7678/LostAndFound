
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.bson.Document" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Chat</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/styles.css" />
    <script>
        function sendMessage() {
            const msg = document.getElementById('msg').value;
            const matchId = '<%= ((Document)request.getAttribute("match")).getObjectId("_id").toHexString() %>';
            if (!msg) return;
            const xhr = new XMLHttpRequest();
            xhr.open('POST', '<%= request.getContextPath() %>/chat', true);
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xhr.onload = function () { if (xhr.status === 200) { location.reload(); } };
            xhr.send('matchId=' + encodeURIComponent(matchId) + '&message=' + encodeURIComponent(msg));
        }
    </script>
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
        <a href="<%= request.getContextPath() %>/matches">Back to Matches</a>
    </div>
</div>

<div class="container">
    <div class="card" style="max-width:760px; margin: 20px auto;">
        <div style="display:flex; align-items:center; gap:12px;">
            <img src="<%= request.getContextPath() %>/assets/chat-icon-logo-template-600w-779557519.webp" alt="Chat" style="width:40px; height:40px;"/>
            <h2 class="m-0">Chat</h2>
        </div>
        <div id="chatBox" class="mt-3" style="border:1px solid #e2e8f0; border-radius:8px; padding:12px; max-height:360px; overflow:auto; background:#fff;">
            <%
                Document match = (Document) request.getAttribute("match");
                List<Document> chat = match.getList("chatHistory", Document.class);
                if (chat == null || chat.isEmpty()) {
            %>
            <div>No messages yet.</div>
            <%
                } else {
                    for (Document c : chat) {
            %>
            <div style="margin:8px 0;">
                <div style="font-weight:600; color:#334155;"><%= c.getString("sender") %></div>
                <div><%= c.getString("message") %></div>
            </div>
            <%
                    }
                }
            %>
        </div>
        <div class="mt-3" style="display:flex; gap:8px;">
            <input class="input" type="text" id="msg" placeholder="Type a message" />
            <button class="btn" onclick="sendMessage()">Send</button>
        </div>
    </div>
</div>
</body>
</html>
