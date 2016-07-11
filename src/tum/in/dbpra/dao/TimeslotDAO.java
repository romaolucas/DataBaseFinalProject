package tum.in.dbpra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

	public ArrayList<TimeslotBean> getTimeSlots(TimeslotBean timeslotBean) throws SQLException, ClassNotFoundException {
		
		ArrayList<TimeslotBean> timeslots = new ArrayList<TimeslotBean>();
		Map<Integer, Object> pstmtVariables = new HashMap<Integer, Object>();
		
		//create customized query
		String query = "SELECT * FROM timeslot ";
		
		if(timeslotBean.getpID()!=null || timeslotBean.getSectionID()!=null || timeslotBean.getTimeBuildUp()!=null || timeslotBean.getTimeGone()!=null){
			int counter=1;
			query+="WHERE ";
			
			if(timeslotBean.getpID()!=null){
				query+="pID = ? ";
				pstmtVariables.put(counter++, timeslotBean.getpID());
			}
			
			if(timeslotBean.getSectionID()!=null){
				if(counter!=1) query+="AND ";
				query+="sectionID = ?";
				pstmtVariables.put(counter++, timeslotBean.getSectionID());
			}
			
			if(timeslotBean.getTimeBuildUp()!=null){
				if(counter!=1) query+="AND ";
				query+="timeBuildUp >= ?";
				pstmtVariables.put(counter++, timeslotBean.getTimeBuildUp());
			}
			
			if(timeslotBean.getTimeGone()!=null){
				if(counter!=1) query+="AND ";
				query+="timeGone <= ?";
				pstmtVariables.put(counter++, timeslotBean.getTimeGone());
			}
		}
		
		//set connection
		Connection con = getConnection();
		
		//set variables to Prepared Statement
		PreparedStatement pstmt = con.prepareStatement(query);
		for(Map.Entry<Integer, Object> entry:pstmtVariables.entrySet()){
			if(entry.getValue().getClass().equals(Integer.class)){
				pstmt.setInt(entry.getKey(), (Integer)entry.getValue());
			}
			if(entry.getValue().getClass().equals(Timestamp.class)){
				pstmt.setTimestamp(entry.getKey(), (Timestamp)entry.getValue());
			}
		}
		
		
		//retrive results of query
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
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