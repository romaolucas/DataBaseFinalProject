package tum.in.dbpra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import tum.in.dbpra.bean.BandBean;


public class BandDAO extends DAO {
	public ArrayList<BandBean> getAllBands() throws SQLException,
			ClassNotFoundException {

		String query = "SELECT * FROM Band b,Provider p WHERE b.pID=p.pID;";

		Connection con = getConnection();

		PreparedStatement pstmt = con.prepareStatement(query);

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

		rs.close();
		pstmt.close();
		con.close();

		return bands;

	}

	public Integer getBandIdByName(String name) throws BandNotFoundException,
			SQLException, ClassNotFoundException {

		String query = "SELECT pid FROM provider WHERE name = ?;";

		Connection con = getConnection();

		PreparedStatement pstmt = con.prepareStatement(query);

		pstmt.setString(1, name);

		ResultSet rs = pstmt.executeQuery();

		if (rs.next()) {
			return rs.getInt("pid");
		} else {
			throw new BandNotFoundException("There is no Band with name "
					+ name + "!");
		}

		// rs.close();
		// pstmt.close();
		// con.close();

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