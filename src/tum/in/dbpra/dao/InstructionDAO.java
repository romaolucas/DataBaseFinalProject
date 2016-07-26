package tum.in.dbpra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import tum.in.dbpra.bean.InstructionBean;
import tum.in.dbpra.dbutils.PGUtils;

public class InstructionDAO {
	
	//This method inserts an instruction in DB providing the following arguments
	public void insertInstruction(int pid, String title, String description, String status, String type){
		Connection connection = null;
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		InstructionBean instructionBean = new InstructionBean();
		try {
			//Create connection to DB
			connection = PGUtils.createConnection();
			//Begin transaction
			connection.setAutoCommit(false);
			
			//Select the max instruction id, the inserted instrction will have the next value as its id
			String getInstructionID = "select max(instructionid) from instruction";
			String insertInstructions = "insert into instruction values (?,?,?,?,?,?,?)";
			ResultSet rsID;
			Statement stmtID;
			stmtID = connection.createStatement();
			rsID = stmtID.executeQuery(getInstructionID);
			if(rsID.next()){
				//Fill in values in the prepared statement
				int instructionid = rsID.getInt("max")+1;
				preparedStatement = connection.prepareStatement(insertInstructions);
				preparedStatement.setInt(1, instructionid);
				preparedStatement.setInt(2, pid);
				preparedStatement.setString(3, title);
				preparedStatement.setString(4, description);
				preparedStatement.setString(5, status);
				preparedStatement.setString(6, type);
				
				
				//Get the current timestamp
				java.util.Date date= new java.util.Date();
				Timestamp currentTS = new Timestamp(date.getTime());
				//Set the instruction time to the current timestamp
				preparedStatement.setTimestamp(7, currentTS);
				//Insert the instruction by executing the query
				preparedStatement.executeUpdate();
				//Finish transaction
				connection.commit();
				//Close ps and rs and stmt
				preparedStatement.close();
				rsID.close();
				stmtID.close();
				//Close connection
				PGUtils.closeConnection(connection);
				
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			PGUtils.closeConnection(connection);
		}
	}

}