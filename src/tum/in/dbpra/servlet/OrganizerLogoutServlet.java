package tum.in.dbpra.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logoutOrganizer")
public class OrganizerLogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private boolean loginSucessfull = false;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OrganizerLogoutServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("text/html");
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("JSESSIONID")) {
						System.out.println("JSESSIONID=" + cookie.getValue());
						break;
					}
				}
			}
			// invalidate the session if exists
			HttpSession session = request.getSession(false);
			System.out.println("UserName=" + session.getAttribute("username"));
			if (session != null) {
				session.invalidate();
			}
			response.sendRedirect("login-organizer.jsp");
		} catch (Throwable e) {
			request.setAttribute("error", e.getMessage());

		}
	}
}
