
package com.gms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gms.dao.ParticipantsDAO;
import com.gms.dao.UsersDAO;
import com.gms.pojo.Participants;
import com.gms.pojo.User;

public class AddParticipant extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AddParticipant() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter pw = response.getWriter();

        try {
            // Get the user input from the request parameters
            UsersDAO userDao = new UsersDAO();
            ParticipantsDAO participantsDAO = new ParticipantsDAO();
            Participants participant = new Participants();
            String pname = request.getParameter("name");
            User user = userDao.displayUser(pname);
            HttpSession session = request.getSession();

            if (user != null) {
                String email = user.getEmail();
                int age = Integer.parseInt(request.getParameter("age"));
                String gender = request.getParameter("gender");
                String phone = request.getParameter("phone");
                int bid = Integer.parseInt(request.getParameter("bid"));
                Participants result = null;
                participant.setPname(pname);
                participant.setAge(age);
                participant.setGender(gender);
                participant.setPhone(phone);
                participant.setEmail(email);
                participant.setBid(bid);
                //Check the participants if already added
                result = participantsDAO.getParticipant(pname, bid);
                if(result != null) {
                	session.setAttribute("actionSuccessful", true);
                	session.setAttribute("register", true);
                	response.sendRedirect("error.jsp");
                }else {
	                int add = participantsDAO.insertParticipant(participant);
	
	                if (add > 0) {
	                	session.setAttribute("actionSuccessful", true);
	                	response.sendRedirect("success.jsp");
	                } else {
	                    // Display an error message
	                    response.setContentType("text/html");
	                    pw.println("<html><body>");
	                    pw.println("<h2>Error occurred while adding!</h2>");
	                    pw.println("</body></html>");
	                }
                }
            } else {
                // Display an error message
                response.setContentType("text/html");
                pw.println("<html><body>");
                pw.println("<h2>User does not exist</h2>");
                pw.println("</body></html>");
            }
        } catch (ClassNotFoundException | SQLException e) {
            // Display an error message
            response.setContentType("text/html");
            pw.println("<html><body>");
            pw.println("<h2>Error occurred while adding...!</h2>");
            pw.println("</body></html>");
            pw.println(e);
            
        } 
    }
}
