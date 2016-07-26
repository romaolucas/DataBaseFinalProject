package tum.in.dbpra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import tum.in.dbpra.bean.TicketCategoryBean;
import tum.in.dbpra.dbutils.PGUtils;

public class TicketCategoryDAO {
	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	TicketCategoryBean tcBean;
	//get all different ticket order by their price
	public List<TicketCategoryBean> getTCDetails(){
		List<TicketCategoryBean> ticketList =new ArrayList<TicketCategoryBean>();
		try {
			connection = PGUtils.createConnection();
			connection.setAutoCommit(false);
			//Query to fetch all tickets order by their price
			String fetchTicketCategory = "select * from ticketcategory order by price";
			preparedStatement = connection.prepareStatement(fetchTicketCategory);
						
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				do {
					tcBean = new TicketCategoryBean();
					tcBean.setId(resultSet.getInt(1));
					tcBean.setName(resultSet.getString(2));
					tcBean.setPrice(resultSet.getDouble(3));
					tcBean.setVolume(resultSet.getInt(4));
					tcBean.setStartTime(resultSet.getDate(5));
					tcBean.setEndTime(resultSet.getDate(6));
					tcBean.setDescription(resultSet.getString(7));
					tcBean.setDuration(resultSet.getInt(8));
					ticketList.add(tcBean);
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
}
