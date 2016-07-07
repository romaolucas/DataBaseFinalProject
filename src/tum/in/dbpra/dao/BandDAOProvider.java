package tum.in.dbpra.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.Date;

import tum.in.dbpra.bean.BandBeanProvider;
import tum.in.dbpra.dbutils.PGUtils;

public class BandDAOProvider {
	public void submitBandApp(BandBeanProvider band, String comment) {
		String getAppId = "select max(applicationid) from application";
		String insertBand = "insert into band values (?, ?, ?, ?)";
		String insertApplication = "insert into application values (?, ?, ?, 'Band', ?, 'In Process')";
		Connection con;
		PreparedStatement psBand, psApp;
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
			psBand = con.prepareStatement(insertBand);
			psBand.setInt(1, band.getId());
			psBand.setString(2, band.getStyle());
			psBand.setDouble(3, band.getCharge());
			psBand.setString(4, band.getCountry());
			psBand.executeUpdate();
			psApp = con.prepareStatement(insertApplication);
			psApp.setInt(1, applicationid);
			psApp.setInt(2, band.getId());
			Date today = new Date();
			psApp.setTimestamp(3, new Timestamp(today.getTime()));
			if (comment != null)
				psApp.setString(4, comment);
			else
				psApp.setString(4, "");
			psApp.executeUpdate();
			con.commit();
			psBand.close();
			psApp.close();
			PGUtils.closeConnection(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}