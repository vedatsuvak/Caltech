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
boolean addbatch = session.getAttribute("addbatch") != null && (boolean) session.getAttribute("addbatch");
boolean deletebatch = session.getAttribute("deletebatch") != null && (boolean) session.getAttribute("deletebatch");
boolean deleteparticipant = session.getAttribute("deleteparticipant") != null && (boolean) session.getAttribute("deleteparticipant");
boolean editbatch = session.getAttribute("editbatch") != null && (boolean) session.getAttribute("editbatch");
boolean showparticipants = session.getAttribute("showparticipants") != null && (boolean) session.getAttribute("showparticipants");
boolean startbatch = session.getAttribute("startbatch") != null && (boolean) session.getAttribute("startbatch");

//Remove the session attribute to prevent direct access to the page via URL
session.removeAttribute("actionSuccessful");
session.removeAttribute("register");
session.removeAttribute("addbatch");
session.removeAttribute("deletebatch");
session.removeAttribute("deleteparticipant");
session.removeAttribute("editbatch");
session.removeAttribute("showparticipants");
session.removeAttribute("startbatch");

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
	<% if (addbatch){ %>
	<h3>Batch adding failed...!</h3>
	<%}if (deletebatch){ %>
	<h3>Batch deleting failed...!</h3>
	<%}if (deleteparticipant){ %>
	<h3>Participant unregister action failed...!</h3>
	<%}if (editbatch){ %>
	<h3>Editing Batch failed...!</h3>
	<%}if (showparticipants){ %>
	<h3>Listing Participants failed...!</h3>
	<%}if (startbatch){ %>
	<h3>Starting Batch failed...!</h3>
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