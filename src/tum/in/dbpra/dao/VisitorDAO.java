
package tum.in.dbpra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import tum.in.dbpra.bean.VisitorBean;
import tum.in.dbpra.dbutils.PGUtils;

public class VisitorDAO {
	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	VisitorBean visitorBean;
       //fetch and auth visitor basedd on username and password
	public List<VisitorBean> getVisitors(String username, String password) {
		List<VisitorBean> visitorList = new ArrayList<VisitorBean>();
		
		
		try {
			connection = PGUtils.createConnection();
			connection.setAutoCommit(false);
			String fetchVisitor = "select * from visitor where username = ? and password=?";
			// Fetch supplier key from the supplier table using the supplier
			// name that user provides as input
			preparedStatement = connection.prepareStatement(fetchVisitor);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				do {
					//System.out.println("orders");
					visitorBean = new VisitorBean();
					visitorBean.setVisitorId(resultSet.getInt(1));
					visitorBean.setFirstName(resultSet.getString(2));
					visitorBean.setLastName(resultSet.getString(3));
					visitorBean.setUsername(resultSet.getString(4));
					visitorBean.setPassword(resultSet.getString(5));
					visitorBean.setAddress(resultSet.getString(6));
					visitorBean.setPhoneNumber(resultSet.getString(7));
					visitorBean.setEmail(resultSet.getString(8));
					visitorList.add(visitorBean);
				} while (resultSet.next());
				resultSet.close();
				preparedStatement.close();
			} else {
				visitorList = null;
			}
			return visitorList;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			PGUtils.closeConnection(connection);
		}
		return visitorList;
	}
	
}
