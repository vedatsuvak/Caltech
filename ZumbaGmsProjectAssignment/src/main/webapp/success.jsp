<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%
// Check if the action was successful
boolean actionSuccessful = session.getAttribute("actionSuccessful") != null && (boolean) session.getAttribute("actionSuccessful");
boolean userdeleted = session.getAttribute("userdeleted") != null && (boolean) session.getAttribute("userdeleted");
boolean useredited = session.getAttribute("useredited") != null && (boolean) session.getAttribute("useredited");
boolean addbatch = session.getAttribute("addbatch") != null && (boolean) session.getAttribute("addbatch");
boolean deletebatch = session.getAttribute("deletebatch") != null && (boolean) session.getAttribute("deletebatch");
boolean deleteparticipant = session.getAttribute("deleteparticipant") != null && (boolean) session.getAttribute("deleteparticipant");
boolean editbatch = session.getAttribute("editbatch") != null && (boolean) session.getAttribute("editbatch");
boolean startbatch = session.getAttribute("startbatch") != null && (boolean) session.getAttribute("startbatch");
boolean addparticipant = session.getAttribute("addparticipant") != null && (boolean) session.getAttribute("addparticipant");

// Remove the session attribute to prevent direct access to the page via URL
session.removeAttribute("actionSuccessful");
session.removeAttribute("userdeleted");
session.removeAttribute("useredited");
session.removeAttribute("addbatch");
session.removeAttribute("deletebatch");
session.removeAttribute("deleteparticipant");
session.removeAttribute("editbatch");
session.removeAttribute("startbatch");
session.removeAttribute("addparticipant");

//Restrict direct access to page
if (actionSuccessful) {
    // Check if the user is logged in
    boolean isLoggedIn = session.getAttribute("username") != null;
    if (isLoggedIn) { 
    	 RequestDispatcher rd=request.getRequestDispatcher("main.jsp");
    	 rd.include(request, response);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>
    <h3 class="link-container"><i>The action has been done successfully...!!</i></h3>
    <% if (userdeleted){ %>
	<h3>User deleted...!</h3>
	<%} if (useredited){ %>
	<h3>User edited successfully...!</h3>
	<%}  if (addbatch){ %>
	<h3>Batch added successfully...!</h3>
	<%}if (deletebatch){ %>
	<h3>Batch deleted successfully...!</h3>
	<%}if (deleteparticipant){ %>
	<h3>Participant unregistered from Batch successfully...!</h3>
	<%}if (editbatch){ %>
	<h3>Batch edited successfully...!</h3>
	<%}if (startbatch){ %>
	<h3>Batch Started successfully...!</h3>
	<h3>Message Sent to all Participants...!</h3>
	<%}if (addparticipant){ %>
	<h3>Participant added successfully...!</h3>
	<%} %>
	
</body>
</html>
<%
    } else {
        // User is not logged in, redirect to login page
        response.sendRedirect("login.jsp");
    }
} else {
	// Direct access restricted
	response.sendRedirect("main.jsp");
}
%>
