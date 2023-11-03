<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// Check if the user is logged in
boolean isLoggedIn = session.getAttribute("username") != null;
if (!isLoggedIn) {
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel='stylesheet' type='text/css' href='styles.css'>
	<title>Login Page</title>
</head>
<body>
	<div class="link-container">
		<h1><i>Login form </i></h1>
		<form action="LoginServlet" method="post">			
			<table class="container">
			  <tr class='label'>
			    <td>User Name</td>
			    <td><input type="text" name="user" pattern="^[A-Za-z0-9_-]{5,30}$" title="letters, numbers, underscores, and hyphens. Length between 5 and 30 characters." required></td>
			  </tr>
			  <tr class='label'>
			    <td>Password</td>
			    <td><input type="password" name="pwd" pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*.?&])[A-Za-z\d@$!%*.?&]{8,}$" title="At least 8 characters long, Must contain at least one uppercase letter, one lowercase letter, one digit,one special character (@$!%*.?&)" required></td>
			  </tr>
			  <tr>
			    <td colspan="2"><input type="submit" value="Login"></td>
			  </tr>
			</table>
		</form>
		<form action="register.jsp" method="post">
			<p>Still not registered?
			<input type="submit" value="Register Here!"></p>	
		</form>
	</div>
</body>
</html>
<%
} else {
	response.sendRedirect("./Profile");
}
%>