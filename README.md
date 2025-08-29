# 📦 Lost & Found System  

<p align="center">
  <img src="https://github.com/utkarsh7678/LostAndFound/blob/main/src/main/webapp/assets/ChatGPT%20Image%20Aug%2029%2C%202025%2C%2002_34_02%20PM.png?raw=true" alt="Project Logo" width="200" height="200">
</p>

A **Java/JSP web application** to report lost/found items, browse lists, and view automatic matches, with a simple chat for matched items.  
Built with **Servlet/JSP, MongoDB, and Maven**; deployable on **Apache Tomcat**.  

---

## ✨ Features  
- 🔑 User registration and login  
- 🏠 Dashboard with navigation  
- 📌 Report **Lost** and **Found** items (supports Image URLs)  
- 🖼️ View lists of Lost and Found items with thumbnails  
- 🤝 Automatic match generation between Lost and Found items  
- 💬 Matches page with link to a simple Chat view  

---

## 🛠️ Tech Stack  
- ☕ Java 17, Servlet/JSP (Tomcat target)  
- 🍃 MongoDB Java Driver (Sync)  
- 🛠️ Maven (WAR packaging)  
- 📑 JSTL for JSPs  
- 📝 Logback/SLF4J for logging  

---

## 📂 Project Structure  
src/main/java/com/lostfound/
├── controllers/ # Auth & utility servlets (Login, Register, Logout)
├── servlet/ # App feature servlets (lost, found, matches, chat)
├── dao/ # Mongo access (DBUtil, LostItemDAO, FoundItemDAO, MatchDAO, UserDAO)
├── models/ # Simple POJOs
src/main/webapp/
├── index.jsp # Landing
├── pages/ # App pages (dashboard, forms, lists, matches, chat)
├── assets/ # Images & logos
├── styles.css # Shared styling
└── WEB-INF/web.xml

---

## ✅ Prerequisites  
- ☕ **JDK 17+**  
- ⚙️ **Maven 3.8+**  
- 🌐 **Apache Tomcat 9.x** (or compatible Servlet 4.0 container)  
- 🍃 **MongoDB Atlas** (recommended) or a local MongoDB instance  

---

## ⚙️ Configuration  

The app reads `MONGODB_URI` from the environment if set; otherwise it uses a fallback in `DBUtil`.  

Set an environment variable:  
```bash
MONGODB_URI=mongodb+srv://<USERNAME>:<PASSWORD>@<CLUSTER>/<DATABASE>?retryWrites=true&w=majority

🏗️ Build

From the project root:
mvn clean package
WAR file will be created at:
target/lostandfound.war
🚀 Deployment (Tomcat)

1.Stop Tomcat (if running).

2.Delete old app folder:

<TOMCAT>/webapps/lostandfound

3.Copy the new WAR:

target/lostandfound.war → <TOMCAT>/webapps/

4.Start Tomcat – it will auto-deploy.

5.Open in browser:

http://localhost:8080/lostandfound/

🔗 Key URLs

.🏠 Landing: /lostandfound/

.📝 Register: /lostandfound/pages/register.jsp

.🔑 Login: /lostandfound/pages/login.jsp

.📊 Dashboard: /lostandfound/pages/dashboard.jsp

.📌 Report Lost: /lostandfound/pages/lostItem.jsp → POST /lostandfound/lost/add

.🎒 Report Found: /lostandfound/pages/foundItem.jsp → POST /lostandfound/found/add

.📋 Lost List: /lostandfound/lost/list

.📋 Found List: /lostandfound/found/list

.🤝 Matches: /lostandfound/matches

.💬 Chat: /lostandfound/chat?matchId=<MATCH_ID>

🗄️ Data Model (MongoDB)

.users → { name, email, password }

.lostitems → { userId, itemName, description, location, date, imageUrl?, createdAt }

.founditems → { userId, itemName, description, location, date, imageUrl?, createdAt }

.matches → { lostItemId, foundItemId, status, chatHistory? }

ℹ️ userId = email.
Matches auto-created in LostItemServlet via regex matching on name/location.

🔒 Security & Environment

.Always set MONGODB_URI instead of hardcoding credentials.

.Ensure Atlas IP Access List includes your server IP.

.Assign proper DB roles (read/write only to app DB).

📝 Logging

.Config: src/main/resources/logback.xml

.Logs: console + rotating files under Tomcat logs/.

💡 Development Tips

.Rebuild & redeploy WAR after changes.

.If JSPs are cached, delete exploded folder webapps/lostandfound before redeployment.

👨‍💻 Author

.Utkarsh Kumar 🚀
