package com.cts.gto.techngage.smartride.backend.pubnub.utils.data;

import org.json.JSONException;
import org.json.JSONObject;


public class BothwayMessage extends CommonMessage {
	
	private String uuid;
	
	
	public BothwayMessage(String uuid) {
		super();
		super.setBothway(true);		
		setUuid(uuid);
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
		try {
			super.getJson().put("uuid", uuid);
		} catch (JSONException e) {			
			e.printStackTrace();
		}
	}

	public void setJsonString(String jsonString) {
		super.setJsonString(jsonString);
				
		JSONObject jsonObj = super.getJson();
		try {
			jsonObj.put("uuid", uuid);
		} catch (JSONException e) {			
			e.printStackTrace();
		}
	}
}
