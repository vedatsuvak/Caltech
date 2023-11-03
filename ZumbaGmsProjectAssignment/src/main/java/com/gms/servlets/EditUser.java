package com.gms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gms.dao.UsersDAO;
import com.gms.pojo.User;

public class EditUser extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        RequestDispatcher rd = request.getRequestDispatcher("main.jsp");
        boolean isLoggedIn = session.getAttribute("username") != null;
        PrintWriter pw = response.getWriter();
        UsersDAO dao = new UsersDAO();
        User user = new User();
        User selectedUser = null;
        String selectedUsername = request.getParameter("username");        
        if (isLoggedIn && username.equalsIgnoreCase("admin")) {
            try {
                selectedUser = dao.displayUser(selectedUsername);
                if (selectedUser != null && !selectedUsername.equalsIgnoreCase("admin")) {
                    rd.include(request, response);
                    StringBuilder html = new StringBuilder();
                    html.append("<html><head><link rel='stylesheet' type='text/css' href='styles.css'></head>");
                    html.append("<body><div class='link-container'>");
                    html.append("<h1>Edit User</h1>");
                    html.append("<form action='EditUser' method='POST'>");
                    html.append("<input type='hidden' name='username' value='" + request.getParameter("username") + "'>");
                    html.append("<table class='container'>");
                    html.append("<tr class = 'label'><td>User Name</td><td>:</td><td><input type='text' name='newUsername' value='" + request.getParameter("username") + "' pattern=\"^[A-Za-z0-9_-]{5,30}$\" title=\"letters, numbers, underscores, and hyphens. Length between 5 and 30 characters.\" required></td></tr>");
                    html.append("<tr class = 'label'><td>Password</td><td>:</td><td><input type='password' name='newPassword' value='" + selectedUser.getPassword() + "' pattern=\"^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*.?&])[A-Za-z\\d@$!%*.?&]{8,}$\" title=\"At least 8 characters long, Must contain at least one uppercase letter, one lowercase letter, one digit,one special character (@$!%*.?&)\" required></td></tr>");
                    html.append("<tr class = 'label'><td>Confirm Password</td><td>:</td><td><input type='password' name='confirmPassword' value='" + selectedUser.getPassword() + "' pattern=\"^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*.?&])[A-Za-z\\d@$!%*.?&]{8,}$\" title=\"At least 8 characters long, Must contain at least one uppercase letter, one lowercase letter, one digit,one special character (@$!%*.?&)\" required></td></tr>");
                    html.append("<tr class = 'label'><td>Email</td><td>:</td><td><input type='text' name='newEmail' value='" + selectedUser.getEmail() + "' pattern=\"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$\" title=\"Enter a valid email address.\" required></td></tr>");
                    html.append("<tr class='link-container'><td colspan='3'><input type='submit' value='Save Changes'></td>");
                    html.append("</table></form>");
                    html.append("</div></body></html>");
                    pw.println(html.toString());                   
                } else if (selectedUser != null && selectedUsername.equalsIgnoreCase("admin")) {
                    rd.include(request, response);
                    StringBuilder html = new StringBuilder();
                    html.append("<html><head><link rel='stylesheet' type='text/css' href='styles.css'></head>");
                    html.append("<body><div class='link-container'>");
                    html.append("<h1>Edit User</h1>");
                    html.append("<form action='EditUser' method='POST'>");
                    html.append("<input type='hidden' name='username' value='" + request.getParameter("username") + "'>");
                    html.append("<table class='container'>");
                    html.append("<tr class = 'label'><td>User Name</td><td>:</td><td>Admin<input type='hidden' name='newUsername' value='" + request.getParameter("username") + "'></td></tr>");
                    html.append("<tr class = 'label'><td>Password</td><td>:</td><td><input type='password' name='newPassword' value='" + selectedUser.getPassword() + "' pattern=\"^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*.?&])[A-Za-z\\d@$!%*.?&]{8,}$\" title=\"At least 8 characters long, Must contain at least one uppercase letter, one lowercase letter, one digit,one special character (@$!%*.?&)\" required></td></tr>");
                    html.append("<tr class = 'label'><td>Confirm Password</td><td>:</td><td><input type='password' name='confirmPassword' value='" + selectedUser.getPassword() + "' pattern=\"^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*.?&])[A-Za-z\\d@$!%*.?&]{8,}$\" title=\"At least 8 characters long, Must contain at least one uppercase letter, one lowercase letter, one digit,one special character (@$!%*.?&)\" required></td></tr>");
                    html.append("<tr class = 'label'><td>Email</td><td>:</td><td><input type='text' name='newEmail' value='" + selectedUser.getEmail() + "' pattern=\"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$\" title=\"Enter a valid email address.\" required></td></tr>");
                    html.append("<tr class='link-container'><td colspan='3'><input type='submit' value='Save Changes'></td>");
                    html.append("</table></form>");
                    html.append("</div></body></html>");

                    pw.println(html.toString());
                } else {
                    pw.println(username + " not found.");
                    System.out.println(selectedUser);
                }
            } catch (Exception e) {
                pw.println("An error occurred while retrieving User: " + e.getMessage());
            }
        } else if (isLoggedIn && !username.equalsIgnoreCase("admin")) {
            try {
                user = dao.displayUser(username);
                if (user != null) {
                    response.setContentType("text/html");
                    rd.include(request, response);
                    StringBuilder html = new StringBuilder();
                    html.append("<html><head><link rel='stylesheet' type='text/css' href='styles.css'></head>");
                    html.append("<body><div class='link-container'>");
                    html.append("<h1>Edit User</h1>");
                    html.append("<form action='EditUser' method='POST'>");
                    html.append("<input type='hidden' name='username' value='" + user.getUsername() + "'>");
                    html.append("<table class='container'>");
                    html.append("<tr class = 'label'><td>User Name</td><td>:</td><td><input type='text' name='newUsername' value='" + user.getUsername() + "' pattern=\"^[A-Za-z0-9_-]{5,30}$\" title=\"letters, numbers, underscores, and hyphens. Length between 5 and 30 characters.\" required></td></tr>");
                    html.append("<tr class = 'label'><td>Password</td><td>:</td><td><input type='password' name='newPassword' value='" + user.getPassword() + "' pattern=\"^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*.?&])[A-Za-z\\d@$!%*.?&]{8,}$\" title=\"At least 8 characters long, Must contain at least one uppercase letter, one lowercase letter, one digit,one special character (@$!%*.?&)\" required></td></tr>");
                    html.append("<tr class = 'label'><td>Confirm Password</td><td>:</td><td><input type='password' name='confirmPassword' value='" + user.getPassword() + "' pattern=\"^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*.?&])[A-Za-z\\d@$!%*.?&]{8,}$\" title=\"At least 8 characters long, Must contain at least one uppercase letter, one lowercase letter, one digit,one special character (@$!%*.?&)\" required></td></tr>");
                    html.append("<tr class = 'label'><td>Email</td><td>:</td><td><input type='text' name='newEmail' value='" + user.getEmail() + "' pattern=\"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$\" title=\"Enter a valid email address.\" required></td></tr>");
                    html.append("<tr class='link-container'><td colspan='3'><input type='submit' value='Save Changes'></td>");
                    html.append("</table></form>");
                    html.append("</div></body></html>");

                    pw.println(html.toString());
                } else {
                    response.setContentType("text/html");
                    pw.println("<html><body><center>");
                    pw.println("<h2>User details not found</h2>");
                    pw.println("</center></body></html>");
                }
            } catch (Exception e) {
                response.setContentType("text/html");
                pw.println("<html><body><center>");
                pw.println("<h2>Error occurred while retrieving user details.</h2>");
                pw.println("</center></body></html>");
                pw.println(e);
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        String username = request.getParameter("username");
        String newUsername = request.getParameter("newUsername");
        String newPassword = request.getParameter("newPassword");
        String newEmail = request.getParameter("newEmail");
        String confirmPassword = request.getParameter("confirmPassword");
        HttpSession session = request.getSession();
        String oldusername = (String) session.getAttribute("username");
        RequestDispatcher rd = request.getRequestDispatcher("main.jsp");
        PrintWriter pw = response.getWriter();
        UsersDAO dao = new UsersDAO();
        user.setUsername(newUsername);
        user.setPassword(newPassword);
        user.setEmail(newEmail);
        try {
            if (!newPassword.equals(confirmPassword)) {
                response.setContentType("text/html");
                rd.include(request, response);
                pw.println("<html><body>");
                pw.println("<h3>Password does not match. Please try again...!</h3>");
                pw.println("</body></html>");
            } else {
                int result = dao.editUser(user, username);
                if (oldusername.equalsIgnoreCase("admin") && result > 0) {
                    session.setAttribute("actionSuccessful", true);
                    response.sendRedirect("success.jsp");
                } else if (!oldusername.equalsIgnoreCase("admin") && result > 0) {
                    session.setAttribute("username", newUsername);
                    session.setAttribute("actionSuccessful", true);
                    response.sendRedirect("success.jsp");
                } else {
                    response.setContentType("text/html");
                    pw.println("<html><body>");
                    pw.println("<h2>Error occurred while updating user details.</h2>");
                    pw.println("</body></html>");
                }
            }
        } catch (Exception e) {
            response.setContentType("text/html");
            pw.println("<html><body>");
            pw.println("<h2>Error occurred while updating user details.</h2>");
            pw.println("</body></html>");
        	pw.println(e);
        } 
    }
}