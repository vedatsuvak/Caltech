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
<title>Unregister Participant</title>
</head>
<body>
	<h1><i>Deleting a Participant from Batch</i></h1>
<%
	boolean isLoggedIn = session.getAttribute("username") != null;
	String username = (String) session.getAttribute("username");
    int bid = 0;
    
    if (isLoggedIn) {
        // Get the product ID from the request parameter
        try {
        	bid = Integer.parseInt(request.getParameter("bid"));
        } catch (NumberFormatException e) {
            out.println("Invalid batch ID");
            return;
        }
        ParticipantsDAO dao = new ParticipantsDAO();

        try {
            Participants prt = new Participants();
            prt.setPid(bid);
            prt.setPname(username);

            // Delete the product from the database
            int rowsDeleted = dao.unregisterParticipant(username, bid);

            // Check if the deletion was successful
            if (rowsDeleted > 0) {
                out.println("Participant has been unregistered successfully.");
                session.setAttribute("actionSuccessful", true);
                session.setAttribute("deleteparticipant", true);
                response.sendRedirect("success.jsp");
            } else {
                out.println("Failed to delete participant.");
                session.setAttribute("error", true);
                session.setAttribute("deleteparticipant", true);
                response.sendRedirect("error.jsp");
            }
        } catch (ClassNotFoundException | SQLException e) {
            out.println("An error occurred while deleting the participant: " + e.getMessage());
            session.setAttribute("error", true);
            session.setAttribute("deleteparticipant", true);
            response.sendRedirect("error.jsp");
        }
    } else {
        // If not logged in entry Restricted
        response.sendRedirect("login.jsp");
    }
%>
</body>
</html>