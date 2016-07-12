package tum.in.dbpra.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;

public class PGUtils {
	// TODO: change this value to the URL value of the particular database
	// system that is currently working
	public static final String URL = "jdbc:postgresql:";
	// TODO: change this value to the user id value of the particular database
	// system that is currently working
	public static final String database = "festival";
	public static final String user = "postgres";
	// TODO: change this value to the password value of the particular database
	// system that is currently working
	public static final String password = "dbpra";

	// queries from Visitor
	public static final String fetchVisitor = "select * from visitor where username = ? and password=?";
	public static final String insertVisitor = "INSERT INTO visitor(visitorid, firstname, lastname, username, password, address,phone, email)"
			+ "VALUES (9, ?, ?, ?, ?, ?, ? ,?);";
	public static final String fetchRoomDetails = "	select a.roomno,a.address,a.rent,a.capacity,a.pets,a.smoking,r.checkindate,r.checkoutdate"
			+ " from Reservation as r,Accommodation as a	where r.VisitorID=? and r.roomid=a.roomid";

	public static final String fetchRoomByCapasity = "with CurrentReservation(roomid,checkindate,checkoutdate) as "
			+ "(select roomid,checkindate,checkoutdate from reservation where checkoutdate>now())"
			+ "  SELECT a.roomno,a.address,a.rent,a.capacity,a.pets,a.smoking,cr.checkindate,cr.checkoutdate "
			+ "FROM accommodation as a LEFT OUTER JOIN CurrentReservation as cr ON (a.roomid = cr.roomid) where a.capacity=?";

	public static final String fetchTicketCagory = "select * from ticketcategory order by price";
	public static final String fetchRFIDDetails = "select w.status,w.balance,w.activationtime,w.rfid from wristband as w where w.rfid in (select rfid from ticket where visitorid =? ) and w.status=true";
	public static final String fetchTicketByVisitor = "select t.ticketid,tc.name,t.purchasedate,tc.price,t.activationTime from ticketcategory as tc,ticket as t where t.visitorid=? and tc.categoryid=t.categoryid";
	public static final String insertIntoTicket = "insert into ticket(ticketid,visitorid,rfid,categoryid,purchasedate) values ((select max(ticketid) from ticket)+1,?,?,?,now())";
	public static final String updateRFIDComments = "update wristband set comments=comments||? where rfid=?";
	public static final String fetchPurchase = "select p.name,p.category,pr.totalprice,pr.paymentdate,pr.quantity from product as p,purchase as pr where rfid=? and p.productid=pr.productid";

	// queries from Provider
	public static final String fetchProviderCredentials = "select * from provider where email = ? and password = ?";
	public static final String fetchProviderCredentialsByEmail = "select * from provider where email = ?";
	public static final String fetchTimeSlots = "select * from timeslot where pid = ?";
	public static final String fetchInstructions = "select * from instruction where pid = ?";
	public static final String insertInstructions = "insert into instruction values (?,?,?,?,?,?,?)";

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