package com.tum.dbpra.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.tum.dbpra.model.bean.StageBean;

public class StageDAO extends DAO {
	public ArrayList<StageBean> getAllStages() throws SQLException,
			ClassNotFoundException {

		String query = "SELECT * FROM Stage st, Section se WHERE st.sectionID=se.sectionID;";

		Connection con = getConnection();

		PreparedStatement pstmt = con.prepareStatement(query);

		ResultSet rs = pstmt.executeQuery();

		ArrayList<StageBean> stages = new ArrayList<StageBean>();

		while (rs.next()) {
			StageBean stage = new StageBean();
			stage.setSectionID(rs.getInt("sectionID"));
			stage.setCapacity(rs.getInt("capacity"));
			stage.setAreaID(rs.getInt("areaID"));
			stage.setName(rs.getString("name"));
			stage.setMetersquarres(rs.getInt("metersquarres"));

			stages.add(stage);
		}

		rs.close();
		pstmt.close();
		con.close();

		return stages;

	}

	public Integer getStageIdByName(String name) throws StageNotFoundException,
			SQLException, ClassNotFoundException {

		String query = "SELECT sectionID FROM section WHERE name = ?;";

		Connection con = getConnection();

		PreparedStatement pstmt = con.prepareStatement(query);

		pstmt.setString(1, name);

		ResultSet rs = pstmt.executeQuery();

		if (rs.next()) {
			return rs.getInt("sectionID");
		} else {
			throw new StageNotFoundException("There is no Stage with name "
					+ name + "!");
		}

		// rs.close();
		// pstmt.close();
		// con.close();

	}

	public static class StageNotFoundException extends Throwable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		StageNotFoundException(String message) {
			super(message);

		}
	}
}
