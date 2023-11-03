<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>error</title>
</head>
<%
//Check if the action was successful
boolean actionSuccessful = session.getAttribute("actionSuccessful") != null && (boolean) session.getAttribute("actionSuccessful");
boolean register = session.getAttribute("register") != null && (boolean) session.getAttribute("register");
//Remove the session attribute to prevent direct access to the page via URL
session.removeAttribute("actionSuccessful");
session.removeAttribute("register");
//Restrict direct access to page
if (actionSuccessful) {
	 // Check if the user is logged in
	 boolean isLoggedIn = session.getAttribute("username") != null;
	 if (isLoggedIn) { 
%>
<body>
	<h2>Something went wrong...!</h2>
	<% if (register){ %>
	<h3>User already registered...!</h3>
	<%} %>
</body>
<%
 	} else {
    	response.sendRedirect("login.jsp");
 	}
} else {
	// Direct access restricted
	response.sendRedirect("main.jsp");
}
%>
</html>