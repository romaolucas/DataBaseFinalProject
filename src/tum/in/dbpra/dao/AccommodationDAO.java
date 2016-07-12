package tum.in.dbpra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import tum.in.dbpra.bean.AccommodationBean;
import tum.in.dbpra.bean.VisitorBean;
import tum.in.dbpra.dbutils.PGUtils;

public class AccommodationDAO {
	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	AccommodationBean acbean;
	
	public List<AccommodationBean> getAccomodation(int visitorID){
		List<AccommodationBean> roomList =new ArrayList<AccommodationBean>();
		try {
			connection = PGUtils.createConnection();
			connection.setAutoCommit(false);

			// Fetch supplier key from the supplier table using the supplier
			// name that user provides as input
			System.out.println("Visitor id:"+visitorID);
			preparedStatement = connection.prepareStatement(PGUtils.fetchRoomDetails);
			preparedStatement.setInt(1, visitorID);
			
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				do {
					//System.out.println("orders");
					acbean = new AccommodationBean();
					acbean.setRoomno(resultSet.getInt(1));
					System.out.println("In room no");
					acbean.setAddress(resultSet.getString(2));
					acbean.setRent(resultSet.getDouble(3));
					acbean.setCapasity(resultSet.getInt(4));
					acbean.setPets(resultSet.getBoolean(5));
					acbean.setSmoking(resultSet.getBoolean(6));
					acbean.setStartdate(resultSet.getDate(7));
					acbean.setEndDate(resultSet.getDate(8));
					roomList.add(acbean);
				} while (resultSet.next());
				resultSet.close();
				preparedStatement.close();
			} else {
				roomList = null;
			}
			return roomList;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			PGUtils.closeConnection(connection);
		}
		return roomList;
		
	}
	
	public List<AccommodationBean> getRoomByCapasity(int number){
		List<AccommodationBean> roomList =new ArrayList<AccommodationBean>();
		try{
			
			connection=PGUtils.createConnection();
			preparedStatement=connection.prepareStatement(PGUtils.fetchRoomByCapasity);	
			preparedStatement.setInt(1, number);
			resultSet=preparedStatement.executeQuery();
			while (resultSet.next()){
				acbean = new AccommodationBean();
				//System.out.println("in resultset of getRoomByCapasity:"+resultSet.getInt(1));
				acbean.setRoomno(resultSet.getInt(1));
				acbean.setAddress(resultSet.getString(2));
				acbean.setRent(resultSet.getDouble(3));
				acbean.setCapasity(resultSet.getInt(4));
				acbean.setPets(resultSet.getBoolean(5));
				acbean.setSmoking(resultSet.getBoolean(6));
				acbean.setCheckinDate(resultSet.getDate(7));
				acbean.setCheckoutDate(resultSet.getDate(8));
				roomList.add(acbean);
			}
			resultSet.close();
			preparedStatement.close();
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error in RoomByCapacity:"+e.getMessage());
			e.printStackTrace();
			
		}finally {
			PGUtils.closeConnection(connection);
		}
		return roomList;
	}
}
