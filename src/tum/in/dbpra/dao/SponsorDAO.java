package tum.in.dbpra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import tum.in.dbpra.bean.SponsorBean;
import tum.in.dbpra.dbutils.PGUtils;

public class SponsorDAO {
	public void submitSponsorApp(SponsorBean sponsor, String comment) {
		String getAppId = "select max(applicationid) from application";
		String insertSponsor = "insert into sponsor values (?, ?, ?)";
		String insertApplication = "insert into application values (?, ?, ?, 'Sponsor', ?, 'In Process')";
		Connection con;
		PreparedStatement psSponsor, psApp;
		Statement stmt;
		ResultSet rs;
		try {
			con = PGUtils.createConnection();
			con.setAutoCommit(false);
			stmt = con.createStatement();
			rs = stmt.executeQuery(getAppId);
			int applicationid = 1;
			if (rs.next())
				applicationid = Integer.parseInt(rs.getString("max")) + 1;
			rs.close();
			stmt.close();
			psSponsor = con.prepareStatement(insertSponsor);
			psSponsor.setInt(1, sponsor.getId());
			psSponsor.setString(2, sponsor.getType());
			psSponsor.setDouble(3, sponsor.getAmount());
			psSponsor.executeUpdate();
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
			PGUtils.closeConnection(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}