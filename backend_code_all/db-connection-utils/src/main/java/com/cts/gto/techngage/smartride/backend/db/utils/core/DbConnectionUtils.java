package com.cts.gto.techngage.smartride.backend.db.utils.core;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class DbConnectionUtils {
	
	private static DataSource dataSource;
	
	public static Connection getConnection() {
		Connection conn = null;
				
		try {
			dataSource = DataSourceFactory.getDataSource();
			conn = dataSource.getConnection();
		} catch (SQLException e) {			
			e.printStackTrace();
		} catch (Exception e) {			
			e.printStackTrace();
		}		
		return conn;		
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}

}
