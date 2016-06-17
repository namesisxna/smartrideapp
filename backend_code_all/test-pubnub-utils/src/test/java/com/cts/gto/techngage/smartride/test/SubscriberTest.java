package com.cts.gto.techngage.smartride.test;


import com.cts.gto.pubnub.utils.common.CallbackSubscribe;
import com.cts.gto.pubnub.utils.core.PubnubUtils;

import com.pubnub.api.PubnubException;


public class SubscriberTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {			
			String pubKey = "pub-c-d1fd173b-9659-49bb-bd2a-932dbb59b4ed";
			String subKey = "sub-c-98efdd80-aa19-11e5-bb8b-02ee2ddab7fe";

			
			PubnubUtils pubnub = PubnubUtils.getInstance(pubKey, subKey);
			
			MyListener myListener = new MyListener("cell-1199");
			CallbackSubscribe cback = new CallbackSubscribe(myListener);
			
			pubnub.subscribe("ss_channl", cback);
			
			pubnub.subscribe("ss_channl1", cback);
			
			pubnub.subscribe("ss_channl2", cback);

		} catch (PubnubException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
