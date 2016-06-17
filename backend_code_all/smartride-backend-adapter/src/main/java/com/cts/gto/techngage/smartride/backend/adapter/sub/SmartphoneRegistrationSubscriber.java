package com.cts.gto.techngage.smartride.backend.adapter.sub;

import com.cts.gto.techngage.smartride.backend.adapter.common.IConstants;
import com.cts.gto.techngage.smartride.backend.adapter.listener.BookingGroupListener;
import com.cts.gto.techngage.smartride.backend.adapter.listener.SmartphoneRegistrationListener;
import com.cts.gto.techngage.smartride.backend.pubnub.utils.common.CallbackSubscribe;
import com.cts.gto.techngage.smartride.backend.pubnub.utils.core.PubnubUtils;
import com.pubnub.api.Callback;
import com.pubnub.api.PubnubException;


public class SmartphoneRegistrationSubscriber {
	
//	public void subscribe() {
//		//BOOKING_CHANNEL_GROUP
//		
//		PubnubUtils pubnub = PubnubUtils.getInstance(IConstants.pubKey, IConstants.subKey);
//		
//		BookingGroupListener myListener = new BookingGroupListener();
//		CallbackSubscribe cback = new CallbackSubscribe(myListener);
//		
//		try {
//			pubnub.subscribeGroup(IConstants.BOOKING_CHANNEL_GROUP, cback);
//		} catch (PubnubException e) {			
//			e.printStackTrace();
//		}
//	}
	
	
	public void subscribeString() {
		//BOOKING_CHANNEL_GROUP
		
		PubnubUtils pubnub = PubnubUtils.getInstance(IConstants.pubKey, IConstants.subKey);
		
		SmartphoneRegistrationListener myListener = new SmartphoneRegistrationListener();
		CallbackSubscribe cback = new CallbackSubscribe(myListener);
		
		
		try {			
			pubnub.subscribe(IConstants.GLOBAL_SMARTPHONE_REGISTRATION_CHANNEL, cback);
		} catch (PubnubException e) {			
			e.printStackTrace();
		}
	}

}
