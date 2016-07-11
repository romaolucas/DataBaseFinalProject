package tum.in.dbpra.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
			SectionDAO sectionDAO = new SectionDAO();
			List<String> allSectionName = sectionDAO.getAllSectionName();
			if (allSectionName != null && !allSectionName.isEmpty()) {
				request.setAttribute("allSectionName", allSectionName);
			}
			EmployeeDAO employeeDAO = new EmployeeDAO();
			List<String> allEmployeeName = employeeDAO.getAllEmployeeName();
			if (allEmployeeName != null && !allEmployeeName.isEmpty()) {
				request.setAttribute("allEmployeeName", allEmployeeName);
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
		try {
			SectionDAO sectionDAO = new SectionDAO();
			EmployeeDAO employeeDAO = new EmployeeDAO();
			shiftDAO shiftDAO = new shiftDAO();
			List<String> allSectionName = sectionDAO.getAllSectionName();
			if (allSectionName != null && !allSectionName.isEmpty()) {
				request.setAttribute("allSectionName", allSectionName);
			}

			List<String> allEmployeeName = employeeDAO.getAllEmployeeName();
			if (allEmployeeName != null && !allEmployeeName.isEmpty()) {
				request.setAttribute("allEmployeeName", allEmployeeName);
			}

			if (request.getParameter("sectionName") != ""
					&& request.getParameter("sectionName") != null) {
				System.out.println(request.getParameter("sectionName"));
				if (request.getParameter("sectionName").equals("All")) {
					System.out.println("not filtered");
					List<ShiftBean> notFilteredShift = new ArrayList<ShiftBean>();

					notFilteredShift = (List<ShiftBean>) shiftDAO.getAllShift();

					request.setAttribute("allShift", notFilteredShift);

				} else {

					String parameter = (String) request
							.getParameter("sectionName");
					System.out.println("Section: " + parameter);
					List<ShiftBean> filteredShift = new ArrayList<ShiftBean>();

					filteredShift = (List<ShiftBean>) shiftDAO
							.getShiftBySectionName(parameter);
					request.setAttribute("allShift", filteredShift);
					System.out.println("filtered");

				}
			}
			if (request.getParameter("employeeName") != ""
					&& request.getParameter("employeeName") != null) {
				System.out.println(request.getParameter("employeeName"));
				if (request.getParameter("employeeName").equals("All")) {
					System.out.println("not filtered");
					List<ShiftBean> notFilteredShift = new ArrayList<ShiftBean>();

					notFilteredShift = (List<ShiftBean>) shiftDAO.getAllShift();

					request.setAttribute("allShift", notFilteredShift);

				} else {

					String parameter = (String) request
							.getParameter("employeeName");
					System.out.println("Employee: " + parameter);
					List<ShiftBean> filteredShift = new ArrayList<ShiftBean>();

					filteredShift = (List<ShiftBean>) shiftDAO
							.getShiftByEmployeeName(parameter);
					request.setAttribute("allShift", filteredShift);
					System.out.println("filtered");

				}

			}

			if (request.getParameter("filter") != null) {
				String parameter = (String) request
						.getParameter("searchPattern");
				System.out.println("Pattern: " + parameter);
				List<ShiftBean> filteredShift = new ArrayList<ShiftBean>();

				filteredShift = (List<ShiftBean>) shiftDAO
						.getShiftByTask(parameter);
				request.setAttribute("allShift", filteredShift);
				System.out.println("filtered");
				System.out.println("not filtered");
				// List<ShiftBean> notFilteredShift = new
				// ArrayList<ShiftBean>();
				//
				// notFilteredShift = (List<ShiftBean>) shiftDAO.getAllShift();
				//
				// request.setAttribute("allShift", notFilteredShift);

			} else {
				// todo
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (request.getParameter("add") != null) {

			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/shiftAdd.jsp");
			dispatcher.forward(request, response);
		} else {
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/shift.jsp");
			dispatcher.forward(request, response);
		}

	}

}
