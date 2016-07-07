package com.tum.dbpra;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tum.dbpra.model.bean.OrganizerBean;
import com.tum.dbpra.model.dao.OrganizerDAO;

@WebServlet("/welcomeOrganizer")
public class WelcomeOrganizerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean loginSucessfull = false;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WelcomeOrganizerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher;
		//
		// if (request.getParameter("human") != null) {
		// dispatcher = request.getRequestDispatcher("/shift.jsp");
		// dispatcher.forward(request, response);
		//
		// } else if (request.getParameter("bands") != null) {
		// dispatcher = request.getRequestDispatcher("/timeslotAssign.jsp");
		// dispatcher.forward(request, response);
		//
		// } else if (request.getParameter("finances") != null) {
		// dispatcher = request.getRequestDispatcher("/finance.jsp");
		// dispatcher.forward(request, response);
		//
		// }

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {

			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String keepLoggedin[] = request.getParameterValues("loginkeeping");
			OrganizerDAO organizerDAO = new OrganizerDAO();
			boolean success = organizerDAO.validate(username, password);

			if (success) {

				loginSucessfull = true;

				HttpSession session = request.getSession();
				session.setAttribute("username", username);

			}

		} catch (Throwable e) {
			request.setAttribute("error", e.getMessage());
		}
		request.setAttribute("loginStatus", loginSucessfull);
		if (loginSucessfull) {
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/welcomeOrganizer.jsp");
			dispatcher.forward(request, response);
		} else {
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/index.jsp");
			request.setAttribute("loginMessage", "Login unsucessful");
			dispatcher.forward(request, response);
		}
	}

}
