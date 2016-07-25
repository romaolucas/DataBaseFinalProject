package tum.in.dbpra.dao;

import java.util.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import tum.in.dbpra.dbutils.PGUtils;

public class EmployeeDAO extends DAO {

	public List<String> getAllEmployeeName() throws ClassNotFoundException,
			SQLException {
		List<String> allEmployeeName = new ArrayList<String>();

		String query = "SELECT firstname, lastname FROM employee order by firstname;";
		Connection con = PGUtils.createConnection();
		PreparedStatement preparedStmt = con.prepareStatement(query);
		ResultSet resultSet = preparedStmt.executeQuery();

		while (resultSet.next()) {
			String firstname = resultSet.getString("firstname");
			String lastname = resultSet.getString("lastname");

			allEmployeeName.add(firstname + " " + lastname);
		}
		resultSet.close();
		preparedStmt.close();
		PGUtils.closeConnection(con);

		return allEmployeeName;
	}

	/**
	 * this function retrieves the employeeID of an employee given his name :
	 * firstname lastname
	 * 
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int getEmployeeIdByName(String name) throws SQLException,
			ClassNotFoundException {
		int id = 0;
		// the array names contains firstname and lastname
		String[] names = name.split(" ");
		String firstname = names[0]; // frstname
		String lastname = names[1]; // lastname

		String query = "SELECT eid FROM employee WHERE firstname=? AND lastname=?;";
		Connection con = PGUtils.createConnection();
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setString(1, firstname);
		preparedStmt.setString(2, lastname);

		ResultSet resultSet = preparedStmt.executeQuery();
		if (resultSet.next()) {
			id = resultSet.getInt("eid");
		}

		resultSet.close();
		preparedStmt.close();
		PGUtils.closeConnection(con);

		return id;
	}
}
