<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.gms.dao.BatchDAO" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="com.gms.pojo.Batch" %>
<%
//Check if the user is logged in
boolean isLoggedIn = session.getAttribute("username") != null;
String username = (String) session.getAttribute("username");

//Only Admin
if (isLoggedIn && username.equalsIgnoreCase("admin")) {

	BatchDAO dao = new BatchDAO();
	Batch batch = new Batch();
	int result = 0;
	int bid = 0;
	try {
		bid = Integer.parseInt(request.getParameter("bid"));
		result = dao.startBatch(bid);
		if(result >0){
			session.setAttribute("actionSuccessful", true);
			response.sendRedirect("success.jsp");
		}
	} catch (ClassNotFoundException | SQLException e) {
		out.println("An error occurred while updating the Batch: " + e.getMessage());
		response.sendRedirect("error.jsp");
	}
} else {
    // User is not logged in, redirect to login page or show an error message
    response.sendRedirect("login.jsp");
}
%>