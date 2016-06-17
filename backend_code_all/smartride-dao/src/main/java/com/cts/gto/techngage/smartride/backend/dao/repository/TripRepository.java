package com.cts.gto.techngage.smartride.backend.dao.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cts.gto.techngage.smartride.backend.db.utils.core.DbConnectionUtils;

import com.cts.gto.techngage.smartride.backend.dao.key.UniqueKeyUtils;
import com.cts.gto.techngage.smartride.backend.dataobj.StringData;


public class TripRepository {
	private Connection con;
	private PreparedStatement preparedStatement;
	private ResultSet rs;
	
	private void init() {
		con = DbConnectionUtils.getConnection();
	}
	
	public TripRepository(){
		init();
	}

	
	public StringData allocateVehicles(StringData input) {
		StringData triplistStringData = new StringData();		
		ResultSet result = null;
		try {
			String route_id = input.getValue(0, "routeId");
			
			String trip_date=input.getValue(0, "tripDate");
			
			
//			keyUtils = UniqueKeyUtils.getInstance();
//			String key = keyUtils.generateNewKey("PASSENGER_PHONE", "DEVICE_ID");
//			
			//int phoneId = 0;
//			String trip_id = null;
//			String vehicle_id = null;
//			String next_stop = null;
			
			String sql = "select * from TRIP_EXEC where ROUTE_ID =? and to_char(trip_date, 'DD-MON-YYYY')=?";
						
			preparedStatement = con.prepareStatement(sql);
												
			preparedStatement.setString(1, route_id);
			preparedStatement.setString(2, trip_date);
			result = preparedStatement.executeQuery();
					
			while (result.next()) {
				//phoneId = result.getInt(1);
				String trip_id = result.getString(1);
				route_id = result.getString(2);
				String vehicle_id = result.getString(3);
				trip_date = result.getString(4);
				String next_stop = result.getString(5);
				String trip_status = result.getString(6);
				System.out.println(" trip_id:"+trip_id+"route_id:"+ route_id+"vehicle_id:"+ vehicle_id+" trip_date:"+trip_date+" trip_id:"+trip_id+" next_stop:"+next_stop+" trip_status:"+trip_status);
				//triplistStringData = new StringData("route-id="+route_id+"trip-date="+trip_date);
//				break;
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
		return triplistStringData;
	}
	
	
	
	public StringData registerTrip(StringData input) {
		StringData triplistStringData = new StringData();
		UniqueKeyUtils keyUtils = null;		
		
		try {
			String trip_idString = input.getValue(0, "tripId");
			int trip_id = Integer.parseInt(trip_idString);
			
			String route_idString = input.getValue(0, "routeId");
			int route_id = Integer.parseInt(route_idString);
			
			String vehicle_idString = input.getValue(0, "vehicleId");
			int vehicle_id = Integer.parseInt(vehicle_idString);
			
			String trip_date = input.getValue(0, "tripDate");
			
			String next_stopString = input.getValue(0, "nextStop");
			int next_stop = Integer.parseInt(next_stopString);
			
			String trip_status = input.getValue(0, "tripStatus");

			
				keyUtils = UniqueKeyUtils.getInstance();
				String key = keyUtils.generateNewKey("TRIP_EXEC"); // "TRIP_ID"
				

				String sql = "insert into TRIP_EXEC (TRIP_ID, ROUTE_ID, VEHICLE_ID,TRIP_DATE, NEXT_STOP, TRIP_STATUS) values (?, ?, ?, ?, ?, ?)";
							
				preparedStatement = con.prepareStatement(sql);									
				preparedStatement.setInt(1, trip_id);
				preparedStatement.setInt(2, route_id);
				preparedStatement.setInt(3, vehicle_id);
				preparedStatement.setString(4, trip_date);
				preparedStatement.setInt(5, next_stop );
				preparedStatement.setString(6, trip_status);				
				
				preparedStatement.executeUpdate();
	

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
		return triplistStringData;
	}
	
	
		
}
