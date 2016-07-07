package tum.in.dbpra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import tum.in.dbpra.bean.TimeslotBean;

public class TimeslotDAO extends DAO {

	public boolean insertTimeslot(TimeslotBean timeslot) throws SQLException,
			ClassNotFoundException {

		String query = "INSERT INTO timeslot (pid, sectionid, timebuildup, timeplay, timefinish, timegone) VALUES (?, ?, ?, ?, ?, ?);";

		Connection con = getConnection();

		PreparedStatement pstmt = con.prepareStatement(query);

		pstmt.setInt(1, timeslot.getpID());
		pstmt.setInt(2, timeslot.getSectionID());
		pstmt.setTimestamp(3, timeslot.getTimeBuildUp());
		pstmt.setTimestamp(4, timeslot.getTimePlay());
		pstmt.setTimestamp(5, timeslot.getTimeFinish());
		pstmt.setTimestamp(6, timeslot.getTimeGone());

		Integer effectedRowCount = pstmt.executeUpdate();

		pstmt.close();
		con.close();

		if (effectedRowCount > 0)
			return true;
		return false;

	}

	public ArrayList<TimeslotBean> getTimeSlotsByStage(Integer stageID)
			throws SQLException, ClassNotFoundException {

		String query = "SELECT * FROM timeslot WHERE sectionID = ?;";

		Connection con = getConnection();

		PreparedStatement pstmt = con.prepareStatement(query);

		pstmt.setInt(1, stageID);

		ResultSet rs = pstmt.executeQuery();

		ArrayList<TimeslotBean> timeslots = new ArrayList<TimeslotBean>();

		while (rs.next()) {
			TimeslotBean timeslot = new TimeslotBean();
			timeslot.setTimeslotID(rs.getInt("timeslotID"));
			timeslot.setpID(rs.getInt("pID"));
			timeslot.setSectionID(rs.getInt("sectionID"));
			timeslot.setTimeBuildUp(rs.getTimestamp("timeBuildUp"));
			timeslot.setTimePlay(rs.getTimestamp("timePlay"));
			timeslot.setTimeFinish(rs.getTimestamp("timeFinish"));
			timeslot.setTimeGone(rs.getTimestamp("timeGone"));

			timeslots.add(timeslot);
		}

		rs.close();
		pstmt.close();
		con.close();

		return timeslots;

	}
}