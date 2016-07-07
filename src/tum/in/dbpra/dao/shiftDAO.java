package tum.in.dbpra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import tum.in.dbpra.bean.ShiftBean;


public class shiftDAO extends DAO {

	private String query = "";
	private Connection con;
	private PreparedStatement preparedStmt;
	private ResultSet resultSet;

	public List<ShiftBean> getAllShift() throws ClassNotFoundException,
			SQLException {
		List<ShiftBean> allShift = new ArrayList<ShiftBean>();

		// query = "SELECT * FROM shift;";
		query = "SELECT section.name, employee.firstname, employee.lastname, shift.starttime, shift.endtime, shift.task FROM section, shift, employee WHERE section.sectionid = shift.sectionid AND employee.eid = shift.eid;";
		con = getConnection();
		preparedStmt = con.prepareStatement(query);
		resultSet = preparedStmt.executeQuery();

		while (resultSet.next()) {
			ShiftBean shift = new ShiftBean();
			shift.setSectionName(resultSet.getString("name"));
			String firstname = resultSet.getString("firstname");
			String lastname = resultSet.getString("lastname");
			shift.setEmployeeName(firstname + " " + lastname);
			shift.setStartTime(resultSet.getTimestamp("starttime"));
			shift.setEndTime(resultSet.getTimestamp("endtime"));
			shift.setTask(resultSet.getString("task"));
			allShift.add(shift);

		}
		return allShift;

	}

	public int postShift(ShiftBean shift) throws ClassNotFoundException,
			SQLException {
		int change = 0;
		query = "INSERT INTO shift VALUES (?, ? , ?, ?, ?);";

		con = getConnection();
		preparedStmt = con.prepareStatement(query);
		preparedStmt.setInt(1, shift.getSectionId());
		preparedStmt.setInt(2, shift.getEid());
		long millisStart = shift.getStartTime().getTime();
		preparedStmt.setTimestamp(3, new Timestamp(millisStart));
		long millisEnd = shift.getEndTime().getTime();
		preparedStmt.setTimestamp(4, new Timestamp(millisEnd));
		preparedStmt.setString(5, shift.getTask());
		change = preparedStmt.executeUpdate();

		return change;

	}

	public List<ShiftBean> filterShift(String column, String parameter)
			throws ClassNotFoundException, SQLException {
		List<ShiftBean> filteredShift = new ArrayList<ShiftBean>();
		query = "SELECT * FROM shift WHERE " + column + "LIKE ?";
		con = getConnection();
		preparedStmt = con.prepareStatement(query);

		switch (column) {
		case "Section Name":
			SectionDAO sectionDAO = new SectionDAO();
			int sectionID = sectionDAO.getSectionIdByName(parameter);
			preparedStmt.setInt(1, sectionID);
			resultSet = preparedStmt.executeQuery();
			while (resultSet.next()) {
				ShiftBean shift = new ShiftBean();
				shift.setSectionName(resultSet.getString("name"));
				String firstname = resultSet.getString("firstname");
				String lastname = resultSet.getString("lastname");
				shift.setEmployeeName(firstname + " " + lastname);
				shift.setStartTime(resultSet.getTimestamp("starttime"));
				shift.setEndTime(resultSet.getTimestamp("endtime"));
				shift.setTask(resultSet.getString("task"));
				filteredShift.add(shift);
			}
			break;
		case "Employee Name":
			break;

		case "Date":
			break;
		case "Start time":
			break;
		case "End time":
			break;
		case "Task":
			break;
		default:
			break;
		}
		return filteredShift;
	}

}
