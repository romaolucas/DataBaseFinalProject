package tum.in.dbpra.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tum.in.dbpra.bean.OrganizerBean;
import tum.in.dbpra.dao.OrganizerDAO;

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

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String organizername = "";
		try {

			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			String keepLoggedin[] = request.getParameterValues("loginkeeping");
			OrganizerDAO organizerDAO = new OrganizerDAO();
			boolean success = organizerDAO.validate(username, password);

			if (success) {

				loginSucessfull = true;

				HttpSession session = request.getSession();
				session.setAttribute("user", username);
				session.setMaxInactiveInterval(60 * 60);
				Cookie userEmail = new Cookie("user", username);
				userEmail.setMaxAge(60 * 60);
				response.addCookie(userEmail);
				session.setAttribute("username", username);
				organizername = username;

			}

		} catch (Throwable e) {
			request.setAttribute("error", e.getMessage());
		}
		request.setAttribute("loginStatus", loginSucessfull);
		if (loginSucessfull) {
			request.setAttribute("organizer", organizername);
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
