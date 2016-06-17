package com.cts.gto.techngage.smartride.backend.dao.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cts.gto.techngage.smartride.backend.db.utils.core.DbConnectionUtils;
import com.cts.gto.techngage.smartride.backend.dao.key.UniqueKeyUtils;
import com.cts.gto.techngage.smartride.backend.dataobj.StringData;



public class RegistrationRepository {
	private Connection con;
	private PreparedStatement preparedStatement;
	private ResultSet rs;
	
	private void init() {
		con = DbConnectionUtils.getConnection();
	}
	
	public RegistrationRepository(){
		init();
	}

	
	public StringData getRegistrationDataByUuid(StringData input) {
		StringData routelistStringData = null;		
		ResultSet result = null;
		try {
			String deviceUuid = input.getValue(0, "DEVICE_UUID");
			
//			keyUtils = UniqueKeyUtils.getInstance();
//			String key = keyUtils.generateNewKey("PASSENGER_PHONE", "DEVICE_ID");
//			
			int phoneId = 0;
			String deviceChannel = null;
			
			String sql = "select DEVICE_ID, DEVICE_CHANNEL from PASSENGER_PHONE where DEVICE_UUID =?";
						
			preparedStatement = con.prepareStatement(sql);
												
			preparedStatement.setString(1, deviceUuid);			
			result = preparedStatement.executeQuery();
						
			while (result.next()) {
				phoneId = result.getInt(1);
				deviceChannel = result.getString(2);
				
				routelistStringData = new StringData("phone-id="+phoneId+"|ph-channel="+deviceChannel);
				break;
			}            	
			

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {			
			e.printStackTrace();
		} finally {
			try {
				if (result != null)
					result.close();	
				if (preparedStatement != null)
					preparedStatement.close();				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return routelistStringData;
	}
	
	
		
	public StringData registerSmartphone(StringData input) {
		StringData routelistStringData = new StringData();
		UniqueKeyUtils keyUtils = null;
		String deviceChannel = null;
		try {
			String deviceUuid = input.getValue(0, "DEVICE_UUID");
			
			StringData existingData = getRegistrationDataByUuid(input);
			
			
			if(existingData == null) {
			
				keyUtils = UniqueKeyUtils.getInstance();
				String key = keyUtils.generateNewKey("PASSENGER_PHONE"); //"DEVICE_ID" unique
				
				int keyId = Integer.parseInt(key);
				deviceChannel = "ph-ch_"+keyId;
				
				String sql = "insert into PASSENGER_PHONE (DEVICE_ID, DEVICE_UUID, DEVICE_CHANNEL) values (?, ?, ?)";
							
				preparedStatement = con.prepareStatement(sql);
													
				preparedStatement.setInt(1, keyId);
				preparedStatement.setString(2, deviceUuid);
				preparedStatement.setString(3, deviceChannel); 
				
				preparedStatement.executeUpdate();
				routelistStringData = new StringData("phone-id="+keyId+"|ph-channel="+deviceChannel);
				
			} else {
				routelistStringData = existingData;
			}		

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {			
			e.printStackTrace();
		} finally {
			try {				
				if (preparedStatement != null)
					preparedStatement.close();				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return routelistStringData;
	}
	
		
}
