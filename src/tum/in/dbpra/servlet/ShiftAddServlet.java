package tum.in.dbpra.servlet;

import java.io.IOException;
import java.util.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import tum.in.dbpra.bean.ShiftBean;
import tum.in.dbpra.dao.EmployeeDAO;
import tum.in.dbpra.dao.SectionDAO;
import tum.in.dbpra.dao.shiftDAO;

@WebServlet("/shiftAdd")
public class ShiftAddServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ShiftAddServlet() {
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

		SectionDAO sectionDAO = new SectionDAO();
		EmployeeDAO employeeDAO = new EmployeeDAO();
		shiftDAO shiftDAO = new shiftDAO();
		String sectionName = (String) request.getParameter("sectionName");
		String employeeName = (String) request.getParameter("employeeName");
		String starttime = (String) request.getParameter("date") + " "
				+ (String) request.getParameter("starttime") + ":00";
		String endTime = (String) request.getParameter("date") + " "
				+ (String) request.getParameter("endtime") + ":00";
		String task = (String) request.getParameter("task");

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			int sectionId = sectionDAO.getSectionIdByName(sectionName);
			int employeeId = employeeDAO.getEmployeeIdByName(employeeName);

			ShiftBean shiftBean = new ShiftBean();
			shiftBean.setEid(employeeId);
			shiftBean.setSectionId(sectionId);

			// shiftBean.setStartTime(new Date(System.currentTimeMillis()));

			shiftBean.setStartTime(new Timestamp(formatter.parse(starttime)
					.getTime()));
			// shiftBean.setEndTime(new Date(System.currentTimeMillis()));
			// shiftBean.setEndTime((Date) formatter.parse(endTime));
			shiftBean.setEndTime(new Timestamp(formatter.parse(endTime)
					.getTime()));

			shiftBean.setTask(task);

			int change = shiftDAO.postShift(shiftBean);
			System.out.println("CHANGED. " + change);
			if (change > 0) {
				request.setAttribute("success", "You request succeed");

				List<ShiftBean> allShift = shiftDAO.getAllShift();
				request.setAttribute("allShift", allShift);
				List<String> allSectionName = sectionDAO.getAllSectionName();
				if (allSectionName != null && !allSectionName.isEmpty()) {
					request.setAttribute("allSectionName", allSectionName);
				}
				
				List<String> allEmployeeName = employeeDAO.getAllEmployeeName();
				if (allEmployeeName != null && !allEmployeeName.isEmpty()) {
					request.setAttribute("allEmployeeName", allEmployeeName);
				}
				JOptionPane.showMessageDialog(null,
						"The Shift has been added successfully");
			} else {
				request.setAttribute("error", "Your request failed");
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/shift.jsp");
		dispatcher.forward(request, response);

	}
}
