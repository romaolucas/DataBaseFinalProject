package tum.in.dbpra.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.istack.internal.logging.Logger;

import tum.in.dbpra.bean.ProviderBean;
import tum.in.dbpra.dao.ProviderDAO;

/**
 * Servlet implementation class ProviderLoginServlet
 */
@WebServlet("/welcomeProvider")
public class ProviderLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private boolean loginSuccessful = false;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProviderLoginServlet() {
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
			Logger log = Logger.getLogger(ProviderLoginServlet.class);
			String email = request.getParameter("username");
			String password = request.getParameter("password");
			String keepLoggedin[] = request.getParameterValues("loginkeeping");
			ProviderDAO providerDAO = new ProviderDAO();
			List<ProviderBean> providerList = providerDAO.getProviders(email,
					password);
			log.info(Integer.toString(providerList.size()));
			if (providerList.size() > 0) {
				loginSuccessful = true;
				boolean hasApplication = providerDAO
						.hasApplication(providerList.get(0));
				request.setAttribute("providers", providerList);
				request.setAttribute("application", hasApplication);
				HttpSession session = request.getSession();
				session.setAttribute("email", providerList.get(0).getEmail());
				session.setMaxInactiveInterval(60 * 60);
				Cookie userEmail = new Cookie("email", providerList.get(0)
						.getEmail());
				userEmail.setMaxAge(60 * 60);
				response.addCookie(userEmail);
			}

		} catch (Throwable e) {
			request.setAttribute("error", e.getMessage());

		}
		request.setAttribute("loginStatus", loginSuccessful);
		if (loginSuccessful) {
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/welcome-provider.jsp");
			dispatcher.forward(request, response);
			// response.sendRedirect("welcome-provider.jsp");
		} else {
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/login-provider.jsp");
			request.setAttribute("loginMessage", "Login unsuccessful");
			dispatcher.forward(request, response);
		}
	}
}