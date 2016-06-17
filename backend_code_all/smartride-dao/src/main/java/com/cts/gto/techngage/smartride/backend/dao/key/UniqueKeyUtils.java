package com.cts.gto.techngage.smartride.backend.dao.key;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import com.cts.gto.techngage.smartride.backend.db.utils.core.DbConnectionUtils;



public class UniqueKeyUtils {

	private static final String[] TABLES = new String[] {
			"PASSENGER_PHONE=DEVICE_ID", "ROUTE_SCH=ROUTE_ID"
			,"BOOKING=BOOKING_ID","ROUTE_STOP_MAP=MAP_ID"};

	private Map<String, SortedSet<String>> keySetMap;

	// ==============================================================
	private static UniqueKeyUtils instance;

	private UniqueKeyUtils() {
		init();
	}

	public static UniqueKeyUtils getInstance() {
		instance = instance == null ? new UniqueKeyUtils() : instance;
		return instance;
	}

	// ==============================================================

	private void init() {

		keySetMap = keySetMap == null ? new HashMap<String, SortedSet<String>>(10) : keySetMap;

		for (String table : TABLES) {
			String tableNm = table.substring(0, table.indexOf('='));
			String colunmNm = table.substring(table.indexOf('=') + 1);

			SortedSet<String> tableKeySet = keySetMap.get(tableNm);
			tableKeySet = (tableKeySet == null) ? getTreeSet()	: tableKeySet;

			keySetMap.put(tableNm, tableKeySet);
			
			loadTableKeys(tableKeySet, tableNm, colunmNm);
			System.out.println(" Table loaded : " + tableNm);
		}
		System.out.println(" -- Table Key loading completed-- ");
	}

	private void loadTableKeys(SortedSet<String> tableKeySet, String tableName,
			String columnName) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = DbConnectionUtils.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("select " + columnName + " from "+ tableName
								+ " order by " + columnName);
			while (rs.next()) {
				String key = rs.getString(columnName);
				tableKeySet.add(key);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static String findEffectiveSeqNumber(SortedSet<String> keySet) throws Exception {
					
			long newKeyLong = 0;
			int i=0;	
			long lastNo = 0;
			int len = keySet.size();
			boolean gapFound = false;
					
			if(len > 0) {
								
				for(String key : keySet) {
					long keyNo = Long.parseLong(key);
					long diff = keyNo - lastNo;
					
					if(diff > 1) {
						newKeyLong = (lastNo + 1);
						gapFound = true;
						break;
					}
					else if(diff == 1) {
						
					}
					else if(diff < 1) {
						if(diff == 0) {
							
						} else {
							
						}
					}	
					lastNo = keyNo;					
					
					i++;
				}//for---	
				
				if(!gapFound) { newKeyLong = lastNo+1; }
			}
			else {
				newKeyLong = 1;
			}
			
			return ""+newKeyLong;		
	}
	
		
	public String generateNewKey(String tableName) throws Exception {
		
		SortedSet<String> keySet =	keySetMap.get(tableName);
		String newKey = findEffectiveSeqNumber(keySet);	
		
		System.out.println(" Key for "+tableName+" : "+newKey);
								
		return newKey;
	}
	
	
	public void addNewKey(String tableName, String key) {
		SortedSet<String> keySet =	keySetMap.get(tableName);
		keySet.add(key);
	}
	

	
	public static void main(String[] ags) {
		UniqueKeyUtils utils = null;
		String key = null;
		try {
			utils = UniqueKeyUtils.getInstance();
			key = utils.generateNewKey("PASSENGER_PHONE");
		} catch (Exception e) {			
			e.printStackTrace();
		}
		System.out.println(" Key = "+key);
	}
	
	
	private SortedSet<String> getTreeSet() {
		
		SortedSet<String> s1 = new TreeSet<String>(new Comparator<String>() {
	        /**
	         * Returns a positive value if number1 is larger than number 2, a
	         * negative number if number1 is less than number2, and 0 if they
	         * are equal.
	         */
	        public int compare(String number1, String number2) {
	            return Integer.parseInt(number1) - Integer.parseInt(number2);
	        }
	    });
		return s1;
	}
	
	
}
