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

	public List<TicketCategoryBean> getTCDetails() {
		List<TicketCategoryBean> ticketList = new ArrayList<TicketCategoryBean>();
		try {
			connection = PGUtils.createConnection();
			connection.setAutoCommit(false);

			// Fetch supplier key from the supplier table using the supplier
			// name that user provides as input
			preparedStatement = connection
					.prepareStatement(PGUtils.fetchTicketCagory);

			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				do {
					// System.out.println("orders");
					tcBean = new TicketCategoryBean();
					tcBean.setId(resultSet.getInt(1));
					tcBean.setName(resultSet.getString(2));
					tcBean.setPrice(resultSet.getDouble(3));
					tcBean.setVolume(resultSet.getInt(4));
					tcBean.setStartTime(resultSet.getDate(5));
					tcBean.setEndTime(resultSet.getDate(6));
					tcBean.setDescription(resultSet.getString(7));
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