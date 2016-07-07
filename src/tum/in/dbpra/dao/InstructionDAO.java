package tum.in.dbpra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import tum.in.dbpra.bean.InstructionBean;
import tum.in.dbpra.dbutils.PGUtils;

public class InstructionDAO {
	
	public void insertInstruction(int pid, String title, String description, String status, String type){
		Connection connection = null;
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		InstructionBean instructionBean = new InstructionBean();
		try {
			connection = PGUtils.createConnection();
			connection.setAutoCommit(false);
			String getInstructionID = "select max(instructionid) from instruction";
			ResultSet rsID;
			Statement stmtID;
			stmtID = connection.createStatement();
			rsID = stmtID.executeQuery(getInstructionID);
			if(rsID.next()){
				int instructionid = rsID.getInt("max")+1;
				preparedStatement = connection.prepareStatement(PGUtils.insertInstructions);
				preparedStatement.setInt(1, instructionid);
				preparedStatement.setInt(2, pid);
				preparedStatement.setString(3, title);
				preparedStatement.setString(4, description);
				preparedStatement.setString(5, status);
				preparedStatement.setString(6, type);
				java.util.Date date= new java.util.Date();
				Timestamp currentTS = new Timestamp(date.getTime());
				preparedStatement.setTimestamp(7, currentTS);
				preparedStatement.executeUpdate();
				connection.commit();
				preparedStatement.close();
				rsID.close();
				stmtID.close();
				connection.close();
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			PGUtils.closeConnection(connection);
		}
	}

}