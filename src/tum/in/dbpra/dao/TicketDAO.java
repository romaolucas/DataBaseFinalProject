package tum.in.dbpra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import tum.in.dbpra.bean.RFIDTicketBean;
import tum.in.dbpra.bean.TicketBean;
import tum.in.dbpra.dbutils.PGUtils;

public class TicketDAO {
	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	TicketBean ticketBean;
	Boolean status=false;
	
	public List<TicketBean> getTicketbyVisitor(int visitorID){
		List<TicketBean> ticketList =new ArrayList<TicketBean>();
		try {
			connection = PGUtils.createConnection();
			connection.setAutoCommit(false);
			String fetchTicketByVisitor = "select t.ticketid,tc.name,t.purchasedate,tc.price,t.activationTime from ticketcategory as tc,ticket as t where t.visitorid=? and tc.categoryid=t.categoryid";
			// Fetch supplier key from the supplier table using the supplier
			// name that user provides as input
			preparedStatement = connection.prepareStatement(fetchTicketByVisitor);
			preparedStatement.setInt(1, visitorID);
						
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				do {
					//System.out.println("orders");
					ticketBean = new TicketBean();
					ticketBean.setTickeID(resultSet.getInt(1));
					ticketBean.setName(resultSet.getString(2));
					ticketBean.setPurchaseDate(resultSet.getDate(3));
					ticketBean.setPrice(resultSet.getDouble(4));
					ticketBean.setActivationDate(resultSet.getDate(5));
					ticketList.add(ticketBean);
				} while (resultSet.next());
				resultSet.close();
				preparedStatement.close();
			} else {
				ticketList = null;
			}
			return ticketList;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			PGUtils.closeConnection(connection);
		}
		return ticketList;
		
	}
	
	public boolean buyTickets(int visitorID,int quantity,int categoryID){
		
	 int updatedRow=0;
	 
		try {
			connection = PGUtils.createConnection();
			connection.setAutoCommit(false);
			
			String insertIntoTicket = "insert into ticket(ticketid,visitorid,rfid,categoryid,purchasedate) values ((select max(ticketid) from ticket)+1,?,?,?,now())";
			RFIDTicketDAO RFIDDAO=new RFIDTicketDAO();
			List<RFIDTicketBean> rfList = RFIDDAO.getRFIDDetails(visitorID);
			int rfid=rfList.get(0).getRfid();
			// Fetch supplier key from the supplier table using the supplier
			// name that user provides as input
			preparedStatement = connection.prepareStatement(insertIntoTicket);
			preparedStatement.setInt(1, visitorID);
			preparedStatement.setInt(2, rfid);
			preparedStatement.setInt(3, categoryID);
			
			for(int i=0;i<quantity;i++){
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
				System.out.println("Ticket buy failed"+e.getMessage());
				status=false;
												
			}finally {
				PGUtils.closeConnection(connection);
			}
		System.out.println("The value of status:"+status);
		return status;	
	}
}
