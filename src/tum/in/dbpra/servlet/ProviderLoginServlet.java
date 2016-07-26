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
			//Get email and password from the form
			String email = request.getParameter("username");
			String password = request.getParameter("password");
			//Get info about the keepLoggedin - not really used so far
			String keepLoggedin[] = request.getParameterValues("loginkeeping");
			//Create a providerDAO
			ProviderDAO providerDAO = new ProviderDAO();
			//Use ProviderDAO to use the getProviders method using the following arguments
			List<ProviderBean> providerList = providerDAO.getProviders(email,
					password);
			log.info(Integer.toString(providerList.size()));
			if (providerList.size() > 0) {
				//If the returned list size is positive, then we have that provider in DB - login successful -
				loginSuccessful = true;
				//Check if provider has application
				boolean hasApplication = providerDAO
						.hasApplication(providerList.get(0));
				//Set those attributes in the request
				request.setAttribute("providers", providerList);
				request.setAttribute("application", hasApplication);
				//Create the session
				HttpSession session = request.getSession();
				session.setAttribute("email", providerList.get(0).getEmail());
				//Set max inactive interval to 1 hour
				session.setMaxInactiveInterval(60 * 60);
				//Create cookies using email
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
			//If login is successful, direct to welcome page
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/welcome-provider.jsp");
			dispatcher.forward(request, response);
			// response.sendRedirect("welcome-provider.jsp");
		} else {
			//If login is not successful, direct to the same login page
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/login-provider.jsp");
			request.setAttribute("loginMessage", "Login unsuccessful");
			dispatcher.forward(request, response);
		}
	}
}