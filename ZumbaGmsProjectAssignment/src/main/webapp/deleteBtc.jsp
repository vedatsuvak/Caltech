
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.gms.dao.BatchDAO" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %>
<%@ page import="com.gms.pojo.Batch" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
</head>
<body>
	<h1><i>Deleting a Batch</i></h1>

<%
    boolean isLoggedIn = session.getAttribute("username") != null;
    String username = (String) session.getAttribute("username");
    int batchId = 0;
	
    //Only Admin Allowed
    if (isLoggedIn && username.equalsIgnoreCase("admin")) {
        // Get the product ID from the request parameter
        try {
            batchId = Integer.parseInt(request.getParameter("bid"));
        } catch (NumberFormatException e) {
            // Handle the case when "bid" parameter is not a valid integer
            out.println("Invalid batch ID");
            return;
        }
        BatchDAO dao = new BatchDAO();
        try {
            Batch batch = new Batch();
            batch.setBid(batchId);

            // Delete the batch from the database
            int rowsDeleted = dao.deleteBatch(batchId);

            // Check if the deletion was successful
            if (rowsDeleted > 0) {
                out.println("Batch with ID " + batchId + " has been deleted successfully.");
                session.setAttribute("actionSuccessful", true);
                response.sendRedirect("success.jsp");
            } else {
                out.println("Failed to delete batch with ID " + batchId + ".");
            }
        } catch (ClassNotFoundException | SQLException e) {
            out.println("An error occurred while deleting the batch: " + e.getMessage());
        }
    } else {
        // Other Users restricted
        response.sendRedirect("login.jsp");
    }
%>
</body>
</html>
