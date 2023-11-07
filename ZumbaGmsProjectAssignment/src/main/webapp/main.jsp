<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Zumba</title>
	<link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
	<h1 class ="link-container"><i>Welcome to My Application</i></h1>
	<h3 class ="link-container"><i>Zumba Company Inc®</i></h3>
	<% 
	response.setContentType("text/html");
	boolean isLoggedIn = session.getAttribute("username") != null;
	String username = (String) session.getAttribute("username");
	
	//admin
	if (isLoggedIn && username.equalsIgnoreCase("admin")) {
	%>
    <div class="welcome">
        <p>Welcome <%= username.toUpperCase() %>! You are logged in.</p>
    </div>
    <div class="link-container">
        <a href="./Profile">Profile</a>       
        <a href="indexBtc.jsp">Batches</a>
        <a href="registeredBtc.jsp">Registered Batches</a>
        <a href="indexPrt.jsp">All Participants</a>
        <a href="admin.jsp">Show All Users</a>
        <a href="./Logout">Logout</a>
    </div>
	<% 
	//user
	}else if (isLoggedIn && !username.equalsIgnoreCase("admin")) {
	%>
   <div class="welcome">
       <p>Welcome <%= username.toUpperCase() %>! You are logged in.</p>
   </div>
   <div class="link-container">
       <a href="./Profile">Profile</a>       
       <a href="indexBtc.jsp">Batches</a>
       <a href="registeredBtc.jsp">Registered Batches</a>
       <a href="./Logout">Logout</a>
   </div>
	<%
	//not user or not logged in 
	} else {
	%>
   <div class="link-container">
       <a href="login.jsp">Login</a>
       <a href="register.jsp">Register</a>
   </div>
	<%
	}
	%>
</body>
</html>