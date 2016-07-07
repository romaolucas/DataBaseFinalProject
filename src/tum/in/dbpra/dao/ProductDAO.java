package tum.in.dbpra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tum.in.dbpra.bean.ProductBean;
import tum.in.dbpra.dbutils.PGUtils;

public class ProductDAO {

	public void addProduct(int pid, ProductBean product) {
		String query = "insert into product values (?, ?, ?, ?, ?, ?)";
		String getProductId = "select max(productid) from product";
		Connection con;
		PreparedStatement pstm;
		Statement stmt;
		ResultSet rs;
		try {
			con = PGUtils.createConnection();
			con.setAutoCommit(false);
			pstm = con.prepareStatement(query);
			stmt = con.createStatement();
			rs = stmt.executeQuery(getProductId);
			int productId = 1;
			if (rs.next())
				productId = Integer.parseInt(rs.getString("max")) + 1;
			pstm.setInt(1, productId);
			pstm.setInt(2, pid);
			pstm.setString(3, product.getName());
			pstm.setDouble(4, product.getPrice());
			pstm.setString(5, product.getCategory());
			pstm.setInt(6, product.getQuantity());
			pstm.executeUpdate();
			con.commit();
			rs.close();
			stmt.close();
			pstm.close();
			PGUtils.closeConnection(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<ProductBean> getProducts(int pid) {
		List<ProductBean> products = new ArrayList<ProductBean>();
		String query = "select name, price, category, quantity from product where pid = ?";
		Connection con;
		PreparedStatement pstm;
		ResultSet rs;
		try {
			con = PGUtils.createConnection();
			pstm = con.prepareStatement(query);
			pstm.setInt(1, pid);
			rs = pstm.executeQuery();
			while (rs.next()) {
				ProductBean product = new ProductBean();
				product.setName(rs.getString("name"));
				product.setPrice(rs.getDouble("price"));
				product.setCategory(rs.getString("category"));
				product.setQuantity(rs.getInt("quantity"));
				products.add(product);
			}
			rs.close();
			pstm.close();
			PGUtils.closeConnection(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;
	}
	
	public boolean updateProduct(String email, String name, int quantity) {
		String updateQuery = "update product set quantity = ? where pid = ? and name = ?";
		String getPid = "select pid from provider where email = ?";
		boolean worked = false;
		Connection con;
		PreparedStatement pstmUpdate;
		PreparedStatement pstmSelect;
		ResultSet rs;
		try {
			con = PGUtils.createConnection();
			pstmUpdate = con.prepareStatement(updateQuery);
			pstmSelect = con.prepareStatement(getPid);
			pstmSelect.setString(1, email);
			System.out.println(pstmSelect.toString());
			rs = pstmSelect.executeQuery();
			int pid;
			if (rs.next()) {
				pid = rs.getInt("pid");
				pstmUpdate.setInt(1, quantity);
				pstmUpdate.setInt(2, pid);
				pstmUpdate.setString(3, name);
				System.out.println(pstmUpdate.toString());
				int alteredRows = pstmUpdate.executeUpdate();
				if (alteredRows > 0) worked = true;
			}
			rs.close();
			pstmSelect.close();
			pstmUpdate.close();
			PGUtils.closeConnection(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return worked;
	}
}
