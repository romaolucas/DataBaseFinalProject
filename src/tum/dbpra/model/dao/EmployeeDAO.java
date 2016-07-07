package com.tum.dbpra.model.dao;

import java.util.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDAO extends DAO {

	private String query = "";
	private Connection con;
	private PreparedStatement preparedStmt;
	private ResultSet resultSet;

	public List<String> getAllEmployeeName() throws ClassNotFoundException,
			SQLException {
		List<String> allEmployeeName = new ArrayList<String>();

		query = "SELECT firstname, lastname FROM employee;";
		con = getConnection();
		preparedStmt = con.prepareStatement(query);
		resultSet = preparedStmt.executeQuery();

		while (resultSet.next()) {
			String firstname = resultSet.getString("firstname");
			String lastname = resultSet.getString("lastname");

			allEmployeeName.add(firstname + " " + lastname);
		}

		return allEmployeeName;
	}

	public int getEmployeeIdByName(String name) throws SQLException,
			ClassNotFoundException {
		int id = 0;
		String[] parts = name.split(" ");
		String firstname = parts[0]; // 004
		String lastname = parts[1]; // 034556

		query = "SELECT eid FROM employee WHERE firstname=? AND lastname=?;";
		con = getConnection();
		preparedStmt = con.prepareStatement(query);
		preparedStmt.setString(1, firstname);
		preparedStmt.setString(2, lastname);

		resultSet = preparedStmt.executeQuery();
		if (resultSet.next()) {
			id = resultSet.getInt("eid");
		}

		return id;
	}
}
