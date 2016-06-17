package com.cts.gto.techngage.smartride.backend.dao.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.cts.gto.techngage.smartride.backend.db.utils.core.DbConnectionUtils;
import com.cts.gto.techngage.smartride.backend.dao.vo.Route;
import com.cts.gto.techngage.smartride.backend.dataobj.StringData;


public class RouteRepository {
	private Connection con = null;
	private Statement stmt = null;
	private PreparedStatement preparedStatement, preparedStatement2;
	private ResultSet rs = null;
	
	private void init() {
		con = DbConnectionUtils.getConnection();
	}
	
	public RouteRepository(){
		init();
	}

	public static void main(String[] args) {
		
		RouteRepository routeRepo = new RouteRepository();
		try {
			String testData = "pick=10|dest=3|date=27-MAY-2016|pickuptime=09h35m";
		
			StringData routeStringData = routeRepo.retrieveRoute(new StringData(testData));	
					
			System.out.println("routeStringData Value :\n" + routeStringData.toString());
		
			System.out.println("RouteId :" + routeStringData.getValue(0, "route_nm") );
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}

	
	public StringData retrieveRoute(StringData input) {
		StringData routelistStringData = new StringData();
		int pickupStopId=0, destStopId=0;
		double timePickupHr = 0.0;
		ResultSet result1 = null; 
		try {
			String pickupStop = input.getValue(0, "pick");
			String destStop = input.getValue(0, "dest");
			String date = input.getValue(0, "date");	
			date = getDateDDMONYYYY(date);
			
			String pickupTime = input.getValue(0, "pickuptime");
			
			String offSetStr = input.getValue(0, "time-offset-hr");
			
			double offSet = offSetStr == null ? -1.0: Double.parseDouble(offSetStr);
						
			/**
			 * -- Test Query ---------------------------------
			select distinct start_seq_no.route_id
			,start_seq_no.stop_id StartStop
			,end_seq_no.stop_id EndStop
			,start_seq_no.ESTM_ARV_TM
			from 
			(select route_id, STOP_SEQ_NO, stop_id, ESTM_ARV_TM from route_stop_map where stop_id=10) start_seq_no,
			(select route_id, STOP_SEQ_NO, stop_id from route_stop_map where stop_id=3) end_seq_no
			where 
			start_seq_no.STOP_SEQ_NO < end_seq_no.STOP_SEQ_NO
			
			and (start_seq_no.ESTM_ARV_TM between 9.76 and 11)

			 */
			String sql= 
				"select distinct start_seq_no.route_id, route.ROUTE_NM ,start_seq_no.stop_id StartStop " +
				", end_seq_no.stop_id EndStop ,start_seq_no.ESTM_ARV_TM " +
				" from " +
				" (select route_id, STOP_SEQ_NO, stop_id, ESTM_ARV_TM from route_stop_map where stop_id= ? ) start_seq_no" +
				", (select route_id, STOP_SEQ_NO, stop_id from route_stop_map where stop_id= ?) end_seq_no " +
				", route_sch route"+
				" where " +
				" start_seq_no.STOP_SEQ_NO < end_seq_no.STOP_SEQ_NO "+
				" and start_seq_no.route_id = route.route_id ";
				
			
			String queryPartBetween = " and (start_seq_no.ESTM_ARV_TM between ? and ?)" ;
			
			sql = offSet != -1.0 ? sql + queryPartBetween : sql;
			
			System.out.println(" Query :\n"+sql);
			preparedStatement = con.prepareStatement(sql);			
			
									
			pickupStopId= Integer.parseInt(pickupStop);
			destStopId= Integer.parseInt(destStop);
			timePickupHr = getTimeInHour(pickupTime);
									
			preparedStatement.setInt(1, pickupStopId);
			preparedStatement.setInt(2, destStopId);
            
            if(offSet != -1.0) {
            	sql = sql + queryPartBetween;
            	
            	preparedStatement.setDouble(3, timePickupHr-offSet);
            	preparedStatement.setDouble(4, timePickupHr+offSet);
            }            
			result1 = preparedStatement.executeQuery();			
			StringBuilder sbuild = new StringBuilder();
			
			int i = 0;
			int routeId =0;
			StringBuilder sbRouteIdIn = new StringBuilder();
			List<Integer> routeIdList = new ArrayList<Integer>(5);
			
			while (result1.next()) {
				routeId = result1.getInt(1);
				sbuild.append("route_id=").append(routeId).append('|');
				sbuild.append("route_nm=").append(result1.getString(2)).append('|');
				
				sbuild.append("pick=").append(pickupStop).append('|');
				sbuild.append("dest=").append(destStop).append('|');
				
				sbuild.append("pickup_date=").append(date).append('|');
				sbuild.append("estm_arv_tm=").append(result1.getDouble(5))
				.append('^');
				
				sbRouteIdIn.append(routeId).append(',');
				routeIdList.add(routeId);
				
				i++;
			}	
			try {
				sbRouteIdIn.deleteCharAt(sbRouteIdIn.length()-1);
				sbuild.deleteCharAt(sbuild.length()-1);
				
			} catch (ArrayIndexOutOfBoundsException e) {
				// TODO: handle exception
			}
			
			
			routelistStringData = new StringData(sbuild.toString());
			
			StringData countData = new StringData("route_id_comma_sep="
			+sbRouteIdIn+"|pickup_date="+date);
			
			countData = getBookingCountsByRouteIdsAndDate(countData);
			
			for(Integer routeIdInt : routeIdList) {
				String confCount = countData.getValueForKey("route_id", ""+routeIdInt,  "confirm_count");
				String tentCount = countData.getValueForKey("route_id", ""+routeIdInt,  "tent_count");
				
				routelistStringData.putValueForKey("route_id", ""+routeIdInt, "confirm_count", confCount);
				routelistStringData.putValueForKey("route_id", ""+routeIdInt, "tent_count", tentCount);
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {			
			e.printStackTrace();
		} finally {
			try {
				if (result1 != null)
					result1.close();
				if (preparedStatement != null)
					preparedStatement.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return routelistStringData;
	}
	
	
	
	public StringData getBookingCountsByRouteIdsAndDate(StringData input) {
		StringData routelistStringData = null;		
		ResultSet result = null, result2 = null;
		try {
			String routeIdCommaSep = input.getValue(0, "route_id_comma_sep");
			String pickDate = input.getValue(0, "pickup_date");
		
			int tentCount = 0, confirmCount=0;
			//String tentCountStr = null, confirmCountStr=null;
			//String bookType = null;
			String bookConf = "'C'", bookTent = "'T'";
			String grpBy = " group by route_id ";
			
			String sqlPrefix = "select route_id, count(booking_type) from BOOKING " +
					"where ROUTE_ID in ("+routeIdCommaSep+") and to_char(PICKUP_DT_TM, 'DD-MON-YYYY') = ? " +
					"and booking_type = ";
			
			String query = sqlPrefix + bookConf + grpBy;
			System.out.println(" Count Query 1:\n"+query);
						
			preparedStatement = con.prepareStatement(query);
												
			//preparedStatement.setString(1, routeIdCommaSep);
			preparedStatement.setString(1, pickDate);
			
			result = preparedStatement.executeQuery();
			int routeId = 0;
			StringBuilder sbBuild = new StringBuilder();
						
			while (result.next()) {
				confirmCount = result.getInt(2);
				routeId = result.getInt(1);	
				
				sbBuild.append("route_id=").append(routeId).append('|')
				.append("confirm_count=").append(confirmCount).append('^');
			}	
			try {
				sbBuild.deleteCharAt(sbBuild.length()-1);
				
			} catch (StringIndexOutOfBoundsException e) {
				// TODO: handle exception
			}
			
			
			routelistStringData = new StringData(sbBuild.toString());
			
			query = sqlPrefix + bookTent + grpBy;
			System.out.println(" Count Query 2:\n"+query);
			
			preparedStatement2 = con.prepareStatement(query);
			
			//preparedStatement2.setString(1, routeIdCommaSep);
			preparedStatement2.setString(1, pickDate);
			
			result2 = preparedStatement2.executeQuery();
						
			while (result2.next()) {
				routeId = result2.getInt(1);	
				tentCount = result2.getInt(2);
				
				routelistStringData.putValueForKey("route_id", ""+routeId, "tent_count", ""+tentCount);
			}			
			System.out.println("RouteRepository.getBookingCountsByRouteIdsAndDate() completed---");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {			
			e.printStackTrace();
		} finally {
			try {
				if (result != null)
					result.close();	
				if (result2 != null)
					result2.close();	
				if (preparedStatement != null)
					preparedStatement.close();
				if (preparedStatement2 != null)
					preparedStatement2.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return routelistStringData;
	}
	
	
	
	public static double getTimeInHour(String nnhnnm) throws NumberFormatException {
		double hr24 = 0.0;
		
		String hrStr = nnhnnm.substring(0, nnhnnm.indexOf('h'));
		String mmStr = nnhnnm.substring(nnhnnm.indexOf('h')+1, nnhnnm.indexOf('m'));
		
		double hr = (double)Integer.parseInt(hrStr);
		double mmHr = (double)Integer.parseInt(mmStr) / (double)60.0;
		
		hr24 = hr + mmHr;
		
		return hr24;
	}
	
	public static String get3LetterMonth(int index) {
		String[] month3letter = new String[]
				{ "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };
		
		return month3letter[index];
	}
	
	public static String getDateDDMONYYYY(String DDMYYYY) throws NumberFormatException {				
				
		String DD = DDMYYYY.substring(0, DDMYYYY.indexOf('-'));		
		String MM = DDMYYYY.substring(DDMYYYY.indexOf('-')+1, DDMYYYY.lastIndexOf('-'));		
		int monthIndex = -1;
		
		monthIndex = Integer.parseInt(MM);		
		String MON = get3LetterMonth(monthIndex);		
		String YYYY = DDMYYYY.substring(DDMYYYY.lastIndexOf('-')+1);
						
		return DD+'-'+MON+'-'+YYYY;
	}
	
}
