package com.cts.gto.techngage.smartride.backend.pubnub.utils.common;

//import org.json.JSONException;
//import org.json.JSONObject;

import com.pubnub.api.Callback;
import com.pubnub.api.PubnubError;

public class CallbackSubscribe extends Callback {

	private CallbackListener listener;
	
	public CallbackSubscribe(CallbackListener listener) {
		this.listener = listener;
	}
	
	@Override
	public void connectCallback(String channel, Object message) {
		System.out.println("SUBSCRIBE : CONNECT on channel:"
				+ channel + " : " + message.getClass() + " : "
				+ message.toString());
	}

	@Override
	public void disconnectCallback(String channel, Object message) {
		System.out.println("SUBSCRIBE : DISCONNECT on channel:"
				+ channel + " : " + message.getClass() + " : "
				+ message.toString());
	}

	public void reconnectCallback(String channel, Object message) {
		System.out.println("SUBSCRIBE : RECONNECT on channel:"
				+ channel + " : " + message.getClass() + " : "
				+ message.toString());
	}

	@Override
	public void successCallback(String channel, Object message) {
		System.out.println("-- begin Success-- Channel :"+channel);
		try {
//			JSONObject json = null;
//			String str = null;
			
			listener.onSuccessReceive(channel, (String)message);
			
//			if(message instanceof String) {
//				listener.onSuccessReceive(channel, (String)message);
//				try {
//					str = (String)message;
//					json = new JSONObject(str);
//				} catch(JSONException e) {
//					str = "{  \"key\": \""+str+"\"  }";
//					json = new JSONObject(str);
//				}
//			} else {
//				json = (JSONObject)message;
//				listener.onSuccessReceive(channel, json);
//			}
			
						
			System.out.println("-- end Success--");		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void errorCallback(String channel, PubnubError error) {
		System.out.println("SUBSCRIBE : ERROR on channel "
				+ channel + " : " + error.toString());
	}

	public CallbackListener getListener() {
		return listener;
	}

	public void setListener(CallbackListener listener) {
		this.listener = listener;
	}

}
