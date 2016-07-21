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

public List<RFIDTicketBean> getRFIDDetails(int visitorID){
	List<RFIDTicketBean> RFIDList =new ArrayList<RFIDTicketBean>();

	try{
		connection=PGUtils.createConnection();
	connection.setAutoCommit(false);

	// Fetch supplier key from the supplier table using the supplier
	// name that user provides as input
	preparedStatement = connection.prepareStatement(PGUtils.fetchRFIDDetails);
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

public boolean updateDisableComment(int rfid,String comments){
   	
	int updatedRow=0;
	boolean status=false;
	try{
		connection=PGUtils.createConnection();
	connection.setAutoCommit(false);

	// Fetch supplier key from the supplier table using the supplier
	// name that user provides as input
	preparedStatement = connection.prepareStatement(PGUtils.updateRFIDComments);
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
