package com.gms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gms.dao.UsersDAO;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	response.sendRedirect("login.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    PrintWriter pw = response.getWriter();	
	    try {
	        // Get the user input from the request parameters
	        String username = request.getParameter("user");
	        String password = request.getParameter("pwd");	
	        UsersDAO dao = new UsersDAO();
	        //Check DB if user exist
	        boolean loginSuccessful = dao.login(username, password);	
	        if (loginSuccessful) {
	            // User exists, create a session and redirect to the profile page
	            HttpSession session = request.getSession();
	            session.setAttribute("username", username);
	            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
	            rd.forward(request, response);
	        } else {
	            // User does not exist, display an error message
	            response.setContentType("text/html");
	            pw.println("<html><body><center>");
	            pw.println("<h2>Invalid username or password</h2>");
	            pw.println("</center></body></html>");
	        }
	    } catch (ClassNotFoundException | SQLException e) {
	    	// Display an error message
		    response.setContentType("text/html");
		    pw.println("<html><body><center");
		    pw.println("<h2>Error occurred while logging in!</h2>");
		    pw.println("</center</body></html>");
		    pw.println(e);
		} 
	}
}
