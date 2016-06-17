package com.cts.gto.techngage.smartride.backend.pubnub.utils.common;

import org.json.JSONException;
import org.json.JSONObject;

import com.cts.gto.techngage.smartride.backend.pubnub.utils.common.CallbackListener;
import com.cts.gto.techngage.smartride.backend.pubnub.utils.data.BothwayMessage;
import com.cts.gto.techngage.smartride.backend.pubnub.utils.data.CommonMessage;

public abstract class DefaultCallbackListener implements CallbackListener {

	private boolean isBothway;
	private BothwayMessage bothwayMessage;
	private CommonMessage commonMessage;
	private String deviceUuid;
	
	private String receivedData;
	
	public DefaultCallbackListener() {}
	public DefaultCallbackListener(String deviceUuid) { this.deviceUuid=deviceUuid; }

	public void onSuccessReceive(String ch, JSONObject msg) {
		
		try {
			String uuid = msg.getString("uuid");
			isBothway = uuid != null && !uuid.isEmpty();
			
			if(isBothway) {
				if(uuid.equals(deviceUuid)) {
					System.out.println("-- skip own message --");
				} else {
					bothwayMessage = new BothwayMessage(uuid);
					bothwayMessage.setJson(msg);
				}				
			}
			else {
				commonMessage = new CommonMessage();
				commonMessage.setJson(msg);
			}			
		} catch (JSONException e) {			
			e.printStackTrace();
		}
	}
	
	
	public void onSuccessReceive(String ch, String msg) {
		
		receivedData = msg;
	}
	
	
	public boolean isBothway() {
		return isBothway;
	}

	public BothwayMessage getBothwayMessage() {
		return bothwayMessage;
	}

	public CommonMessage getCommonMessage() {
		return commonMessage;
	}

	public String getDeviceUuid() {
		return deviceUuid;
	}

	public void setDeviceUuid(String deviceUuid) {
		this.deviceUuid = deviceUuid;
	}
	public String getReceivedData() {
		return receivedData;
	}
	public void setReceivedData(String receivedData) {
		this.receivedData = receivedData;
	}	

}
