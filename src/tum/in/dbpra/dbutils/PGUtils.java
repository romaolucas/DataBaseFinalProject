package tum.in.dbpra.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;

public class PGUtils {
	// TODO: change this value to the URL value of the particular database
	// system that is currently working
	public static final String URL = "jdbc:postgresql://localhost:5432/festival";
	// TODO: change this value to the user id value of the particular database
	// system that is currently working
	public static final String user = "postgres";
	// TODO: change this value to the password value of the particular database
	// system that is currently working
	public static final String password = "dbpra";

	// query strings
	public static final String fetchVisitorCredentials = "select username,password from visitors where username = ? and password=?";
	public static final String fetchProviderCredentials = "select * from provider where email = ? and password = ?";
	public static final String fetchProviderCredentialsByEmail = "select * from provider where email = ?";
	public static final String fetchTimeSlots = "select * from timeslot where pid = ?";
	public static final String fetchInstructions = "select * from instruction where pid = ?";
	public static final String insertInstructions = "insert into instruction values (?,?,?,?,?,?,?)";

	// query strings
	public static final String fetchVisitor = "select * from visitor where username = ? and password=?";
	public static final String insertVisitor = "INSERT INTO visitor(visitorid, firstname, lastname, username, password, address,phone, email)"
			+ "VALUES (9, ?, ?, ?, ?, ?, ? ,?);";
	public static final String fetchRoomDetails = "	select a.roomno,a.address,a.rent,a.capacity,a.pets,a.smoking,r.checkindate,r.checkoutdate"
			+ " from Reservation as r,Accommodation as a	where r.VisitorID=? and r.roomid=a.roomid";
	public static final String fetchRoomByCapasity = "select a.roomno,a.address,a.rent,a.capacity,a.pets,a.smoking from Accommodation a where capacity=? ";
	public static final String fetchTicketCagory = "select * from ticketcategory order by price";
	public static final String fetchRFIDDetails = "select w.status,w.balance,w.activationtime,w.rfid from wristband as w where w.rfid in (select rfid from ticket where visitorid =? ) and w.status=true";
	public static final String fetchTicketByVisitor = "select t.ticketid,tc.name,t.purchasedate,tc.price from ticketcategory as tc,ticket as t where t.visitorid=? and tc.categoryid=t.categoryid";
	public static final String insertIntoTicket = "insert into ticket (tickectid,visitorid,rfid,categoryid,purchasedate) values(default,?,?,?,now())";

	// shared methods
	public static Connection createConnection() {
		Connection openConnection = null;
		try {
			Class.forName("org.postgresql.Driver");
			openConnection = DriverManager.getConnection(URL, user, password);
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