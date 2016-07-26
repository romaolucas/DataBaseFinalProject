
package tum.in.dbpra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import tum.in.dbpra.bean.ActivityLogBean;
import tum.in.dbpra.dbutils.PGUtils;

public class ActivityLogDAO {
	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	ActivityLogBean activityLogBean;
         //show activity 
	public List<ActivityLogBean> getActivityLog() {
		List<ActivityLogBean> visitorList = new ArrayList<ActivityLogBean>();		
		
		try {
			connection = PGUtils.createConnection();
			connection.setAutoCommit(false);
			String fetchActivityLog = "select * from enters";
			// Fetch supplier key from the supplier table using the supplier
			// name that user provides as input
			preparedStatement = connection.prepareStatement(fetchActivityLog);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				do {
					activityLogBean = new ActivityLogBean();
					activityLogBean.setRfid(resultSet.getInt(1));
					activityLogBean.setStageid(resultSet.getInt(2));
					activityLogBean.setEntertime(resultSet.getDate(3));
					activityLogBean.setExittime(resultSet.getDate(4));
					visitorList.add(activityLogBean);
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
