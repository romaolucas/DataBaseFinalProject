package tum.in.dbpra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import tum.in.dbpra.bean.RFIDTicketBean;
import tum.in.dbpra.bean.TimetableBean;
import tum.in.dbpra.bean.BandSelectionBean;
import tum.in.dbpra.dbutils.PGUtils;

public class TimetableDAO {
	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	TimetableBean ttBean;
	BandSelectionBean bsBean;
	Boolean status=false;
	
	public List<TimetableBean> getTimetableDetails(){
		List<TimetableBean> timetableList =new ArrayList<TimetableBean>();
		try {
			connection = PGUtils.createConnection();
			connection.setAutoCommit(false);
			String fetchTimeslot ="select b.pid, p.name, b.style, b.country, b.charge, s.sectionid, t.timebuildup, t.timeplay, t.timefinish, t.timegone from provider p, band b, stage s, timeslot t where b.pid=t.pid and s.sectionid=t.sectionid and p.pid=t.pid";
			// Fetch supplier key from the supplier table using the supplier
			// name that user provides as input
			preparedStatement = connection.prepareStatement(fetchTimeslot);
						
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				do {
					ttBean = new TimetableBean();
					ttBean.setId(resultSet.getInt(1));
					ttBean.setName(resultSet.getString(2));
					ttBean.setStyle(resultSet.getString(3));
					ttBean.setCountry(resultSet.getString(4));
					ttBean.setCharge(resultSet.getDouble(5));
					ttBean.setSectionid(resultSet.getInt(6));
					ttBean.setTimebuildup(resultSet.getDate(7));
					ttBean.setTimeplay(resultSet.getDate(8));
					ttBean.setTimefinish(resultSet.getDate(9));
					ttBean.setTimegone(resultSet.getDate(10));
					timetableList.add(ttBean);
				} while (resultSet.next());
				resultSet.close();
				preparedStatement.close();
			} else {
				timetableList = null;
			}
			return timetableList;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			PGUtils.closeConnection(connection);
		}
		return timetableList;		
	}
	
	public boolean custTimetable(int visitorID, List<TimetableBean> ttList, String[] ID,int noOfSelection){
		
		 int updatedRow=0;
		 
			try {
				connection = PGUtils.createConnection();
				connection.setAutoCommit(false);
				String insertIntoBandselection = "insert into BandSelection (VisitorID, BandID, Name, Style, Country, timeplay, timefinish) values (?,?,?,?,?,?,?)";
				preparedStatement = connection.prepareStatement(insertIntoBandselection);
				for(int i=0; i<ID.length; i++){
					preparedStatement.setInt(1, visitorID);
					preparedStatement.setInt(2, ttList.get(Integer.parseInt(ID[i])).getId());
					preparedStatement.setString(3, ttList.get(Integer.parseInt(ID[i])).getName());
					preparedStatement.setString(4, ttList.get(Integer.parseInt(ID[i])).getStyle());
					preparedStatement.setString(5, ttList.get(Integer.parseInt(ID[i])).getCountry());
					preparedStatement.setDate(6, (java.sql.Date) ttList.get(Integer.parseInt(ID[i])).getTimeplay());
					preparedStatement.setDate(7, (java.sql.Date) ttList.get(Integer.parseInt(ID[i])).getTimefinish());
					updatedRow=preparedStatement.executeUpdate();
					if(updatedRow<1){
						connection.rollback();
						break;
					}else{
						status=true;
						}
				}
				
//				for(int i=0;i<noOfSelection;i++){
//					
//					}
				connection.commit();
				
				}catch (Exception e) {
					// TODO: handle exception
					System.out.println("Customised timetable creation failed"+e.getMessage());
					status=false;
													
				}finally {
					PGUtils.closeConnection(connection);
				}
			System.out.println("The value of status:"+status);
			return status;	
		}
	
	public boolean delRowTimetable(int visitorID, List<BandSelectionBean> bsList, String[] ID,int noOfSelection){
		
		 int updatedRow=0;
		 
			try {
				connection = PGUtils.createConnection();
				connection.setAutoCommit(false);
				String deleteFromBandselection = "delete from bandselection where visitorid = ? and bandid = ?";
				preparedStatement = connection.prepareStatement(deleteFromBandselection);
				for(int i=0; i<ID.length; i++){
					preparedStatement.setInt(1, visitorID);
					preparedStatement.setInt(2, bsList.get(Integer.parseInt(ID[i])).getBandID());
					updatedRow=preparedStatement.executeUpdate();
					if(updatedRow<1){
						connection.rollback();
						break;
					}else{
						status=true;
						}
				}
				
				connection.commit();
				
				}catch (Exception e) {
					// TODO: handle exception
					System.out.println("Customised timetable creation failed"+e.getMessage());
					status=false;
													
				}finally {
					PGUtils.closeConnection(connection);
				}
			System.out.println("The value of status:"+status);
			return status;	
		}
	
	
	public List<BandSelectionBean> getBandSelection(){
		List<BandSelectionBean> bandselectionList =new ArrayList<BandSelectionBean>();
		try {
			connection = PGUtils.createConnection();
			connection.setAutoCommit(false);
			String fetchBandselection = "select VisitorID, BandID, Name, Style, Country, timeplay, timefinish from Bandselection";
			// Fetch supplier key from the supplier table using the supplier
			// name that user provides as input
			preparedStatement = connection.prepareStatement(fetchBandselection);
						
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				do {
					bsBean = new BandSelectionBean();
					bsBean.setVisitorID(resultSet.getInt(1));
					bsBean.setBandID(resultSet.getInt(2));
					bsBean.setName(resultSet.getString(3));
					bsBean.setStyle(resultSet.getString(4));
					bsBean.setCountry(resultSet.getString(5));
					bsBean.setTimeplay(resultSet.getDate(6));
					bsBean.setTimefinish(resultSet.getDate(7));
					bandselectionList.add(bsBean);
				} while (resultSet.next());
				resultSet.close();
				preparedStatement.close();
			} else {
				bandselectionList = null;
			}
			return bandselectionList;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			PGUtils.closeConnection(connection);
		}
		return bandselectionList;		
	}
	
}
