package tum.in.dbpra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import tum.in.dbpra.bean.TimeslotBean;

public class TimeslotDAO extends DAO {
	
	public boolean insertTimeslots(ArrayList<TimeslotBean> timeslots) throws SQLException,ClassNotFoundException, TimeslotNotFoundException {
		
		//declare variables
		Integer insertions = 0;
		Connection con = getConnection();
		con.setAutoCommit(false);
		PreparedStatement pstmt = null;
		TimeslotBean timeslot = null;
		String query = "";
		
		//for each timeslot user wants to assign
		for(int i=0; i<timeslots.size(); i++){
			
			timeslot = timeslots.get(i);
			
			//reset result indicator
			Integer affectedRowCount = 0;
			
			//add savepoint
			Savepoint my_savepoint = con.setSavepoint();
			
			//create customized query
			query = "INSERT INTO timeslot (pid, sectionid, timebuildup, timeplay, timefinish, timegone) VALUES (?, ?, ?, ?, ?, ?);"
			+"SAVEPOINT my_savepoint_"+i+";";
			
			//set variables to Prepared Statement
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, timeslot.getpID());
			pstmt.setInt(2, timeslot.getSectionID());
			pstmt.setTimestamp(3, timeslot.getTimeBuildUp());
			pstmt.setTimestamp(4, timeslot.getTimePlay());
			pstmt.setTimestamp(5, timeslot.getTimeFinish());
			pstmt.setTimestamp(6, timeslot.getTimeGone());
			
			//try to execute query
			try{
				affectedRowCount = pstmt.executeUpdate();
			}catch(Exception e){
				//if it violates db rules rollback only this query till the savepoint
				con.rollback(my_savepoint);
			}
			
			//if query is successfull increment counter of insertions
			if (affectedRowCount > 0)
				insertions++;
			
		}

		//ask user for confirmation of assignment
		//showing how much of timeslots actually can be added
		//using dialog message box
		Object[] options = {"Yes, please", "No, thanks"};
		
		int answer = JOptionPane.showOptionDialog(null,
		"Do you confirm the assignment of "+insertions+"/"+timeslots.size()+" timeslot(s)?",
		"Confirmation",
		JOptionPane.YES_NO_OPTION,
		JOptionPane.QUESTION_MESSAGE,
		null,
		options,
		options[1]);
		
		//depending on user's answer commit or rollback the whole transaction
		if(answer==0)
			con.commit();
		else con.rollback();
		
		//close everything
		pstmt.close();
		con.close();

		if(answer==0)
			return true;
		return false;
	
	}

	public ArrayList<TimeslotBean> getTimeSlotsByStage(Integer stageID) throws SQLException, ClassNotFoundException {
		
		//create customized query
		String query = "SELECT * FROM timeslot WHERE sectionID = ?;";

		//set connection
		Connection con = getConnection();
		
		//set variables to Prepared Statement
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, stageID);

		//retrieve results of query
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

		//close everything
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
		Connection conn = getConnection();
		
		//set variables to Prepared Statement
		PreparedStatement pstmt = conn.prepareStatement(query);
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
		
		//close everything
		rs.close();
		pstmt.close();
		conn.close();
		
		return timeslots;
		
	}
	
	public static class TimeslotNotFoundException extends Throwable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		TimeslotNotFoundException(String message) {
			super(message);

		}
	}
}