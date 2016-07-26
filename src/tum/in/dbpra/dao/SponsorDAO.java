package tum.in.dbpra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tum.in.dbpra.bean.SponsorBean;
import tum.in.dbpra.dbutils.PGUtils;

//import tum.in.dbpra.dbutils.PGUtils;

public class SponsorDAO extends DAO {

	public void submitSponsorApp(SponsorBean sponsor, String comment) {
		String getAppId = "select max(applicationid) from application";
		String insertSponsor = "insert into sponsor values (?, ?, ?)";
		String insertApplication = "insert into application values (?, ?, ?, 'Sponsor', ?, 'In Process')";
		Connection con;
		PreparedStatement psSponsor, psApp;
		Statement stmt;
		ResultSet rs;
		try {
			//creates connection
            con = PGUtils.createConnection();
			con.setAutoCommit(false);
			stmt = con.createStatement();
			rs = stmt.executeQuery(getAppId);
			int applicationid = 1;
			if (rs.next())
				applicationid = Integer.parseInt(rs.getString("max")) + 1;
			rs.close();
			stmt.close();
            //inserts the sponsor
			psSponsor = con.prepareStatement(insertSponsor);
			psSponsor.setInt(1, sponsor.getId());
			psSponsor.setString(2, sponsor.getType());
			psSponsor.setDouble(3, sponsor.getAmount());
			psSponsor.executeUpdate();
			//inserts the application, by default its status is in process
            psApp = con.prepareStatement(insertApplication);
			psApp.setInt(1, applicationid);
			psApp.setInt(2, sponsor.getId());
			Date today = new Date();
			psApp.setTimestamp(3, new Timestamp(today.getTime()));
			if (comment != null)
				psApp.setString(4, comment);
			else
				psApp.setString(4, "");
			psApp.executeUpdate();
			con.commit();
			psSponsor.close();
			psApp.close();
			// PGUtils.closeConnection(con);
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * this function returns all the name, type and sponsorship of all sponsor.
	 * 
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */

	public ArrayList<SponsorBean> getAllSponsor() throws SQLException,
			ClassNotFoundException {

		// query
		String query = "SELECT * FROM Sponsor s,Provider p WHERE s.pID=p.pID;";

		// set connection
		Connection con = PGUtils.createConnection();

		// set PS
		PreparedStatement pstmt = con.prepareStatement(query);

		// retrieve results of the query
		ResultSet rs = pstmt.executeQuery();
		ArrayList<SponsorBean> sponsors = new ArrayList<SponsorBean>();

		while (rs.next()) {
			SponsorBean sponsor = new SponsorBean();
			sponsor.setId(rs.getInt("pID"));
			sponsor.setName(rs.getString("name"));
			sponsor.setType(rs.getString("type"));
			sponsor.setAmount(rs.getDouble("amount"));
			sponsor.setPhone(rs.getString("phoneNumber"));
			sponsor.setWebsite(rs.getString("website"));
			sponsor.setAddress(rs.getString("address"));
			sponsor.setEmail(rs.getString("email"));
			sponsor.setPassword(rs.getString("password"));

			sponsors.add(sponsor);
		}
		// close everything
		rs.close();
		pstmt.close();
		con.close();

		return sponsors;

	}

}