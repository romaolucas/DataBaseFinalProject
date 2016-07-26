package tum.in.dbpra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import tum.in.dbpra.bean.BoothBean;
import tum.in.dbpra.bean.SponsorBean;
import tum.in.dbpra.dbutils.PGUtils;

public class BoothDAO extends DAO{
	public ArrayList<BoothBean> getFreeBoothes() throws SQLException, ClassNotFoundException {
		
		//create customized query
		String query = "SELECT * FROM booth b " +
				"	INNER JOIN section s ON b.sectionID=s.sectionID " +
				"	FULL OUTER JOIN representation a ON b.sectionID = a.sectionID" +
				"	WHERE a.pid is null;";
		
		//set connection
		Connection con = PGUtils.createConnection();
		
		//set Prepared Statement
		PreparedStatement pstmt = con.prepareStatement(query);
				
		//retrieve results of query
		ResultSet rs = pstmt.executeQuery();
		ArrayList<BoothBean> boothes = new ArrayList<BoothBean>();
		
		while(rs.next()) {
			BoothBean booth = new BoothBean();
			booth.setSectionID(rs.getInt("sectionID"));
			booth.setEquipment(rs.getString("equipment"));
			booth.setService(rs.getString("service"));
			booth.setType(rs.getString("type"));
			booth.setAreaID(rs.getInt("areaID"));
			booth.setName(rs.getString("name"));
			booth.setMetersquarres(rs.getInt("metersquarres"));
			
			boothes.add(booth);
		}
		
		//close everything
		rs.close();
		pstmt.close();
		PGUtils.closeConnection(con);
		
		return boothes;
		
	}

	public Boolean assignBooth(SponsorBean sponsor, BoothBean booth) throws SQLException, ClassNotFoundException{

		//variable to check is query successfully executed
		Integer affectedRowCount = 0;
		
		//create customized query
		String query = "INSERT INTO representation (pid, sectionid) VALUES (?, ?);";
		
		//set connection
		Connection con = PGUtils.createConnection();
		con.setAutoCommit(false);
		
		//set variables to Prepared Statement
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, sponsor.getId());
		pstmt.setInt(2, booth.getSectionID());
		
		//try to execute the query
		try{
			affectedRowCount = pstmt.executeUpdate();
		}
		catch(Exception e){}
		
		//if query is successful commit it, otherwise rollback it
		if (affectedRowCount > 0) {
			con.commit();
		} else {
			con.rollback();
		}
		
		//close everything
		pstmt.close();
		PGUtils.closeConnection(con);
		
		if (affectedRowCount>0) return true;
		return false;
	}	

	public ArrayList<BoothBean> getAllBoothes() throws SQLException, ClassNotFoundException {
		
		//create customized query
		String query = "SELECT * FROM booth b INNER JOIN section s ON b.sectionID=s.sectionID FULL OUTER JOIN representation a ON b.sectionID = a.sectionID;";
		
		//set connection
		Connection con = PGUtils.createConnection();
		
		//set variables to Prepared Statement
		PreparedStatement pstmt = con.prepareStatement(query);
				
		//retrieve results of query
		ResultSet rs = pstmt.executeQuery();
		ArrayList<BoothBean> boothes = new ArrayList<BoothBean>();
		
		while(rs.next()) {
			BoothBean booth = new BoothBean();
			booth.setSectionID(rs.getInt("sectionID"));
			booth.setEquipment(rs.getString("equipment"));
			booth.setService(rs.getString("service"));
			booth.setType(rs.getString("type"));
			booth.setAreaID(rs.getInt("areaID"));
			booth.setName(rs.getString("name"));
			booth.setMetersquarres(rs.getInt("metersquarres"));
			booth.setpID(rs.getInt("pID"));
			
			boothes.add(booth);
		}
		
		//close everything
		rs.close();
		pstmt.close();
		PGUtils.closeConnection(con);
		
		return boothes;
		
	}
}
	
