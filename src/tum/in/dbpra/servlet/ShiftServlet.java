package tum.in.dbpra.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tum.in.dbpra.bean.*;
import tum.in.dbpra.dao.EmployeeDAO;
import tum.in.dbpra.dao.SectionDAO;
import tum.in.dbpra.dao.shiftDAO;



@WebServlet("/shift")
public class ShiftServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShiftServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		shiftDAO shiftDAO = new shiftDAO();
		try {
			List<ShiftBean> allShift = shiftDAO.getAllShift();
			if (allShift != null && !allShift.isEmpty()) {
				request.setAttribute("allShift", allShift);
			}
		} catch (Throwable e) {
			e.printStackTrace();
			request.setAttribute("error", e.toString() + e.getMessage());
		}
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/shift.jsp");
		dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		SectionDAO sectionDAO = new SectionDAO();
		EmployeeDAO employeeDAO = new EmployeeDAO();
		shiftDAO shiftDAO = new shiftDAO();

		if (request.getParameter("filter") != null
				&& request.getParameter("searchParam") != null) {
			String column = (String) request.getParameter("column");
			String parameter = (String) request.getParameter("searchParam");
			List<ShiftBean> filteredShift = new ArrayList<ShiftBean>();
			try {
				filteredShift = (List<ShiftBean>) shiftDAO.filterShift(column,
						parameter);
				request.setAttribute("allShifts", filteredShift);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			try {
				List<String> allSectionName = sectionDAO.getAllSectionName();
				if (allSectionName != null && !allSectionName.isEmpty()) {
					request.setAttribute("allSectionName", allSectionName);
				}

				List<String> allEmployeeName = employeeDAO.getAllEmployeeName();
				if (allEmployeeName != null && !allEmployeeName.isEmpty()) {
					request.setAttribute("allEmployeeName", allEmployeeName);
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/shiftAdd.jsp");
		dispatcher.forward(request, response);

	}

}
