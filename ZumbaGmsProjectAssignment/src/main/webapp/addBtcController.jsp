
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.gms.pojo.Batch"%>
<%@ page import="com.gms.dao.BatchDAO"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.http.HttpServletResponse"%>
<%@ page import="java.time.LocalTime" %>
<%@ page import="java.time.LocalDate" %>
<%
boolean isLoggedIn = session.getAttribute("username") != null;
String username = (String) session.getAttribute("username");
// Admin
if (isLoggedIn && username.equalsIgnoreCase("admin")) {
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert Batch</title>
</head>
<body>
<%
    BatchDAO dao = new BatchDAO();
    Batch batch = new Batch();
    batch.setBname(request.getParameter("bname"));
    batch.setInstructor(request.getParameter("instructor"));
    batch.setStartTime(request.getParameter("startTime"));
    batch.setStartDate(request.getParameter("startDate"));
	int result = dao.insertBatch(batch);
    if (result > 0) {
    	session.setAttribute("actionSuccessful", true);
    	session.setAttribute("addbatch", true);
    	response.sendRedirect("success.jsp");
    } else {
    	session.setAttribute("actionSuccessful", true);
    	session.setAttribute("addbatch", true);
    	response.sendRedirect("error.jsp");
    	
    }
%>

</body>
</html>
<%
} else {
    // User is not logged
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    httpResponse.sendRedirect("login.jsp");
}
%>