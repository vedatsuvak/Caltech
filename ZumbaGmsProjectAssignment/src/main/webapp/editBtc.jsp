
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.gms.dao.BatchDAO" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %>
<%@ page import="com.gms.pojo.Batch" %>
<% 
//Include main.jsp page
RequestDispatcher rd = request.getRequestDispatcher("main.jsp");
rd.include(request, response);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="styles.css">
    <title>Zumba</title>
</head>
<body class ="link-container">
<%
	boolean isLoggedIn = session.getAttribute("username") != null;
	String username = (String) session.getAttribute("username");
	
	//Admin
	if (isLoggedIn && username.equalsIgnoreCase("admin")) {
		// Get the product ID from the request parameter
		int batchId = 0;
		try {
		    batchId = Integer.parseInt(request.getParameter("bid"));    
		} catch (NumberFormatException e) {
		    // Handle the case when "bid" parameter is not a valid integer
		    out.println("Invalid batch ID");
            session.setAttribute("actionSuccessful", true);
            session.setAttribute("editbatch", true);
		    response.sendRedirect("error.jsp");
		    return;
		}
	
	BatchDAO dao = new BatchDAO();
	
	try {
	    // Retrieve the batch with the provided ID
	    Batch selectedBatch = dao.displayBatch(batchId);
	
	    // Check if the batch was found
	    if (selectedBatch != null) {
%>       
	<h1 ><i>Edit Batch</i></h1>
        <form action = "editBtcController.jsp" method="POST">
			<table class = "container">
				<tr><td class = "right">Batch ID</td>
					<td class = "left">Immutable<input type="hidden" name="bid" value="<%= request.getParameter("bid") %>"></td></tr>
				<tr><td class = "right">Batch Name</td>
					<td class = "left"><input type="text" name="bname" value="<%= selectedBatch.getBname().toUpperCase() %>" pattern="^[A-Za-z0-9_-]{5,30}$" title="letters, numbers, underscores, and hyphens. Length between 5 and 30 characters." required></td></tr>
				<tr>
				    <td class="right">Instructor</td>
				    <td>
				        <select name="instructor" required>
				            <option value="VEDAT">VEDAT</option>
				            <option value="KARTHIK">KARTHIK</option>
				            <option value="KAMAL">KAMAL</option>
				            <option value="SURAJ">SURAJ</option>
				            <option value="ANITA">ANITA</option>
				            <option value="SWATI">SWATI</option>
				        </select>
				    </td>
				</tr>
				<tr><td class = "right">Start Time</td>
					<td class = "left"><input type="time" name="startTime" value="<%= selectedBatch.getStartTime() %>" required></td></tr>
				<tr><td class = "right">Start Date</td>
					<td class = "left"><input type="date" name="startDate" value="<%= selectedBatch.getStartDate() %>" required></td></tr>
				<tr><td colspan = "2"><input type="submit" value="Edit"></td></tr>
			</table>
		</form>
<%
	    } else {
	        out.println("Batch with ID " + batchId + " not found.");
	        session.setAttribute("error", true);
	        session.setAttribute("editbatch", true);
	        response.sendRedirect("error.jsp");
	    }
	} catch (ClassNotFoundException | SQLException e) {
	    out.println("An error occurred while retrieving the Batch: " + e.getMessage());
        session.setAttribute("error", true);
        session.setAttribute("editbatch", true);	    
	    response.sendRedirect("error.jsp");
	}
} else {
    // Other users restricted
    response.sendRedirect("login.jsp");
}
%>
</body>
</html>
