package tum.in.dbpra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import tum.in.dbpra.bean.AccommodationBean;
import tum.in.dbpra.dbutils.PGUtils;

public class AccommodationDAO {
	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	AccommodationBean acbean;
	//get all the room booked by visitor
	public List<AccommodationBean> getAccomodation(int visitorID){
		List<AccommodationBean> roomList =new ArrayList<AccommodationBean>();
		try {
			connection = PGUtils.createConnection();
			connection.setAutoCommit(false);
			//Query to fetch room details booked by the logged in visitor
			String fetchRoomDetails = "	select a.roomno,a.address,a.rent,a.capacity,a.pets,a.smoking,r.checkindate,r.checkoutdate"
					+ " from Reservation as r,Accommodation as a	where r.VisitorID=? and r.roomid=a.roomid";
			System.out.println("Visitor id:"+visitorID);
			preparedStatement = connection.prepareStatement(fetchRoomDetails);
			preparedStatement.setInt(1, visitorID);
			
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				do {
					//System.out.println("orders");
					acbean = new AccommodationBean();
					acbean.setRoomno(resultSet.getInt(1));
					acbean.setAddress(resultSet.getString(2));
					acbean.setRent(resultSet.getDouble(3));
					acbean.setCapacity(resultSet.getInt(4));
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
	//search room by capacity as per user input using left outer join
	public List<AccommodationBean> getRoomByCapacity(int number){
		List<AccommodationBean> roomList =new ArrayList<AccommodationBean>();
		try{
			//Query to display all the booked(future) and non booked room 
			String fetchRoomByCapacity = "with CurrentReservation(roomid,checkindate,checkoutdate) as "
					+ "(select roomid,checkindate,checkoutdate from reservation where checkoutdate>now())"
					+ "  SELECT a.roomno,a.address,a.rent,a.capacity,a.pets,a.smoking,cr.checkindate,cr.checkoutdate "
					+ "FROM accommodation as a LEFT OUTER JOIN CurrentReservation as cr ON (a.roomid = cr.roomid) where a.capacity=?";
			connection=PGUtils.createConnection();
			preparedStatement=connection.prepareStatement(fetchRoomByCapacity);	
			preparedStatement.setInt(1, number);
			resultSet=preparedStatement.executeQuery();
			while (resultSet.next()){
				acbean = new AccommodationBean();
				acbean.setRoomno(resultSet.getInt(1));
				acbean.setAddress(resultSet.getString(2));
				acbean.setRent(resultSet.getDouble(3));
				acbean.setCapacity(resultSet.getInt(4));
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
