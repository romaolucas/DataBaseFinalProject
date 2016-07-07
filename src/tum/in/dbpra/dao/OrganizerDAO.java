package tum.in.dbpra.dao;

import java.sql.*;

import tum.in.dbpra.bean.OrganizerBean;


public class OrganizerDAO extends DAO {

	private String query = "";
	private PreparedStatement preparedStmt = null;
	private ResultSet resultSet = null;
	private boolean success = false;

	public boolean validate(String username, String password)
			throws ClassNotFoundException, SQLException {

		query = "SELECT * FROM organizer WHERE username= ? AND password= ?;";
		Connection con = getConnection();

		preparedStmt = con.prepareStatement(query);

		preparedStmt.setString(1, username);
		preparedStmt.setString(2, password);

		resultSet = preparedStmt.executeQuery();

		success = resultSet.next();
		
		resultSet.close();
		preparedStmt.close();
		con.close();

		return success;
	}

	public boolean test() {
		return true;
	}
}
