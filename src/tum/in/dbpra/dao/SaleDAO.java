package tum.in.dbpra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import tum.in.dbpra.bean.SaleBean;
import tum.in.dbpra.dbutils.PGUtils;

public class SaleDAO {
	public List<SaleBean> getSalesForProvider(int pid) {
		String query = "select p.name as pname, s.name as sname, soldquantity, soldquantity*price as income from sale, section as s, product as p "          
				+ "where p.pid = ? and sale.sectionid = s.sectionid and p.productid = sale.productid";
		List<SaleBean> sales = new ArrayList<SaleBean>();
		PreparedStatement pstm;
		ResultSet rs;
		try {
			Connection con = PGUtils.createConnection();
			con.setAutoCommit(false);
			pstm = con.prepareStatement(query);
			pstm.setInt(1, pid);
			System.out.println(pstm.toString());
			rs = pstm.executeQuery();
			while (rs.next()) {
				SaleBean sale = new SaleBean();
				sale.setProductName(rs.getString("pname"));
				sale.setSection(rs.getString("sname"));
				sale.setSoldquantity(rs.getInt("soldquantity"));
				sale.setIncome(rs.getDouble("income"));
				sales.add(sale);
			}
			con.commit();
			rs.close();
			pstm.close();
			PGUtils.closeConnection(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sales;
	}
}
