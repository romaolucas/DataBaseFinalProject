package tum.in.dbpra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import tum.in.dbpra.bean.TicketBean;
import tum.in.dbpra.dbutils.PGUtils;

public class TicketDAO {
	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	TicketBean ticketBean;

	public List<TicketBean> getTicketbyVisitor(int visitorID) {
		List<TicketBean> ticketList = new ArrayList<TicketBean>();
		try {
			connection = PGUtils.createConnection();
			connection.setAutoCommit(false);

			// Fetch supplier key from the supplier table using the supplier
			// name that user provides as input
			preparedStatement = connection
					.prepareStatement(PGUtils.fetchTicketByVisitor);
			preparedStatement.setInt(1, visitorID);

			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				do {
					// System.out.println("orders");
					ticketBean = new TicketBean();
					ticketBean.setTickeID(resultSet.getInt(1));
					ticketBean.setName(resultSet.getString(2));
					ticketBean.setPrice(resultSet.getDouble(4));
					ticketBean.setPurchaseDate(resultSet.getDate(3));
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
}