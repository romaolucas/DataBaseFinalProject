package com.tum.dbpra.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.tum.dbpra.Config;

public abstract class DAO {
	protected Connection getConnection() throws SQLException,
			ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		
		return DriverManager.getConnection("jdbc:postgresql://"
				+ Config.Database.HOST + ":" + Config.Database.PORT + "/"
				+ Config.Database.DB, Config.Database.USER,
				Config.Database.PASS);
	}
	
	protected void closeConnection(Connection closingConnection){
		try {
			closingConnection.close();
		} catch (Exception ignored) {
			System.out.println(ignored.getMessage());
		}
		
	}

}
