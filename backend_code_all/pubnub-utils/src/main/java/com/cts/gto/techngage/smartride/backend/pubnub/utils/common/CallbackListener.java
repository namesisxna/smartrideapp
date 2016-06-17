package com.cts.gto.techngage.smartride.backend.pubnub.utils.common;

import org.json.JSONObject;

public interface CallbackListener {
	
	public void onSuccessReceive(String ch, JSONObject msg);
	
	public void onSuccessReceive(String ch, String msg);
	
	
}
