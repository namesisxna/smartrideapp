package com.cts.gto.techngage.smartride.backend.adapter.listener;


import com.cts.gto.techngage.smartride.backend.pubnub.utils.common.CallbackPublish;

import com.cts.gto.techngage.smartride.backend.pubnub.utils.common.DefaultCallbackListener;
import com.cts.gto.techngage.smartride.backend.pubnub.utils.core.PubnubUtils;

import com.cts.gto.techngage.smartride.backend.adapter.common.IConstants;
import com.cts.gto.techngage.smartride.backend.adapter.delegate.BusinessDelegate;
import com.cts.gto.techngage.smartride.backend.dataobj.StringData;
import com.pubnub.api.Callback;
import com.pubnub.api.PubnubException;


public class SmartphoneRegistrationListener extends DefaultCallbackListener {
	
	public SmartphoneRegistrationListener() {}
	public SmartphoneRegistrationListener(String deviceUuid) {
		super(deviceUuid);
	}
	
	public void onSuccessReceive(String ch, String msg) {
		
		System.out.println("SmartphoneRegistratnListener.onSuccessReceive().. calling business method..");
		System.out.println("Channel : "+ch+"| Raw response "+ msg.toString());
					
		StringData strData = null, respData=null;		
		try {
			strData = new StringData("DEVICE_UUID="+msg);
		} catch (Exception e) {			
			e.printStackTrace();
		}		
		String respChannel = msg + "_ch";
				
		//-- Delegating request to business logic method ---------
		BusinessDelegate bizLogic = new BusinessDelegate();		
	
		respData = bizLogic.delegateSmartphoneRegistration(strData);
		
		try {
			String deviceRegNo = respData.getValue(0, "phone-id");
			String deviceChannel = respData.getValue(0, "ph-channel");
			
			System.out.println("SmartphoneRegistratnListener.onSuccessReceive() Response data ="+respData.toString());
						
			PubnubUtils pubnub = PubnubUtils.getInstance(IConstants.pubKey, IConstants.subKey);
			Callback cback1 = new Callback() {};		
			
			pubnub.addChannelToGroup(IConstants.BOOKING_CHANNEL_GROUP, deviceChannel, cback1);
			System.out.println("SmartphoneRegistratnListener.onSuccessReceive() Chaneel "+deviceChannel+" added to Group.");
						
			CallbackPublish cbackPublish = new CallbackPublish() {};
			
			pubnub.publish(respChannel, deviceRegNo, cbackPublish);
			
			System.out.println("SmartphoneRegistratnListener.onSuccessReceive() Sent device registration number to SmartPhone. ");
			
		} catch (PubnubException e) {			
			e.printStackTrace();
		} catch (Exception e) {			
			e.printStackTrace();
		}		
		//--------------		
	}
	

}
