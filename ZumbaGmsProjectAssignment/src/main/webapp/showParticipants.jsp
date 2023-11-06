
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.gms.dao.ParticipantsDAO"%>
<%@ page import="com.gms.pojo.Participants"%>
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
		    return confirm("Are you sure to DELETE Participant?");
		}
	</script>
    <title>Zumba</title>
</head>
<body>
<%
boolean isLoggedIn = session.getAttribute("username") != null;
String username = (String) session.getAttribute("username");
ParticipantsDAO dao = new ParticipantsDAO();
Participants participant = new Participants();
int bid = 0;

try {
    	bid = Integer.parseInt(request.getParameter("bid"));    
	} catch (NumberFormatException e) {
	    out.println("Invalid batch ID");
        session.setAttribute("error", true);
        session.setAttribute("showparticipants", true);
	    response.sendRedirect("error.jsp");
    return;
}
// Admin Page
if (isLoggedIn && username.equalsIgnoreCase("admin")) {
	List<Participants> list = dao.BatchParticipants(bid);
%>
    <div style="text-align: center;">
        <h1><i>List of Participants Batch :<%= bid %></i></h1>
	       <form action="addParticipant.jsp" method="POST">
			    <label for="name"></label>
			    <input type="hidden" id="bid" name="bid" value="<%= bid %>"><br>			    
			    <input type="submit" value="Add Participant">
			</form><br>
        <table style="margin: 0 auto;" border="1">
            <tr>
                <th>Participant ID</th>
                <th>Participant Name</th>
                <th>Age</th>
                <th>Gender</th>
                <th>Phone</th>
                <th>Email</th>
                <th>Edit Action</th>
                <th>Delete Action</th>
            </tr>
            <% for (Participants p : list) { %>
                <tr>
                    <td><%= p.getPid() %></td>
                    <td><%= p.getPname().toUpperCase() %></td>
                    <td><%= p.getAge() %></td>
                    <td><%= p.getGender().toUpperCase() %></td>
                    <td><%= p.getPhone() %></td>
                    <td><%= p.getEmail().toLowerCase() %></td>
					<td><button onclick="window.location.href='editParticipant.jsp?pid=<%= p.getPid() %>'" value="<%= request.getParameter("pid") %>">Edit Participant</button></td>                    
					<td><button onclick="if(confirmDelete()) { window.location.href='deleteParticipant.jsp?pid=<%= p.getPid() %>'; }">Delete Participant</button></td>
                </tr>
            <% } 
            RequestDispatcher rd = request.getRequestDispatcher("main.jsp");
			rd.include(request, response);%>
        </table>
    </div>
   	<p style="font-size: 10px; color: red;">Warning: If a Participant deleted, you can not see that Participant ID anymore...!</p>
    
<%
// User Page
} else if (isLoggedIn && !username.equalsIgnoreCase("admin")) {
	List<Participants> list = dao.BatchParticipants(bid);
%>
    <div style="text-align: center;">
        <h1><i>Registered Participants of Batch :<%= bid %></i></h1>
	       <form action="addParticipant.jsp" method="POST">
			    <label for="name"></label>
			    <input type="hidden" id="bid" name="bid" value="<%= bid %>"><br>			    
			    <input type="submit" value="Register this Batch">
			</form><br>
        <table style="margin: 0 auto;" border="1">
            <tr>
                <th>Participant ID</th>
                <th>Participant Name</th>
                <th>Age</th>
                <th>Gender</th>
            </tr>
            <% for (Participants p : list) { %>
				<tr>	
                    <td><%= p.getPid() %></td>
                    <td><%= p.getPname().toUpperCase() %></td>
                    <td><%= p.getAge() %></td>
                    <td><%= p.getGender().toUpperCase() %></td>
				</tr>
            <% } 
            RequestDispatcher rd = request.getRequestDispatcher("main.jsp");
			rd.include(request, response);%>
        </table>
    </div>
<%
//Redirect Login
} else {
    // User is not logged in
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    httpResponse.sendRedirect("login.jsp");
}
%>
</body>
</html>