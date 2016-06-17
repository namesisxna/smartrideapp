package com.cts.gto.techngage.smartride.test;

import java.util.Map;

import com.cts.gto.pubnub.utils.data.BothwayMessage;
import com.cts.gto.pubnub.utils.data.CommonMessage;

public class BusinessDelegate {

	
	public void showResult(CommonMessage response) {
		try {	
			boolean isBothwayMessage = response.isBothway();
			System.out.println("******* BUSINESS METHOD INVOKED *************** ");
			System.out.println("showResult(): isBothwayMessage = "+ isBothwayMessage);
			
			String uuid = null;
			BothwayMessage bothwayMessage = (BothwayMessage) response;
			
			if(isBothwayMessage) {
				uuid = bothwayMessage.getUuid();
				System.out.println(" uuid : "+ uuid);
								
				Map<String, String> map = bothwayMessage.getDataMap();
				
				for(Object key : map.keySet() ) {
					System.out.println(key +" = "+map.get(key));
				}
				
			}
						
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
