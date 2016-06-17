package com.cts.gto.techngage.smartride.backend.db.utils.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;


public class DataSourceFactory {
	
	private static DataSource dataSource;

	static DataSource getDataSource() {
		
		if(dataSource == null) {
			Properties props = new Properties();	        
	        BasicDataSource ds = new BasicDataSource();	  
	        InputStream is = null;
	        try {
	        	is = DataSourceFactory.class.getResourceAsStream("/db.properties");
	            props.load(is);
	        }catch(IOException e){
	            e.printStackTrace();
	            return null;
	        } finally {
	        	try {
					is.close();
				} catch (IOException e) {}
	        }
	        ds.setDriverClassName(props.getProperty("DB_DRIVER_CLASS"));
	        ds.setUrl(props.getProperty("DB_URL"));
	        ds.setUsername(props.getProperty("DB_USERNAME"));
	        ds.setPassword(props.getProperty("DB_PASSWORD"));
	        
	        dataSource = ds;
		}		        
        return dataSource;
	}

	
}
