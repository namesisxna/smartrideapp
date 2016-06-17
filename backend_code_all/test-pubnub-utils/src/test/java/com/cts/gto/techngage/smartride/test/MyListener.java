package com.cts.gto.techngage.smartride.test;


import org.json.JSONObject;

import com.cts.gto.pubnub.utils.common.DefaultCallbackListener;
import com.cts.gto.pubnub.utils.data.CommonMessage;


public class MyListener extends DefaultCallbackListener {

	public MyListener() {}
	public MyListener(String deviceUuid) {
		super(deviceUuid);
	}

	public void onSuccessReceive(String ch, JSONObject msg) {
		System.out.println("MyListener.onSuccessReceive().. calling business method..");
		
		System.out.println(" Raw response "+ msg.toString());
		
		super.onSuccessReceive(ch, msg);		
		boolean isBothwayMessage = super.isBothway();
		
		CommonMessage message = isBothwayMessage ? 
									super.getBothwayMessage() : super.getCommonMessage(); 
		
		//-- Delegating request to business logic method ---------
		BusinessDelegate bizLogic = new BusinessDelegate();
		
		if(message != null) {
			bizLogic.showResult(message);
		}
		
	}
		
	
	

}
