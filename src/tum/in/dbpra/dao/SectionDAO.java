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

		String query = "SELECT name FROM section;";

		Connection con = getConnection();

		PreparedStatement preparedStmt = con.prepareStatement(query);
		ResultSet resultSet = preparedStmt.executeQuery();

		while (resultSet.next()) {
			allSectionName.add(resultSet.getString("name"));
		}
		closeConnection(con);
		return allSectionName;
	}

	public int getSectionIdByName(String name) throws ClassNotFoundException,
			SQLException {
		int id = 0;

		String query = "SELECT sectionid FROM section WHERE name=?;";
		Connection con = getConnection();
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setString(1, name);
		ResultSet resultSet = preparedStmt.executeQuery();
		if (resultSet.next()) {
			id = resultSet.getInt("sectionid");
		}
		resultSet.close();
		preparedStmt.close();
		PGUtils.closeConnection(con);

		return id;

	}

}
