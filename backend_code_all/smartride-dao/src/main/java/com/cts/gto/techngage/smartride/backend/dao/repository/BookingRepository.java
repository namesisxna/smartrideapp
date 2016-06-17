package com.cts.gto.techngage.smartride.backend.dao.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;





import com.cts.gto.techngage.smartride.backend.db.utils.core.DbConnectionUtils;
import com.cts.gto.techngage.smartride.backend.dao.key.UniqueKeyUtils;
import com.cts.gto.techngage.smartride.backend.dataobj.StringData;

public class BookingRepository {
	private Connection con = null;
	
	private PreparedStatement preparedStatement, preparedStatement2;
	private ResultSet rs = null;

	private void init() {
		con = DbConnectionUtils.getConnection();
	}

	public BookingRepository() {
		init();
	}

	public static void main(String[] args) {

		BookingRepository repo = new BookingRepository();
		try {
			String testData = "ROUTE_ID=4|BOOKING_TYPE=C|ONBOARD_STOP=2" +
					"|OFFBOARD_STOP=1|PICKUP_DT_TM=27-MAY-2016" +
					"|BOOKING_STATUS=A|DEVICE_ID=2";
			
			String testData1 = "BOOK_NUM=BOOK_2|DEVICE_ID=3";
			String testdata4 ="deviceId=2";
			
			
			String testData2 = "ROUTE_ID=4|ONBOARD_STOP=1|OFFBOARD_STOP=6";
			StringData calculateCost =  new StringData(testdata4);
			StringData calculateCostResult = repo.getExistingBookingData(calculateCost);
			
//			StringData confirmBookingData =  new StringData(testData1);
//			StringData ConfirmStringData = repo.confirmBooking(confirmBookingData);

//			StringData routeStringData = repo.performBooking(new StringData(testData));
//			System.out.println("performBooking Value :\n"+ routeStringData.toString());
//			System.out.println("Booking Number ::"+ routeStringData.getValue(0, "BOOKING_NUM"));
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public StringData performBooking(StringData stringData) {
		
		System.out.println("BookingRepository performBooking called....... ");
		
		UniqueKeyUtils keyUtils = null;		
		try {
			String passngrId = stringData.getValue(0, "PSNGR_ID");
			String routeId = stringData.getValue(0, "ROUTE_ID");
			String bookType = stringData.getValue(0, "BOOKING_TYPE");
			String picupStop = stringData.getValue(0, "ONBOARD_STOP");
			String dropStop = stringData.getValue(0, "OFFBOARD_STOP");
			String tripDate = stringData.getValue(0, "PICKUP_DT_TM");
			String fareBlocked = stringData.getValue(0, "FARE_BLOCKED");
			String fareCharged = stringData.getValue(0, "FARE_CHARGED");
			String fareRefund = stringData.getValue(0, "FARE_REFUND");
			String bookStatus = stringData.getValue(0, "BOOKING_STATUS");
			
			String deviceId = stringData.getValue(0, "DEVICE_ID");

			keyUtils = UniqueKeyUtils.getInstance();
			String key = keyUtils.generateNewKey("BOOKING"); //Unique key : "BOOKING_ID"

			int keyId = Integer.parseInt(key);
			System.out.println("BookingRepository performBooking key :"+key);
			String bookingNumber = "BOOK_" + keyId;

			String sql = "insert into BOOKING ("
					+ "BOOKING_ID, BOOKING_NUM, PSNGR_ID, ROUTE_ID, "
					+ "BOOKING_TYPE, ONBOARD_STOP, OFFBOARD_STOP, "
					+ "PICKUP_DT_TM, FARE_BLOCKED, FARE_CHARGED, "
					+ "FARE_REFUND, BOOKING_STATUS, DEVICE_ID"
					+ ") values (?, ?, ?,?,?,?,?,?,?,?,?,?,?)";

			preparedStatement = con.prepareStatement(sql);

			preparedStatement.setInt(1, keyId);
			preparedStatement.setString(2, bookingNumber);
			
			preparedStatement.setString(3, passngrId);
			preparedStatement.setString(4, routeId);
			preparedStatement.setString(5, bookType);
			preparedStatement.setString(6, picupStop);
			preparedStatement.setString(7, dropStop);
			preparedStatement.setString(8, tripDate);
			preparedStatement.setString(9, fareBlocked);
			preparedStatement.setString(10, fareCharged);
			preparedStatement.setString(11, fareRefund);
			preparedStatement.setString(12, bookStatus);			
			preparedStatement.setString(13, deviceId);

			preparedStatement.executeUpdate();
			
			stringData.putValueForKey("ROUTE_ID", routeId, "BOOKING_NUM", bookingNumber);
			
			keyUtils.addNewKey("BOOKING", key);
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return stringData;
	}

	
	
	public StringData retrieveBookingCounts(StringData input) {
		StringData routelistStringData = null;
		int count=0;
		double offSetHr=0.0;
		ResultSet result1 = null; 
		String bookType = null;
		try {
			String date = input.getValue(0, "date"); //Pickup_date in 'DD-MON-YYYY' format
			
			
			String pickupTimeHr = input.getValue(0, "pickuptime"); //hr24-mm-ss
			pickupTimeHr = pickupTimeHr.replace('-', ':');
			
			String dateTime = date + ' ' + pickupTimeHr;
			//System.out.println(dateTime);
			
			String offSetStr = input.getValue(0, "time-offset-hr");
			
			try {
				offSetHr = Double.parseDouble(offSetStr);
			} catch (NumberFormatException e) {				
				offSetHr = 1.0;
			}
						
			/**
			 * -- Test Query ---------------------------------
			select r.route_id , b.booking_type, count(booking_type) from BOOKING b, ROUTE_SCH r where 
			b.PICKUP_DT_TM = '27-MAY-2016' 
			and b.ROUTE_ID = r.ROUTE_ID 
			and
			r.DEPRT_TM between 
			(extract(hour from cast( '27-MAY-2016 09:00:00' as timestamp) ) 
			+ ( extract(minute from cast( '27-MAY-2016 09:00:00' as timestamp) ) )/60 ) 
			and 
			(extract(hour from cast( '27-MAY-2016 09:00:00' as timestamp) ) 
			+ ( extract(minute from cast( '27-MAY-2016 09:00:00' as timestamp) ) )/60 ) + 1.0
			group by b.booking_type, r.route_id;

			 */
			String sql= 
				"select r.route_id, b.booking_type, count(b.booking_type) from BOOKING b, ROUTE_SCH r " +
				"where b.PICKUP_DT_TM = ? and b.ROUTE_ID = r.ROUTE_ID " +
				"and r.DEPRT_TM between " +
				"(extract(hour from cast( ? as timestamp) ) + " +
				"( extract(minute from cast( ? as timestamp) ) )/60 ) " +
				"and (extract(hour from cast( ? as timestamp) ) + " +
				"( extract(minute from cast( ? as timestamp) ) )/60 ) + "
				+ offSetHr
				+" group by b.booking_type, r.route_id";
							
						
			System.out.println(" Query :\n"+sql);
			preparedStatement = con.prepareStatement(sql);			

			preparedStatement.setString(1, date);
			preparedStatement.setString(2, dateTime);
			preparedStatement.setString(3, dateTime);
			preparedStatement.setString(4, dateTime);
			preparedStatement.setString(5, dateTime);
            //System.out.println(sql);                    
			result1 = preparedStatement.executeQuery();			
			StringBuilder sbuild = new StringBuilder();
			
			int i = 0;
			int routeId =-1, lastRouteId=-1;
			Map<Integer, Integer[]> routeCountsMap = new HashMap<Integer, Integer[]>(10);
			

			Integer[] arrConfirmTent = new Integer[2];
			
			while (result1.next()) {
				routeId = result1.getInt(1);
				bookType = result1.getString(2);
				count = result1.getInt(3);
				
				if(routeId != lastRouteId) {
					arrConfirmTent[0] =0; arrConfirmTent[1] =0;
				}
				
				if("C".equals(bookType)) {
					arrConfirmTent[0] = count;
				} else if("T".equals(bookType)) {
					arrConfirmTent[1] = count;
				}
				
				routeCountsMap.put(routeId, arrConfirmTent);				
				
				lastRouteId = routeId;
			
				i++;
			}	
			
			for(Integer routeIdInt : routeCountsMap.keySet()) {
				sbuild.append("route_id=").append(routeIdInt).append('|');
				sbuild.append("C=").append(routeCountsMap.get(routeIdInt)[0]).append('|');
				sbuild.append("T=").append(routeCountsMap.get(routeIdInt)[1]).append('^');
			}
			try {
				sbuild.deleteCharAt(sbuild.length()-1);		
			} catch (StringIndexOutOfBoundsException e) {
				
			}
				
			
			routelistStringData = new StringData(sbuild.toString());						

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
	
	public StringData calculateCost(StringData input){
		ResultSet result1 = null;
		StringData calculateCostData = null;
		
		try {
			String routId = input.getValue(0, "ROUTE_ID");
			int intRoutId = Integer.parseInt(routId);
			String onBoardStop = input.getValue(0, "ONBOARD_STOP");
			int intOnBoardStop = Integer.parseInt(onBoardStop);
			String offBoardStop = input.getValue(0, "OFFBOARD_STOP");
			int intoffBoardStop = Integer.parseInt(offBoardStop);
//			if(intOnBoardStop>intoffBoardStop){
//				int a = intoffBoardStop;
//				intoffBoardStop = intOnBoardStop;
//				intOnBoardStop = a;
//			}
			
			String sql = "SELECT l.frezed_Amount AS frezed_Amount ,(f.totalusercostwithlastnextstop ) AS travel_Cost FROM "
					+ "(SELECT SUM (FARE_NEXT_STOP) AS totalusercostwithlastnextstop FROM ROUTE_STOP_MAP WHERE Route_id = ? AND STOP_SEQ_NO BETWEEN "+
    "(SELECT STOP_SEQ_NO FROM ROUTE_STOP_MAP WHERE route_id = ? AND stop_id = ?) AND (SELECT STOP_SEQ_NO FROM ROUTE_STOP_MAP WHERE route_id = ? AND stop_id = ?))f,"
					+
  "(SELECT SUM (FARE_NEXT_STOP) AS frezed_Amount FROM ROUTE_STOP_MAP WHERE Route_id = ?)l where (f.totalusercostwithlastnextstop ) is not null "+
"UNION SELECT l.frezed_Amount AS frezed_Amount ,(f.totalusercostwithlastnextstop ) AS travel_Cost "+
"FROM (SELECT SUM (FARE_NEXT_STOP) AS totalusercostwithlastnextstop FROM ROUTE_STOP_MAP WHERE Route_id = ? AND STOP_SEQ_NO BETWEEN "+
    "(SELECT STOP_SEQ_NO FROM ROUTE_STOP_MAP WHERE route_id = ? AND stop_id = ?)"+
  "AND (SELECT STOP_SEQ_NO FROM ROUTE_STOP_MAP WHERE route_id = ? AND stop_id = ?))f,"+
  "(SELECT SUM (FARE_NEXT_STOP) AS frezed_Amount FROM ROUTE_STOP_MAP WHERE Route_id = ?)l where (f.totalusercostwithlastnextstop) is not null ";
			
			System.out.println("query:\n"+sql);
			preparedStatement = con.prepareStatement(sql);
			System.out.println(intRoutId);
			System.out.println(intOnBoardStop);
			System.out.println(intoffBoardStop);
			preparedStatement.setInt(1, intRoutId);
			preparedStatement.setInt(2, intRoutId);
			preparedStatement.setInt(3, intOnBoardStop);
			preparedStatement.setInt(4, intRoutId);
			preparedStatement.setInt(5, intoffBoardStop);

			preparedStatement.setInt(6, intRoutId);
			
			
			preparedStatement.setInt(7, intRoutId);
			preparedStatement.setInt(8, intRoutId);
			preparedStatement.setInt(9, intoffBoardStop);
			preparedStatement.setInt(10, intRoutId);
			preparedStatement.setInt(11, intOnBoardStop);
			preparedStatement.setInt(12, intRoutId);
			
			
		
			
		
			result1 = preparedStatement.executeQuery();
			int frezedAmount = 0;
			int travelCost = 0;
			while(result1.next()){
				frezedAmount = result1.getInt(1);
				 travelCost = result1.getInt(2);
				System.out.println(frezedAmount);
				System.out.println(travelCost);
			}
			calculateCostData = new StringData("frezed_amount="
					+frezedAmount+"|travel_cost="+travelCost);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
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
			
			//input.getValue(recordIndex, fieldName)
		
			return calculateCostData;
	}
	
public StringData confirmBooking(StringData input){
	UniqueKeyUtils keyUtils = null;
	StringData confirmBookingResp = null;
	
	try {
		
		String booknum = input.getValue(0, "BOOK_NUM");
		String deviceId = input.getValue(0, "DEVICE_ID");
String sql = "update Booking set BOOKING_TYPE = 'C' where BOOKING_NUM = ? and DEVICE_ID = ?";
		
		preparedStatement = con.prepareStatement(sql);	
		preparedStatement.setString(1, booknum);
		int intdeviceId = Integer.parseInt(deviceId);
		preparedStatement.setInt(2, intdeviceId);
	
		preparedStatement.executeQuery();
		String bookType = "C";
		confirmBookingResp = new StringData("BOOK_TYPE="
					+bookType+"|BOOK_NUM="+booknum+"|DEVICE_ID="+deviceId);
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally {
		try {
			if (preparedStatement != null)
				preparedStatement.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
		//input.getValue(recordIndex, fieldName)
			
			
		return confirmBookingResp;
	}



public StringData getExistingBookingData(StringData input) {
	StringData bookingListStringData = null;		
	ResultSet result = null;
	try {
		String device_id = input.getValue(0, "deviceId");
		System.out.println(device_id);
		Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
	String pickDate= sdf.format(date);
	System.out.println(pickDate);
	
	
		String sql = "SELECT Distinct BOOKING_NUM,ROUTE_ID,BOOKING_TYPE,ONBOARD_STOP,OFFBOARD_STOP,PICKUP_DT_TM,query1.ONBOARDSTOPNM,query2.OFFBOARDSTOPNM "+
"FROM BOOKING, "+
  "(SELECT STOP_NM AS ONBOARDSTOPNM FROM SMART_STOP,(SELECT ONBOARD_STOP FROM BOOKING WHERE DEVICE_ID = ? AND PICKUP_DT_TM >= ? ORDER BY "+ "PICKUP_DT_TM)a WHERE STOP_ID = a.ONBOARD_STOP)query1,(SELECT STOP_NM AS OFFBOARDSTOPNM "+
  "FROM SMART_STOP,(SELECT OFFBOARD_STOP FROM BOOKING WHERE DEVICE_ID = ? AND PICKUP_DT_TM >= ? ORDER BY PICKUP_DT_TM)a "+
  "WHERE STOP_ID = a.OFFBOARD_STOP)query2 where DEVICE_ID = ? and PICKUP_DT_TM >= ? ORDER BY PICKUP_DT_TM";
					
		preparedStatement = con.prepareStatement(sql);
											
		preparedStatement.setString(1, device_id);
		preparedStatement.setString(2, pickDate);
		preparedStatement.setString(3, device_id);
		preparedStatement.setString(4, pickDate);
		preparedStatement.setString(5, device_id);
		preparedStatement.setString(6, pickDate);
	
		result = preparedStatement.executeQuery();
//		System.out.println("jknjkn");
		StringBuilder sbBuilder = new StringBuilder();
		while (result.next()) {
			//phoneId = result.getInt(1);
			//String booking_id = result.getString(1);
			String booking_num = result.getString(1);
		
			sbBuilder.append("booking_num=").append(booking_num).append("|");
			//String psngr_id = result.getString(3);
			String route_id = result.getString(2);
			sbBuilder.append("route_id=").append(route_id).append("|");
			String booking_type = result.getString(3);
			sbBuilder.append("booking_type=").append(booking_type).append("|");
			String onboard_stop = result.getString(4);
			sbBuilder.append("onboard_stop=").append(onboard_stop).append("|");
			String offboard_stop = result.getString(5);
			sbBuilder.append("offboard_stop=").append(offboard_stop).append("|");
			String pickup_dt_tm = result.getString(6);
			sbBuilder.append("pickup_dt_tm=").append(pickup_dt_tm).append("|");
			String onboardstopnm = result.getString(7);
			sbBuilder.append("onboardstopnm=").append(onboardstopnm).append("|");
			String offboardstopnm = result.getString(8);
			sbBuilder.append("offboardstopnm=").append(offboardstopnm).append("^");
			
			
			
			
			

			
		}  
		
		try {
			sbBuilder.deleteCharAt(sbBuilder.length()-1);
			bookingListStringData = new StringData(sbBuilder.toString());
			
		} catch (StringIndexOutOfBoundsException e) {
			
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
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return bookingListStringData;
}

}


