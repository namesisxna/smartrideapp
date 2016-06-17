package com.cts.gto.techngage.smartride.backend.adapter.pub;

import org.json.JSONArray;
import org.json.JSONException;

import com.cts.gto.techngage.smartride.backend.pubnub.utils.common.CallbackPublish;
import com.cts.gto.techngage.smartride.backend.pubnub.utils.core.PubnubUtils;
import com.cts.gto.techngage.smartride.backend.pubnub.utils.data.BothwayMessage;
import com.cts.gto.techngage.smartride.backend.adapter.common.IConstants;

public class BookingBackendPublisher {
	
	public void publish(String publishChannel, BothwayMessage bothwayMessage) {
		PubnubUtils pubnub = PubnubUtils.getInstance(IConstants.pubKey, IConstants.subKey);
		
		CallbackPublish callbackPublish = new CallbackPublish();		
		
		pubnub.publish(publishChannel, bothwayMessage, callbackPublish);
		System.out.println("--- BookingBackendPublisher publish done at channel :"+ publishChannel);
	}
	
	
	public void publishStringMessage(String publishChannel, String message) {
		PubnubUtils pubnub = PubnubUtils.getInstance(IConstants.pubKey, IConstants.subKey);
		
		CallbackPublish callbackPublish = new CallbackPublish();	
		JSONArray jarr = null;
		try {
			jarr = new JSONArray(message);
		} catch (JSONException e) {			
			e.printStackTrace();
		}
		
		pubnub.publish(publishChannel, jarr, callbackPublish);
		System.out.println("--- BookingBackendPublisher String message publish done at channel :"+ publishChannel);
	}
	
	public void publishStringMessage1(String publishChannel, String message) {
		PubnubUtils pubnub = PubnubUtils.getInstance(IConstants.pubKey, IConstants.subKey);
		
		CallbackPublish callbackPublish = new CallbackPublish();		
		
		pubnub.publish(publishChannel, message, callbackPublish);
		System.out.println("--- BookingBackendPublisher String message publish done at channel :"+ publishChannel);
	}

}
