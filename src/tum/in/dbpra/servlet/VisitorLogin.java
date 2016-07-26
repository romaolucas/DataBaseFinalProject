package tum.in.dbpra.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tum.in.dbpra.bean.VisitorBean;
import tum.in.dbpra.dao.VisitorDAO;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/welcomeVisitor")
public class VisitorLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private boolean loginSuccessful=false;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VisitorLogin() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//Auth servlet for visitor,and set id to session
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			request.getParameterValues("loginkeeping");
			VisitorDAO visitorDAO = new VisitorDAO();
			List<VisitorBean> visitorList = visitorDAO.getVisitors(username,password);
			if(visitorList.size()>0){
				   loginSuccessful=true;
				   request.setAttribute("visitor", visitorList);
				   HttpSession season=request.getSession();
				   season.setAttribute("username", visitorList.get(0).getUsername());
				   season.setAttribute("visitorid", visitorList.get(0).getVisitorId());	
			}			
			
		} catch (Throwable e) {
			request.setAttribute("error", e.getMessage());
		}
		request.setAttribute("loginStatus", loginSuccessful);
		if (loginSuccessful) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/welcomeVisitor.jsp");
			dispatcher.forward(request, response);
		}else{
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
			request.setAttribute("loginMessage", "Login unsucessful");
			dispatcher.forward(request, response);
		}
	}
}
