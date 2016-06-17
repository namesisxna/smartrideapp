package com.cts.gto.techngage.smartride.backend.pubnub.utils.core;

import org.json.JSONArray;

import com.cts.gto.techngage.smartride.backend.pubnub.utils.common.CallbackPublish;
import com.cts.gto.techngage.smartride.backend.pubnub.utils.common.CallbackSubscribe;
import com.cts.gto.techngage.smartride.backend.pubnub.utils.data.BothwayMessage;
import com.cts.gto.techngage.smartride.backend.pubnub.utils.data.CommonMessage;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubException;

public class PubnubUtils {

	private static PubnubUtils pubnunUtils;
	private Pubnub pubnub;
	
	private PubnubUtils(String pubKey, String subKey) {
		pubnub = pubnub == null ? new Pubnub(pubKey, subKey) : pubnub;
	}

	
	
	public static PubnubUtils getInstance(String pubKey, String subKey) {
		pubnunUtils = pubnunUtils == null ? 
				new PubnubUtils(pubKey, subKey) : pubnunUtils;
				
				
		return pubnunUtils;
	}
	
	public void addChannelToGroup(String channelGroup, String channel, Callback callback) throws PubnubException {
		pubnub.channelGroupAddChannel(channelGroup, channel, callback);
	}
	
	public void removeChannelFromGroup(String channelGroup, String channel, Callback callback) throws PubnubException {
		pubnub.channelGroupRemoveChannel(channelGroup, channel, callback);
	}
	
	
	
	public void subscribe(String channel, CallbackSubscribe callback) throws PubnubException {
		pubnub.subscribe(channel, callback);
	}
	
	public void subscribeGroup(String channelGrp, CallbackSubscribe callback) throws PubnubException {
		pubnub.channelGroupSubscribe(channelGrp, callback);
	}
	
	public void publish(String channel, CommonMessage commonMsg, CallbackPublish callback) {
		pubnub.publish(channel, commonMsg.getJson() , callback);
	}
	
	public void publish(String channel, BothwayMessage bothwayMsg, CallbackPublish callback) {
		pubnub.publish(channel, bothwayMsg.getJson() , callback);
	}
	
	public void publish(String channel, String jsonString, CallbackPublish callback) {
		pubnub.publish(channel, jsonString, callback);
	}
	
	public void publish(String channel, JSONArray jsonString, CallbackPublish callback) {
		pubnub.publish(channel, jsonString, callback);
	}
	
	public void getHistory(String channel, String jsonString, CallbackPublish callback) {
		pubnub.history(channel, 1, callback);
	}
}
