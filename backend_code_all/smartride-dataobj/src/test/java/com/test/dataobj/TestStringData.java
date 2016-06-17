package com.test.dataobj;

import com.cts.gto.techngage.smartride.backend.dataobj.StringData;

public class TestStringData {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String initStrData = "route=4|pick=pic12|dest=dst21|time=10h30m"
				+"^route=5|pick=pic52|dest=dst55|time=11h45m";
		
		try {
			StringData strData = new StringData(initStrData);
			
			System.out.println("Init :"+strData.toString());
			
			int tentCount1=10, confirmCount1=21;
			int tentCount2=9, confirmCount2=17;
		
			strData.putValueForKey("route", "4", "tent-count", ""+tentCount1);
			strData.putValueForKey("route", "4", "confirm-count", ""+confirmCount1);
			
			strData.putValueForKey("route", "5", "tent-count", ""+tentCount2);
			strData.putValueForKey("route", "5", "confirm-count", ""+confirmCount2);
			
			System.out.println("Updated StringData :"+strData.toString());
		
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}

}
