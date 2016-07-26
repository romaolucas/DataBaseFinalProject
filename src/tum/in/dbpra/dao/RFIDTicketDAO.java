package tum.in.dbpra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import tum.in.dbpra.bean.RFIDTicketBean;
import tum.in.dbpra.dbutils.PGUtils;

public class RFIDTicketDAO {
Connection connection;
PreparedStatement preparedStatement;
RFIDTicketBean rfidTicketBean;
ResultSet resultSet;

//get active RFID of visitor 
public List<RFIDTicketBean> getRFIDDetails(int visitorID){
	List<RFIDTicketBean> RFIDList =new ArrayList<RFIDTicketBean>();

	try{
		connection=PGUtils.createConnection();
	connection.setAutoCommit(false);
	//Query to fetch RFID details based on the logged in visitor and for active RFIDs
	String fetchRFIDDetails = "select w.status,w.balance,w.activationtime,w.rfid from wristband as w where w.rfid in (select rfid from ticket where visitorid =? ) and w.status=true";
	preparedStatement = connection.prepareStatement(fetchRFIDDetails);
	preparedStatement.setInt(1, visitorID);
	
				
	resultSet = preparedStatement.executeQuery();
	if (resultSet.next()) {
		do {
			rfidTicketBean = new RFIDTicketBean();
			rfidTicketBean.setStatus(resultSet.getBoolean(1));
			rfidTicketBean.setBalance(resultSet.getInt(2));
			rfidTicketBean.setActivationDate(resultSet.getDate(3));
			rfidTicketBean.setRfid(resultSet.getInt(4));
			RFIDList.add(rfidTicketBean);
		} while (resultSet.next());
		resultSet.close();
		preparedStatement.close();
	} else {
		RFIDList = null;
	}
	return RFIDList;
} catch (Exception ex) {
	System.out.println(ex.getMessage());
} finally {
	PGUtils.closeConnection(connection);
}
return RFIDList;

}
//updating comments by appending status in case of disabling request for RFID
public boolean updateDisableComment(int rfid,String comments){
   	
	int updatedRow=0;
	boolean status=false;
	try{
		connection=PGUtils.createConnection();
	connection.setAutoCommit(false);
	String updateRFIDComments = "update wristband set comments=comments||? where rfid=?";
	// Fetch supplier key from the supplier table using the supplier
	// name that user provides as input
	preparedStatement = connection.prepareStatement(updateRFIDComments);
	preparedStatement.setInt(2, rfid);
	preparedStatement.setString(1, comments);
	updatedRow=preparedStatement.executeUpdate();
	if(updatedRow>=1){
		status =true;
	}	
connection.commit();

}catch (Exception e) {
	// TODO: handle exception
	System.out.println(e.getMessage());
	status=false;
}finally {
	PGUtils.closeConnection(connection);
}

return status;	
	
}

}