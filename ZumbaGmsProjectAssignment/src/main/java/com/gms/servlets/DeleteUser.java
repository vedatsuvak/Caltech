
package com.gms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gms.dao.UsersDAO;

public class DeleteUser extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        boolean isLoggedIn = session.getAttribute("username") != null;
        PrintWriter pw = response.getWriter();
        UsersDAO dao = new UsersDAO();
        String selectedUsername = request.getParameter("username");
       
        if (isLoggedIn && username.equalsIgnoreCase("admin")) {
            try {
                int rowsDeleted = dao.deleteUser(selectedUsername);
                
                if (rowsDeleted > 0) {
                	session.setAttribute("actionSuccessful", true);
                	session.setAttribute("userdeleted", true);
	            	response.sendRedirect("success.jsp");
                } else {
                	session.setAttribute("error", true);
                	session.setAttribute("userdeleted", true);
	            	response.sendRedirect("error.jsp");
                }
            } catch (SQLException | ClassNotFoundException e) {
            	session.setAttribute("error", true);
            	session.setAttribute("userdeleted", true);
            	response.sendRedirect("error.jsp");
            } 
        } else if (isLoggedIn && !username.equalsIgnoreCase("admin")) {
           
            try {
            	int rowsDeleted = dao.deleteUser(username);           
                if (rowsDeleted > 0) {                   
                    session.invalidate();
                    pw.println("<html><body><center>");
                    pw.println("<h2>Account deleted Successfully...</h2>");
                    pw.println("<h2>Hope to see you again!</h2>");
                    pw.println("<form action='register.jsp' method='get'>");
                    pw.println("<button type='submit'>Registeration Page</button></form>");
                    pw.println("<form action='login.jsp' method='get'>");
                    pw.println("<button type='submit'>Login Page</button></form>"); 
                    pw.println("</center></body></html>"); 
                } else {
                	session.setAttribute("error", true);
                	session.setAttribute("userdeleted", true);
                	response.sendRedirect("error.jsp");
                }
            } catch (SQLException | ClassNotFoundException e) {
            	session.setAttribute("error", true);
            	session.setAttribute("userdeleted", true);
            	response.sendRedirect("error.jsp");
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.sendRedirect("login.jsp");   
    }
}