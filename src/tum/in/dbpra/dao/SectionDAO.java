package tum.in.dbpra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tum.in.dbpra.dbutils.PGUtils;

public class SectionDAO extends DAO {

	public List<String> getAllSectionName() throws ClassNotFoundException,
			SQLException {
		List<String> allSectionName = new ArrayList<String>();

		//create customized query
		String query = "SELECT name FROM section order by name;";

		//set connection
		Connection con = PGUtils.createConnection();

		//set Prepared Statement
		PreparedStatement preparedStmt = con.prepareStatement(query);
						
		//retrieve results of query
		ResultSet resultSet = preparedStmt.executeQuery();

		while (resultSet.next()) {
			allSectionName.add(resultSet.getString("name"));
		}
		
		//close everything
		resultSet.close();
		preparedStmt.close();
		PGUtils.closeConnection(con);

		return allSectionName;
	}

	public int getSectionIdByName(String name) throws SectionNotFoundException,
			ClassNotFoundException, SQLException {
		int id = 0;

		//create customized query
		String query = "SELECT sectionid FROM section WHERE name=?;";
		
		//set connection
		Connection con = PGUtils.createConnection();
		
		//set Prepared Statement
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setString(1, name);
						
		//retrieve results of query
		ResultSet resultSet = preparedStmt.executeQuery();
		if (resultSet.next()) {
			id = resultSet.getInt("sectionid");
		} else {
			throw new SectionNotFoundException("Section not found");
		}
		
		//close everything
		resultSet.close();
		preparedStmt.close();
		PGUtils.closeConnection(con);

		return id;

	}

	public static class SectionNotFoundException extends Throwable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		SectionNotFoundException(String message) {
			super(message);
		}
	}
}
