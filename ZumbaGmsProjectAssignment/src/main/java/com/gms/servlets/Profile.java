
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
import com.gms.pojo.User;

public class Profile extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Retrieve the logged-in username from the session
        HttpSession session = request.getSession();
        RequestDispatcher rd = request.getRequestDispatcher("main.jsp");
        String username = (String) session.getAttribute("username");
        boolean isLoggedIn = session.getAttribute("username") != null;
        UsersDAO dao = new UsersDAO();
        User u = null;
        if (isLoggedIn) {
            PrintWriter pw = response.getWriter();
            response.setContentType("text/html");
            try {
                u = dao.displayUser(username);
            } catch (ClassNotFoundException | SQLException e) {
            	pw.println(e);
            }
            if (u != null) {
                // User details found
                String name = u.getUsername();
                String password = u.getPassword();
                String email = u.getEmail();
                // Include main.jsp page
                rd.include(request, response);
                pw.println("<html><head><link rel='stylesheet' type='text/css' href='styles.css'></head>");
                pw.println("<body class='link-container'><div>");
                pw.println("<h2 class='link-container'>Profile</h2>");
                pw.println("<table class='container'>");
                pw.println("<tr><td class='label'>User Name</td><td>:</td><td class='label'>" + name.toUpperCase() + "</td></tr>");
                pw.println("<tr><td class='label'>Password</td><td>:</td><td class='label'>" + "*".repeat(password.length()) + "</td></tr>");
                pw.println("<tr><td class='label'>Email</td><td>:</td><td class='label'>" + email + "</td></tr>");
                pw.println("</table>");
                pw.println("</div><div>");

                if (!name.equalsIgnoreCase("admin")) {
                    // Edit User
                    pw.println("<form action='./EditUser' method='GET'>");
                    pw.println("<input type='hidden' name='username' value='"+ name +"'>");
                    pw.println("<input type='submit' value='Edit User'>");
                    pw.println("</form><br>");
                    //Delete User
                    pw.println("<form action='./DeleteUser' method='POST'>");
                    pw.println("<p>Do you want to delete your account?</p>");
                    pw.println("<p>Please don't abandon us!</p>");
                    pw.println("<p>If you insist, then click the button below :</p>");
                    pw.println("<input type='submit' value='Destroy'> my account..!");
                    pw.println("</form>");
                    pw.println("</div></body></html>");
                } else {
                    // Edit User
                    pw.println("<form action='./EditUser?username=' method='GET'>");
                    pw.println("<input type='hidden' name='username' value='"+ name +"'>");
                    pw.println("<input type='submit' value='Edit Email and Password'>");
                    pw.println("</form><br>");
                    // Delete User
                    pw.println("<h3 class='link-container'>Admin Cannot be Deleted</h3>");
                    pw.println("</div></body></html>");
                }
            } else {
                // Forward the request to the profile.jsp page
                rd.include(request, response);
                // User details not found, display an error message
                pw.println("<html><body>");
                pw.println("<h2>User details not found</h2>");
                pw.println("</body></html>");
            }
        } else {
            // User is not logged in, redirect to login page
            response.sendRedirect("login.jsp");
        }
    }
}
