<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.gms.dao.ParticipantsDAO"%>
<%@ page import="com.gms.pojo.Participants"%>
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
		    return confirm("Are you sure to unregister Batch?");
		}
	</script>
    <title>Batches</title>
</head>
<body>
<%
boolean isLoggedIn = session.getAttribute("username") != null;
String username = (String) session.getAttribute("username");
HttpServletResponse httpResponse = (HttpServletResponse) response;
List<Integer> bids = new ArrayList<>();
List<Integer> pids = new ArrayList<>();

if (isLoggedIn) {
    ParticipantsDAO pdao = new ParticipantsDAO();
    List<Participants> plist = pdao.BatchParticipants(username);
    BatchDAO bdao = new BatchDAO();
    List<Batch> blist = new ArrayList<>();
    for (Participants p : plist){
    	int bid = p.getBid();
    	int pid = p.getPid();
    	bids.add(bid);
    	pids.add(pid);
    	blist.add(bdao.displayBatch(bid));
    }	
%>
    <div style="text-align: center;">
        <h1><i>List of Registered Batches</i></h1>
        <table style="margin: 0 auto;" border="1">
            <tr>
                <th>Batch ID</th>
                <th>Batch Name</th>
                <th>Instructor</th>
                <th>Start Time</th>
                <th>Start Date</th>
                <th>Action</th>
            </tr>
            <% for (Batch b : blist) { %>
                <tr>
                    <td><%= b.getBid() %></td>
                    <td><%= b.getBname().toUpperCase() %></td>
                    <td><%= b.getInstructor().toUpperCase() %></td>
                    <td><%= b.getStartTime() %></td>
                    <td><%= b.getStartDate() %></td>
                    <td><button onclick="if(confirmDelete()) { window.location.href='unregisterParticipant.jsp?bid=<%= b.getBid() %>'; }">Unregister Batch</button></td>
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