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
import tum.in.dbpra.dbutils.PGUtils;

public class shiftDAO extends DAO {

	/**
	 * this function return all shifts stored in the database
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public List<ShiftBean> getAllShift() throws ClassNotFoundException,
			SQLException {
		List<ShiftBean> allShift = new ArrayList<ShiftBean>();

		// query = "SELECT * FROM shift;";
		String query = "SELECT section.name, employee.firstname, employee.lastname, shift.starttime, shift.endtime, shift.task FROM section, shift, employee WHERE section.sectionid = shift.sectionid AND employee.eid = shift.eid;";
		Connection con = getConnection();
		PreparedStatement preparedStmt = con.prepareStatement(query);
		ResultSet resultSet = preparedStmt.executeQuery();

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
		resultSet.close();
		preparedStmt.close();
		PGUtils.closeConnection(con);

		return allShift;

	}

	/**
	 * this function create a new entry in the table shift
	 * 
	 * @param shift
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int postShift(ShiftBean shift) throws ClassNotFoundException,
			SQLException {
		int change = 0;
		String query = "INSERT INTO shift VALUES (?, ? , ?, ?, ?);";

		Connection con = getConnection();
		con.setAutoCommit(false);
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setInt(1, shift.getSectionId());
		preparedStmt.setInt(2, shift.getEid());
		long millisStart = shift.getStartTime().getTime();
		preparedStmt.setTimestamp(3, new Timestamp(millisStart));
		long millisEnd = shift.getEndTime().getTime();
		preparedStmt.setTimestamp(4, new Timestamp(millisEnd));
		preparedStmt.setString(5, shift.getTask());
		change = preparedStmt.executeUpdate();

		if (change > 0) {
			con.commit();
		} else {
			con.rollback();
		}

		preparedStmt.close();
		PGUtils.closeConnection(con);

		return change;

	}

	/**
	 * this function retrieves shifts which match the search criteria defined by
	 * the user
	 * 
	 * @param column
	 * @param parameter
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<ShiftBean> getShiftBySectionName(String sectionName)
			throws ClassNotFoundException, SQLException {
		List<ShiftBean> filteredShift = new ArrayList<ShiftBean>();
		SectionDAO sectionDAO = new SectionDAO();

		int sectionID = sectionDAO.getSectionIdByName(sectionName);

		String query = "SELECT section.name, employee.firstname, employee.lastname, shift.starttime, shift.endtime, shift.task FROM section, shift, employee WHERE section.sectionid = shift.sectionid AND shift.sectionid = ? AND employee.eid = shift.eid;";
		System.out.println("QUERY: " + query);
		Connection con = getConnection();
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setInt(1, sectionID);
		ResultSet resultSet = preparedStmt.executeQuery();
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

		resultSet.close();
		preparedStmt.close();
		PGUtils.closeConnection(con);

		return filteredShift;

	}

	/**
	 * this function retrieves shift by starttime
	 * 
	 * @param starttime
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public List<ShiftBean> getShiftByStarttime(Timestamp starttime)
			throws ClassNotFoundException, SQLException {
		List<ShiftBean> filteredShift = new ArrayList<ShiftBean>();

		String query = "SELECT section.name, employee.firstname, employee.lastname, shift.starttime, shift.endtime, shift.task FROM section, shift, employee WHERE section.sectionid = shift.sectionid AND shift.starttime = ? AND employee.eid = shift.eid;";

		Connection con = getConnection();
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setTimestamp(1, starttime);
		ResultSet resultSet = preparedStmt.executeQuery();
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

		resultSet.close();
		preparedStmt.close();
		PGUtils.closeConnection(con);

		return filteredShift;
	}

	/**
	 * this function retrieves shift by Employee name
	 * 
	 * @param name
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public List<ShiftBean> getShiftByEmployeeName(String name)
			throws ClassNotFoundException, SQLException {
		List<ShiftBean> filteredShift = new ArrayList<ShiftBean>();
		String query = "SELECT section.name, employee.firstname, employee.lastname, shift.starttime, shift.endtime, shift.task "
				+ "FROM section, employee, shift "
				+ "WHERE section.sectionid = shift.sectionid AND employee.eid = shift.eid "
				+ "AND employee.firstname=? " + "AND employee.lastname=?;";
		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		String[] names = name.split(" ");
		String firstname = names[0];
		String lastname = names[1];
		ps.setString(1, firstname);
		ps.setString(2, lastname);

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			ShiftBean shift = new ShiftBean();
			shift.setSectionName(rs.getString("name"));
			shift.setEmployeeName(name);
			shift.setStartTime(rs.getTimestamp("starttime"));
			shift.setEndTime(rs.getTimestamp("endtime"));
			shift.setTask(rs.getString("task"));
			filteredShift.add(shift);
		}

		rs.close();
		ps.close();
		PGUtils.closeConnection(con);

		return filteredShift;
	}

	/**
	 * this function retrieves shift by task
	 * 
	 * @param name
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<ShiftBean> getShiftByTask(String name)
			throws ClassNotFoundException, SQLException {
		List<ShiftBean> filteredShift = new ArrayList<ShiftBean>();
		String query = "SELECT section.name, employee.firstname, employee.lastname, shift.starttime, shift.endtime, shift.task "
				+ "FROM section, shift, employee "
				+ "WHERE section.sectionid = shift.sectionid AND employee.eid = shift.eid "
				+ "AND LOWER(shift.task) LIKE LOWER(?);";
		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, "%" + name + "%");

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			ShiftBean shift = new ShiftBean();
			shift.setSectionName(rs.getString("name"));
			String firstname = rs.getString("firstname");
			String lastname = rs.getString("lastname");
			shift.setEmployeeName(firstname + " " + lastname);
			shift.setStartTime(rs.getTimestamp("starttime"));
			shift.setEndTime(rs.getTimestamp("endtime"));
			shift.setTask(rs.getString("task"));
			filteredShift.add(shift);
		}
		rs.close();
		ps.close();
		PGUtils.closeConnection(con);

		return filteredShift;
	}

}
