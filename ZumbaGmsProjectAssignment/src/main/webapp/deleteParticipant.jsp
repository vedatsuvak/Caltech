
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.gms.dao.ParticipantsDAO" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %>
<%@ page import="com.gms.pojo.Participants" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Delete Participant</title>
	</head>
<body>
	<h1><i>Deleting a Participant from Batch</i></h1>
<%
	boolean isLoggedIn = session.getAttribute("username") != null;
	String username = (String) session.getAttribute("username");
    int pid = 0;
    
  	//Only Admin
    if (isLoggedIn && username.equalsIgnoreCase("admin")) {
        // Get the product ID from the request parameter
        try {
        	pid = Integer.parseInt(request.getParameter("pid"));
        } catch (NumberFormatException e) {
            out.println("Invalid batch ID");
            return;
        }
        ParticipantsDAO dao = new ParticipantsDAO();

        try {
            Participants prt = new Participants();
            prt.setPid(pid);

            // Delete the product from the database
            int rowsDeleted = dao.deleteParticipant(pid);

            // Check if the deletion was successful
            if (rowsDeleted > 0) {
                out.println("Participant with ID " + pid + " has been deleted successfully.");
                session.setAttribute("actionSuccessful", true);
                response.sendRedirect("success.jsp");
            } else {
                out.println("Failed to delete participant with ID " + pid + ".");
            }
        } catch (ClassNotFoundException | SQLException e) {
            out.println("An error occurred while deleting the participant: " + e.getMessage());
        }
    } else {
        // Other Users Restricted
        response.sendRedirect("login.jsp");
    }
%>
</body>
</html>