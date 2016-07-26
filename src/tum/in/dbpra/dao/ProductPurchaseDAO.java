package tum.in.dbpra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import tum.in.dbpra.bean.ProductPurchaseBean;
import tum.in.dbpra.bean.RFIDTicketBean;
import tum.in.dbpra.dbutils.PGUtils;

public class ProductPurchaseDAO {
	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	ProductPurchaseBean ppBean;
	
	public List<ProductPurchaseBean> getPurchaseList(int visitorID) {
		List<ProductPurchaseBean> purchaseList = new ArrayList<ProductPurchaseBean>();
	try {
		connection = PGUtils.createConnection();
		connection.setAutoCommit(false);

		String fetchPurchase = "select p.name,p.category,pr.totalprice,pr.paymentdate,pr.quantity from product as p,purchase as pr where rfid=? and p.productid=pr.productid";
		RFIDTicketDAO RFIDDAO=new RFIDTicketDAO();
		List<RFIDTicketBean> rfList = RFIDDAO.getRFIDDetails(visitorID);
		int rfid=rfList.get(0).getRfid();
		// Fetch supplier key from the supplier table using the supplier
		// name that user provides as input
		preparedStatement = connection.prepareStatement(fetchPurchase);
		preparedStatement.setInt(1, rfid);
		
		resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			do {
				//System.out.println("orders");
				ppBean = new ProductPurchaseBean();
				ppBean.setProductName(resultSet.getString(1));
				ppBean.setProductCategory(resultSet.getString(2));
				ppBean.setTotalPrice(resultSet.getDouble(3));
				ppBean.setPaymentDate(resultSet.getDate(4));
				ppBean.setQuantity(resultSet.getInt(5));
				purchaseList.add(ppBean);
			} while (resultSet.next());
			resultSet.close();
			preparedStatement.close();
		} else {
			purchaseList = null;
		}
		return purchaseList;
	} catch (Exception ex) {
		System.out.println(ex.getMessage());
	} finally {
		PGUtils.closeConnection(connection);
	}
	return purchaseList;
}
	
}
