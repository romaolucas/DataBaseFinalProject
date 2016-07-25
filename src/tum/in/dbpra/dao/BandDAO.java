package tum.in.dbpra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import tum.in.dbpra.bean.BandBean;
import tum.in.dbpra.dbutils.PGUtils;


public class BandDAO extends DAO {
	public ArrayList<BandBean> getAllBands() throws SQLException, BandNotFoundException, ClassNotFoundException {

		//query
		String query = "SELECT * FROM Band b,Provider p WHERE b.pID = p.pID;";

		//set connection
		Connection con = PGUtils.createConnection();


		//set Prepared Statement
		PreparedStatement pstmt = con.prepareStatement(query);

		//retrieve results of query
		ResultSet rs = pstmt.executeQuery();
		ArrayList<BandBean> bands = new ArrayList<BandBean>();

		while (rs.next()) {
			BandBean band = new BandBean();
			band.setpID(rs.getInt("pID"));
			band.setName(rs.getString("name"));
			band.setStyle(rs.getString("style"));
			band.setCharge(rs.getDouble("charge"));
			band.setCountry(rs.getString("country"));
			band.setPhoneNumber(rs.getString("phoneNumber"));
			band.setWebsite(rs.getString("website"));
			band.setAddress(rs.getString("address"));
			band.setEmail(rs.getString("email"));
			band.setPassword(rs.getString("password"));

			bands.add(band);
		}

		//close everything
		rs.close();
		pstmt.close();
		con.close();

		return bands;

	}

	
	public static class BandNotFoundException extends Throwable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		BandNotFoundException(String message) {
			super(message);

		}
	}
}