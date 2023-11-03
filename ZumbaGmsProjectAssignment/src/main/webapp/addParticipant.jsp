
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.gms.dao.ParticipantsDAO"%>
<%@ page import="com.gms.pojo.Participants"%>
<%@ page import="com.gms.dao.UsersDAO"%>
<%@ page import="com.gms.pojo.User"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Participant</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<%
boolean isLoggedIn = session.getAttribute("username") != null;
String username = (String) session.getAttribute("username");
UsersDAO dao = new UsersDAO();
List<User> userList = dao.displayUsers();
String bid = request.getParameter("bid");
//include main
RequestDispatcher rd = request.getRequestDispatcher("main.jsp");
rd.include(request, response);
// Admin

%>
<body class="link-container">
    <h1><i>Adding Participant</i></h1>
    <form action="AddParticipant" method="post">
        <table class="container">
         	<tr>
                <td class="right">Batch ID</td>
                <td><%= bid %><input type="hidden" value="<%= bid %>" name="bid"></td>
            </tr>
            <tr>
                <td class="right">Participant ID</td>
                <td>Auto Increment<input type="hidden" name="pid"></td>
            </tr>               
<%
		if (isLoggedIn && username.equalsIgnoreCase("admin")) {			
%>			<tr>
                <td class="right">Participant Name</td>
                <td> <select name="name" size="3" required>
                <%for(User user : userList) {  %>
                     	<option value="<%= user.getUsername() %>"><%= user.getUsername().toUpperCase() %></option>
                     	<% } %>
                </select></td>
            </tr>
                <%
                }else if(isLoggedIn && !username.equalsIgnoreCase("admin")) {%>
            <tr>            
            	<td class="right">Participant Name</td>
			    <td><%= username.toUpperCase() %><input type="hidden" name="name" value="<%= username %>" required></td>
			</tr>
			            <%}else {
			                // User is not logged in
			                response.sendRedirect("login.jsp");
			            } %>
            <tr>
                <td class="right">Age</td>
                <td><input type="text" name="age" pattern="^(0?[1-9]|[1-9][0-9])$" title="Enter a valid age between 1 and 99." required></td>
            </tr>
			<tr>
			    <td class="right">Gender</td>
			    <td>
			        <select name="gender" required>
			            <option value="male">Male</option>
			            <option value="female">Female</option>
			            <option value="other">Other</option>
			        </select>
			    </td>
			</tr>
            <tr>
                <td class="right">Phone</td>
                <td><input type="text" name="phone" pattern="^\+(?:\d{1,3}-)?\d{5,15}$" title="Enter a valid phone number with a country code. Examples: +1234567890 or +1-234567890." required></td>
            </tr>
            <tr>
                <td class="right">Email</td>
                <td>Auto selected</td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Add"></td>
            </tr>
        </table>
    </form>
</body>
</html>