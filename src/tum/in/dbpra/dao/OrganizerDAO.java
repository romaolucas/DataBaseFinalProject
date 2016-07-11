package tum.in.dbpra.dao;

import java.sql.*;

import tum.in.dbpra.bean.OrganizerBean;

public class OrganizerDAO extends DAO {

	public boolean validate(String username, String password)
			throws ClassNotFoundException, SQLException {

		String query = "SELECT * FROM organizer WHERE username= ? AND password= ?;";
		Connection con = getConnection();

		PreparedStatement preparedStmt = con.prepareStatement(query);

		preparedStmt.setString(1, username);
		preparedStmt.setString(2, password);

		ResultSet resultSet = preparedStmt.executeQuery();

		boolean success = resultSet.next();

		resultSet.close();
		preparedStmt.close();
		con.close();

		return success;
	}
}
