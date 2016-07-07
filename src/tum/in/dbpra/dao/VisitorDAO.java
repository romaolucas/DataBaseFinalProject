package tum.in.dbpra.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import tum.in.dbpra.bean.VisitorBean;
import tum.in.dbpra.dbutils.PGUtils;

import jdk.nashorn.internal.ir.RuntimeNode.Request;

public class VisitorDAO {
	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	VisitorBean visitorBean;

	public List<VisitorBean> getVisitors(String username, String password) {
		List<VisitorBean> visitorList = new ArrayList<VisitorBean>();
		
		
		try {
			connection = PGUtils.createConnection();
			connection.setAutoCommit(false);

			// Fetch supplier key from the supplier table using the supplier
			// name that user provides as input
			preparedStatement = connection.prepareStatement(PGUtils.fetchVisitorCredentials);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				do {
					//System.out.println("orders");
					visitorBean = new VisitorBean();
					visitorBean.setVisitorId(resultSet.getInt(1));
					visitorBean.setPassword(resultSet.getString(2));
					visitorBean.setEmail(resultSet.getString(3));
					visitorBean.setAddress(resultSet.getString(4));
					visitorBean.setPhoneNumber(resultSet.getString(5));
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