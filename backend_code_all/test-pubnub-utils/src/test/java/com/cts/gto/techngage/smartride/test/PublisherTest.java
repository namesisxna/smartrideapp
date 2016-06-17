package com.cts.gto.techngage.smartride.test;


import com.cts.gto.pubnub.utils.common.CallbackPublish;
import com.cts.gto.pubnub.utils.core.PubnubUtils;
import com.cts.gto.pubnub.utils.data.BothwayMessage;


public class PublisherTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			String pubKey = "pub-c-d1fd173b-9659-49bb-bd2a-932dbb59b4ed";
			String subKey = "sub-c-98efdd80-aa19-11e5-bb8b-02ee2ddab7fe";			
									
			BothwayMessage myMessageBothway = new BothwayMessage("112233-3455");
			
			myMessageBothway.setData("key1", "data1");
			myMessageBothway.setData("key2", "data2");	
			
			BothwayMessage myMessageBothway1 = new BothwayMessage("cell-1199");
			
			myMessageBothway1.setData("key1199", "data1 1199");
			myMessageBothway1.setData("key2-1199", "data2 1199");

			PubnubUtils pubnub = PubnubUtils.getInstance(pubKey, subKey);
			
			CallbackPublish callbackPublish = new CallbackPublish();
			
			//pubnub.publish("ss_channl", myMessageBothway, callbackPublish);
			
			pubnub.publish("ss_channl", myMessageBothway1, callbackPublish);
			
			pubnub.publish("ss_channl1", myMessageBothway, callbackPublish);
			
			pubnub.publish("ss_channl2", myMessageBothway1, callbackPublish);
						

		} catch (Exception e) {
			
			e.printStackTrace();
		}

	}

}
