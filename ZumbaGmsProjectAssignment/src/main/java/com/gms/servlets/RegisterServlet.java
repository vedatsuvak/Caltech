package com.gms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import com.gms.dao.UsersDAO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	String username = request.getParameter("user");
    	String password = request.getParameter("pwd");
    	String confirmPassword = request.getParameter("confirmPwd");
    	String email = request.getParameter("email");
        PrintWriter pw = response.getWriter();       
    	if (username == null || password == null || confirmPassword == null || email == null || !password.equals(confirmPassword)) {
    	    String warningMessage = "One or more values are missing or passwords do not match";
    	    request.setAttribute("warningMessage", warningMessage);
    	    pw.println("<script type='text/javascript'>alert('" + warningMessage + "'); window.location.href = 'register.jsp';</script>");   	    
    	    return;
    	    
    	    //todo password cross control
    	}else {       
	     	RequestDispatcher rd=request.getRequestDispatcher("main.jsp");
	 		rd.include(request, response);
	 		UsersDAO add = new UsersDAO();
	 		int result = 0;
			try {
				result = add.insertUser(username, password, email);
				} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				pw.println(e);
				} catch (SQLException e) {
				// TODO Auto-generated catch block
				pw.println(e);
				}
	        if (result > 0) {
	        	System.out.println("User registered successfully!");	
		        // Display a success message
		        response.setContentType("text/html");
		        pw.println("<html><body><center>");
		        pw.println("<h2>User registered successfully!</h2>");
		        pw.println("</center></body></html>");
	        }
    	} 
    }
 
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	response.sendRedirect("register.jsp");
    }
}
