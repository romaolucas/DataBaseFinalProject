package tum.in.dbpra.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.istack.internal.logging.Logger;

import tum.in.dbpra.bean.ProviderBean;
import tum.in.dbpra.dao.ProviderDAO;

/**
 * Servlet implementation class ProviderLogin
 */
@WebServlet("/providerRegistration")
public class ProviderRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProviderRegistration() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String passwordConfirm = request.getParameter("password_confirm");
		String address = request.getParameter("address");
		String website = request.getParameter("website");
		boolean registrationSuccessful = true;
		
		if (!password.equals(passwordConfirm))
			registrationSuccessful = false;
		if (registrationSuccessful) {
			ProviderBean provider = new ProviderBean(name, password, email, phone, address, website);
			ProviderDAO dao = new ProviderDAO();
			dao.registerProvider(provider);
			request.setAttribute("registrationStatus", registrationSuccessful);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/registration-successful.html");
			dispatcher.forward(request, response);
		}
		else {
			Logger log = Logger.getLogger(ProviderRegistration.class);
			log.info("here");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/login-provider.jsp");
			dispatcher.forward(request, response);
		}
	}

}
