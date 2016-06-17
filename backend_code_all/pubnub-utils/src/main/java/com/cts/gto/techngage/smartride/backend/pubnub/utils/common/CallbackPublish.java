package com.cts.gto.techngage.smartride.backend.pubnub.utils.common;

import com.pubnub.api.Callback;
import com.pubnub.api.PubnubError;

public class CallbackPublish extends Callback {

		
	@Override
	public void connectCallback(String channel, Object message) {
		System.out.println("Publish : CONNECT on channel:"
				+ channel + " : " + message.getClass() + " : "
				+ message.toString());
	}

	@Override
	public void disconnectCallback(String channel, Object message) {
		System.out.println("Publish : DISCONNECT on channel:"
				+ channel + " : " + message.getClass() + " : "
				+ message.toString());
	}

	public void reconnectCallback(String channel, Object message) {
		System.out.println("Publish : RECONNECT on channel:"
				+ channel + " : " + message.getClass() + " : "
				+ message.toString());
	}

	@Override
	public void successCallback(String channel, Object message) {
		System.out.println("-- Publish Success--");
	}

	@Override
	public void errorCallback(String channel, PubnubError error) {
		System.out.println("Publish : ERROR on channel "
				+ channel + " : " + error.toString());
	}

	
}
