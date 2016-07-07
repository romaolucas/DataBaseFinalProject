package com.tum.dbpra.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tum.dbpra.model.bean.FestivalBean;

public class FestivalDAO extends DAO {
	public void getAllData(FestivalBean festival)
			throws CustomerNotFoundException, SQLException,
			ClassNotFoundException {

		String query = "SELECT * FROM festival;";

		Connection con = getConnection();

		PreparedStatement pstmt = con.prepareStatement(query);

		ResultSet rs = pstmt.executeQuery();

		if (rs.next()) {
			festival.setName(rs.getString("name"));
			festival.setStartDate(rs.getDate("startDate"));
			festival.setEndDate(rs.getDate("endDate"));
			festival.setStartTime(rs.getTime("startTime"));
			festival.setEndTime(rs.getTime("endTime"));

		} else {
			throw new CustomerNotFoundException("There is no Festval in db!");
		}

		rs.close();
		pstmt.close();
		con.close();

	}

	public static class CustomerNotFoundException extends Throwable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		CustomerNotFoundException(String message) {
			super(message);
		}
	}
}