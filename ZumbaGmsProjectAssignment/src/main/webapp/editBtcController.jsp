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
	try {
		batch.setBid(Integer.parseInt(request.getParameter("bid")));
		batch.setBname(request.getParameter("bname"));
		batch.setInstructor(request.getParameter("instructor"));
		batch.setStartTime(request.getParameter("startTime"));
		batch.setStartDate(request.getParameter("startDate"));
		result = dao.editBatch(batch);
		if(result >0){
            session.setAttribute("actionSuccessful", true);
            session.setAttribute("editbatch", true);
            response.sendRedirect("success.jsp");
		}
	} catch (ClassNotFoundException | SQLException e) {
		out.println("An error occurred while updating the Batch: " + e.getMessage());
        session.setAttribute("actionSuccessful", true);
        session.setAttribute("editbatch", true);
		response.sendRedirect("error.jsp");
	}
} else {
    // User is not logged in, redirect to login page or show an error message
    response.sendRedirect("login.jsp");
}
%>