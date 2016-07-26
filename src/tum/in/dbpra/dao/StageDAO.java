package tum.in.dbpra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import tum.in.dbpra.bean.StageBean;
import tum.in.dbpra.dbutils.PGUtils;


public class StageDAO extends DAO {
	public ArrayList<StageBean> getAllStages() throws SQLException,
			ClassNotFoundException, StageNotFoundException {

		//create customized query
		String query = "SELECT * FROM Stage st, Section se WHERE st.sectionID=se.sectionID;";

		//set connection
		Connection con = PGUtils.createConnection();

		//set Prepared Statement
		PreparedStatement pstmt = con.prepareStatement(query);

		//retrieve results of query
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

		//close everything
		rs.close();
		pstmt.close();
		PGUtils.closeConnection(con);

		return stages;

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
