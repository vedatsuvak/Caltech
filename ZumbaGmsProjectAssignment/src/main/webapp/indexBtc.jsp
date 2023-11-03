<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.gms.dao.BatchDAO"%>
<%@ page import="com.gms.pojo.Batch"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.RequestDispatcher"%>
<%@ page import="javax.servlet.http.HttpServletResponse"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="styles.css">
    <script>
		function confirmDelete() {
		    return confirm("Are you sure to DELETE Batch?");
		    session.setAttribute("actionSuccessful", true);
		}
		function startBatch() {
		    return confirm("Are you sure to Start Batch?");
		}
	</script>
    <title>Batches</title>
</head>
<body>
<%
boolean isLoggedIn = session.getAttribute("username") != null;
String username = (String) session.getAttribute("username");
HttpServletResponse httpResponse = (HttpServletResponse) response;
//admin
if (isLoggedIn && username.equalsIgnoreCase("admin")) {
    BatchDAO dao = new BatchDAO();
    List<Batch> list = dao.displayBatches();
%>
    <div style="text-align: center;">
        <h1><i>List of Batches</i></h1>
        <form action="addBatch.jsp">
            <input type="submit" value="Add Batch">
        </form><br>
        <table style="margin: 0 auto;" border="1">
            <tr>
                <th>Batch ID</th>
                <th>Batch Name</th>
                <th>Instructor</th>
                <th>Start Time</th>
                <th>Start Date</th>
                <th>Edit Batch</th>
                <th>Delete Batch</th>
                <th>Show Participants</th>
                <th>Add Participant</th>
                <th>Action</th>
            </tr>
            <% for (Batch b : list) { %>
                <tr>
                    <td><%= b.getBid() %></td>
                    <td><%= b.getBname().toUpperCase() %></td>
                    <td><%= b.getInstructor().toUpperCase() %></td>
                    <td><%= b.getStartTime() %></td>
                    <td><%= b.getStartDate() %></td>
					<td><button onclick="window.location.href='editBtc.jsp?bid=<%= b.getBid() %>'">Edit Batch</button></td>                    
					<td><button onclick="if(confirmDelete()) { window.location.href='deleteBtc.jsp?bid=<%= b.getBid() %>';}">Delete Batch</button></td>
					<td><button onclick="window.location.href='showParticipants.jsp?bid=<%= b.getBid() %>'">Show Participants</button></td>
					<td><button onclick="window.location.href='addParticipant.jsp?bid=<%= b.getBid() %>'">Add Participant</button></td>
                	<td><button onclick="if(startBatch()) { window.location.href='startBtc.jsp?bid=<%= b.getBid() %>';}">Start Batch</button></td>
                </tr>
            <% } 
            RequestDispatcher rd = request.getRequestDispatcher("main.jsp");
			rd.include(request, response);%>
        </table>
    </div>
    <p style="font-size: 15px; color: red;">Warning: If you start a Batch, the application will send via SMS and Email to participants who registered to that Batch...!</p>
    <p style="font-size: 12px; color: red;">Warning: If a batch deleted, you can not set again that Batch ID anymore...!</p>
	<p style="font-size: 12px; color: red;">Warning: To delete a specific batch, you need to delete all participants inside the batch first...!</p>
<%
//Other registered Users
} else if (isLoggedIn && !username.equalsIgnoreCase("admin")) {
    BatchDAO dao = new BatchDAO();
    List<Batch> list = dao.displayBatches();
%>
    <div style="text-align: center;">
        <h1><i>List of Batches</i></h1>
        <table style="margin: 0 auto;" border="1">
            <tr>
                <th>Batch ID</th>
                <th>Batch Name</th>
                <th>Instructor</th>
                <th>Start Time</th>
                <th>Start Date</th>
                <th>Show Participants</th>
                <th>Register to Batch</th>
            </tr>
            <% for (Batch b : list) { %>
                <tr>
                    <td><%= b.getBid() %></td>
                    <td><%= b.getBname().toUpperCase() %></td>
                    <td><%= b.getInstructor().toUpperCase() %></td>
                    <td><%= b.getStartTime() %></td>
                    <td><%= b.getStartDate() %></td>
                    <td><button onclick="window.location.href='showParticipants.jsp?bid=<%= b.getBid() %>'">See Participants</button></td>
					<td><button onclick="window.location.href='addParticipant.jsp?bid=<%= b.getBid() %>'">Register to Batch</button></td>
                </tr>
            <% } 
            RequestDispatcher rd = request.getRequestDispatcher("main.jsp");
            rd.include(request, response);%>
        </table>
    </div>
<%
//If not logged in
} else {
    // User is not logged in
    httpResponse.sendRedirect("login.jsp");
}
%>
</body>
</html>