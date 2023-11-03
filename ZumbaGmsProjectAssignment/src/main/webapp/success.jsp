<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%
// Check if the action was successful
boolean actionSuccessful = session.getAttribute("actionSuccessful") != null && (boolean) session.getAttribute("actionSuccessful");
// Remove the session attribute to prevent direct access to the page via URL
session.removeAttribute("actionSuccessful");
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
