package tum.in.dbpra.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;

public class PGUtils {
	// TODO: change this value to the URL value of the particular database
	// system that is currently working
	public static final String URL = "jdbc:postgresql:";
	// TODO: change this value to the user id value of the particular database
	// system that is currently working
	public static final String database = "musical_festival";
	public static final String user = "aisulu";
	// TODO: change this value to the password value of the particular database
	// system that is currently working
	public static final String password = "251244";

	// shared methods
	public static Connection createConnection() {
		Connection openConnection = null;
		try {
			Class.forName("org.postgresql.Driver");
			openConnection = DriverManager.getConnection(URL + database, user,
					password);
			openConnection
					.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return openConnection;
	}

	public static void closeConnection(Connection closingConnection) {
		try {
			closingConnection.close();
		} catch (Exception ignored) {
			System.out.println(ignored.getMessage());
		}
	}
}