package com.tum.dbpra.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SectionDAO extends DAO {

	private String query = "";
	private Connection con;
	private PreparedStatement preparedStmt;
	private ResultSet resultSet;

	public List<String> getAllSectionName() throws ClassNotFoundException,
			SQLException {
		List<String> allSectionName = new ArrayList<String>();

		query = "SELECT name FROM section;";

		con = getConnection();

		preparedStmt = con.prepareStatement(query);
		resultSet = preparedStmt.executeQuery();

		while (resultSet.next()) {
			allSectionName.add(resultSet.getString("name"));
		}
		closeConnection(con);
		return allSectionName;
	}

	public int getSectionIdByName(String name) throws ClassNotFoundException,
			SQLException {
		int id = 0;

		query = "SELECT sectionid FROM section WHERE name=?;";
		con = getConnection();
		preparedStmt = con.prepareStatement(query);
		preparedStmt.setString(1, name);
		resultSet = preparedStmt.executeQuery();
		if (resultSet.next()) {
			id = resultSet.getInt("sectionid");
		}

		return id;

	}

}
