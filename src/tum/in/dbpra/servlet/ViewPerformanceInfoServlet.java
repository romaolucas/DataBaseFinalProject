package tum.in.dbpra.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tum.in.dbpra.bean.ProviderBean;
import tum.in.dbpra.bean.TimeSlotBeanProvider;
import tum.in.dbpra.dao.ProviderDAO;

/**
 * Servlet implementation class ViewPerformanceInfoServlet
 */
@WebServlet("/ViewPerformanceInfoServlet")
public class ViewPerformanceInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private boolean loginSuccessful = false;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewPerformanceInfoServlet() {
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
			//Get session email
			HttpSession session = request.getSession(false);
			String email = (String) session.getAttribute("email");
			ProviderDAO providerDAO = new ProviderDAO();
			//Use providerDAO to get the provider id using the session email
			List<ProviderBean> providerList = providerDAO.getProviders(email);
			//Use TimeslotbeanProvider to get the timeslots using the provider id
			List<TimeSlotBeanProvider> timeslots = providerDAO
					.getTimeSlots(providerList.get(0).getId());
			//If the provider has one or more timeslots
			if (timeslots.size() > 0) {
				//Set the timeslots attribute to the list of timeslots and pass it through the request
				request.setAttribute("timeslots", timeslots);
				//Dispatch to the view performance info jsp
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("/view-performance-info.jsp");
				dispatcher.forward(request, response);
			} else {
				// No timeslots assigned
			}
		} catch (Throwable e) {
			request.setAttribute("error", e.getMessage());

		}
	}
}