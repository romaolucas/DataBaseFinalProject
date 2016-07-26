package tum.in.dbpra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import tum.in.dbpra.bean.FestivalBean;
import tum.in.dbpra.dbutils.PGUtils;


public class FestivalDAO extends DAO {
	public void getAllData(FestivalBean festival)
			throws FestivalNotFoundException, SQLException,
			ClassNotFoundException {

		//query
		String query = "SELECT * FROM festival;";

		//set connection
		Connection con = PGUtils.createConnection();

		//set PS
		PreparedStatement pstmt = con.prepareStatement(query);

		//retrieve results of the query
		ResultSet rs = pstmt.executeQuery();

		if (rs.next()) {
			festival.setName(rs.getString("name"));
			festival.setStartDate(rs.getDate("startDate"));
			festival.setEndDate(rs.getDate("endDate"));
			festival.setStartTime(rs.getTime("startTime"));
			festival.setEndTime(rs.getTime("endTime"));

		} else {
			throw new FestivalNotFoundException("There is no Festval in db!");
		}

		//close everything
		rs.close();
		pstmt.close();
		con.close();

	}

	public static class FestivalNotFoundException extends Throwable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		FestivalNotFoundException(String message) {
			super(message);
		}
	}
}