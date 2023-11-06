package com.gms.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gms.dao.ParticipantsDAO;
import com.gms.pojo.Participants;

public class EditParticipant extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EditParticipant() {
		super();
	}
	//only admin access
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ParticipantsDAO dao = new ParticipantsDAO();
		String pname = request.getParameter("pname");
		int age = Integer.parseInt(request.getParameter("age"));
		String gender = request.getParameter("gender");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		int bid = Integer.parseInt(request.getParameter("bid"));
		int pid = Integer.parseInt(request.getParameter("pid"));
		HttpSession session = request.getSession();
		Participants participant = new Participants();
		participant.setPname(pname);
		participant.setAge(age);
		participant.setGender(gender);
		participant.setPhone(phone);
		participant.setEmail(email);
		participant.setBid(bid);
		participant.setPid(pid);
		
		try {
			int result = dao.updateParticipant(participant);
			if (result > 0) {
				session.setAttribute("actionSuccessful", true);
				session.setAttribute("useredited", true);
				response.sendRedirect("success.jsp");
			} else {
				session.setAttribute("editparticipant", true);
				session.setAttribute("error", true);
				response.sendRedirect("error.jsp");
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				session.setAttribute("editparticipant", true);
				session.setAttribute("error", true);
				response.sendRedirect("error.jsp");
			}
		}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.sendRedirect("login.jsp");
	}
}