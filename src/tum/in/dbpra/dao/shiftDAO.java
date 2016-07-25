package tum.in.dbpra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import tum.in.dbpra.bean.ShiftBean;
import tum.in.dbpra.dao.SectionDAO.SectionNotFoundException;
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
		Connection con = PGUtils.createConnection();
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
	 * @throws ShiftNotFoundException
	 */
	public int postShift(ShiftBean shift) throws ClassNotFoundException,
			SQLException, ShiftNotFoundException {
		int rowChanged = 0;
		boolean add = true;
		boolean isOverlaping = false;
		Connection con = PGUtils.createConnection();
		con.setAutoCommit(false);
		String query = "select * from shift"
				+ " where eid=? order by starttime;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, shift.getEid());
		ResultSet rs = ps.executeQuery();
		// check if there are some shifts assigned to the employee
		if (rs.next()) {
			List<ShiftBean> allShift = new ArrayList<ShiftBean>();
			do {
				ShiftBean shiftBean = new ShiftBean();
				shiftBean.setStartTime(rs.getTimestamp("starttime"));
				shiftBean.setEndTime(rs.getTimestamp("endtime"));
				allShift.add(shiftBean);
			} while (rs.next());
			for (int i = 0; i < allShift.size(); i++) {
				// StartA and EndA are beginning and the end of the shift the
				// user wants to assign
				Timestamp startA = shift.getStartTime();
				Timestamp endA = shift.getEndTime();
				// Start B and EndB are beginning and the end of the shift the
				// employee has already assigned to
				Timestamp startB = allShift.get(i).getStartTime();
				Timestamp endB = allShift.get(i).getEndTime();

				// there is overlaping when startA <= endB and endA >= startB

				boolean isStartABeforeEndB = startA.before(endB);
				boolean isEndAAfterStartB = endA.after(startB);

				isOverlaping = isStartABeforeEndB && isEndAAfterStartB;
				if (isOverlaping) {
					add = false;
					throw new ShiftNotFoundException("The employee "
							+ shift.getEmployeeName()
							+ " is already booked from " + startB.toString()
							+ " to " + endB.toString() + "\n"
							+ "Please assign him a new shift");
				}

			}

		}
		if (add) {
			long millisStart = shift.getStartTime().getTime();

			long millisEnd = shift.getEndTime().getTime();
			String insertQuery = "INSERT INTO shift VALUES (?, ? , ?, ?, ?);";
			PreparedStatement preparedStmt = con.prepareStatement(insertQuery);
			preparedStmt.setInt(1, shift.getSectionId());
			preparedStmt.setInt(2, shift.getEid());
			preparedStmt.setTimestamp(3, new Timestamp(millisStart));
			preparedStmt.setTimestamp(4, new Timestamp(millisEnd));
			preparedStmt.setString(5, shift.getTask());
			rowChanged = preparedStmt.executeUpdate();

			if (rowChanged > 0) {
				con.commit();
			} else {
				con.rollback();
			}

		}

		return rowChanged;
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
	 * @throws SectionNotFoundException
	 */
	public List<ShiftBean> getShiftBySectionName(String sectionName)
			throws ClassNotFoundException, SQLException,
			SectionNotFoundException {
		List<ShiftBean> filteredShift = new ArrayList<ShiftBean>();
		SectionDAO sectionDAO = new SectionDAO();

		int sectionID = sectionDAO.getSectionIdByName(sectionName);

		String query = "SELECT section.name, employee.firstname, employee.lastname, shift.starttime, shift.endtime, shift.task FROM section, shift, employee WHERE section.sectionid = shift.sectionid AND shift.sectionid = ? AND employee.eid = shift.eid;";
		System.out.println("QUERY: " + query);
		Connection con = PGUtils.createConnection();
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

		Connection con = PGUtils.createConnection();
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
		Connection con = PGUtils.createConnection();
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
		Connection con = PGUtils.createConnection();
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

	public static class ShiftNotFoundException extends Throwable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		ShiftNotFoundException(String message) {
			super(message);
		}
	}

}
