package tum.in.dbpra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import tum.in.dbpra.bean.BoothBean;
import tum.in.dbpra.bean.SponsorBean;

public class BoothDAO extends DAO{
	public ArrayList<BoothBean> getFreeBoothes() throws SQLException, ClassNotFoundException {
		
		String query = "SELECT * FROM booth b " +
				"	INNER JOIN section s ON b.sectionID=s.sectionID " +
				"	FULL OUTER JOIN representation a ON b.sectionID = a.sectionID" +
				"	WHERE a.pid is null;";
		
		Connection con = getConnection();
		
		PreparedStatement pstmt = con.prepareStatement(query);
				
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
		
		rs.close();
		pstmt.close();
		con.close();
		
		return boothes;
		
	}

	public Boolean assignBooth(SponsorBean sponsor, BoothBean booth) throws SQLException, ClassNotFoundException{

		String query = "INSERT INTO representation (pid, sectionid) VALUES (?, ?);";
		
		Connection con = getConnection();
		
		PreparedStatement pstmt = con.prepareStatement(query);
		
		pstmt.setInt(1, sponsor.getId());
		pstmt.setInt(2, booth.getSectionID());
		
		
		Integer effectedRowCount = pstmt.executeUpdate();
		
		pstmt.close();
		con.close();
		
		if (effectedRowCount>0) return true;
		return false;
	}	

	public ArrayList<BoothBean> getAllBoothes() throws SQLException, ClassNotFoundException {
		
		String query = "SELECT * FROM booth b INNER JOIN section s ON b.sectionID=s.sectionID FULL OUTER JOIN representation a ON b.sectionID = a.sectionID;";
		
		Connection con = getConnection();
		
		PreparedStatement pstmt = con.prepareStatement(query);
				
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
		
		rs.close();
		pstmt.close();
		con.close();
		
		return boothes;
		
	}
}
	
