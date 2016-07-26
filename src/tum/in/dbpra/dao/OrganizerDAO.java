package tum.in.dbpra.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

import tum.in.dbpra.dbutils.PGUtils;

public class OrganizerDAO extends DAO {

	private StringBuffer getPasswordMD5(String password)
			throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte pass[] = md.digest();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < pass.length; i++)
			sb.append(Integer.toString((pass[i] & 0xff) + 0x100, 16).substring(
					1));
		return sb;
	}

	//to login an organizer that is already in db
	public boolean validate(String username, String password)
			throws ClassNotFoundException, SQLException,
			NoSuchAlgorithmException {

		String query = "SELECT * FROM organizer WHERE username= ? AND password= ?;";
		Connection con = PGUtils.createConnection();

		PreparedStatement preparedStmt = con.prepareStatement(query);

		preparedStmt.setString(1, username);
		preparedStmt.setString(2, getPasswordMD5(password).toString());

		ResultSet resultSet = preparedStmt.executeQuery();

		boolean success = resultSet.next();

		resultSet.close();
		preparedStmt.close();
		PGUtils.closeConnection(con);

		return success;
	}
}
