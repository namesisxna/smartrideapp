package com.cts.gto.techngage.smartride.backend.pubnub.utils.data;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class CommonMessage {

	private JSONObject json;
	private boolean isBothway;
	private Map<String, String> dataMap;
	
	public CommonMessage() {
		json = new JSONObject();
		dataMap = new HashMap<String, String>(10);		
		try {
			json.put("dataMap", dataMap);
		} catch (JSONException e) {			
			e.printStackTrace();
		}
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}
	
	public void setJsonString(String jsonString) {
		try {
			json = new JSONObject(jsonString);
		} catch (JSONException e) {
			e.printStackTrace();
			try {
				json = new JSONObject("{\"S\": \""+jsonString+"\" }");
			} catch (JSONException e1) {}
		}
	}

	public boolean isBothway() {
		return isBothway;
	}

	void setBothway(boolean isBothway) {
		this.isBothway = isBothway;
	}

	
	public Map<String, String> getDataMap() {
		Map<String, String> map = null;
		try {			
			JSONObject map1 = (JSONObject) json.get("dataMap");
			String[] keyArr = JSONObject.getNames(map1);
						
			if(keyArr != null) {
				map = new HashMap<String, String>(2);
				
				for(String key : keyArr) {
					map.put(key, map1.getString(key));
				}	
			}			
		} catch (JSONException e) {			
			e.printStackTrace();
		}
		return map;
	}
	
	
	public void setDataMap(Map<String, String> map) {
		try {
			json.put("dataMap", map);
		} catch (JSONException e) {			
			e.printStackTrace();
		}
	}

	
	public String getData(String key) {
		String data = null;
		try {
			JSONObject map1 = (JSONObject) json.get("dataMap");
			data= map1.getString(key);
		} catch (JSONException e) {			
			//e.printStackTrace();
		}
		return data;
	}
	
	public void setData(String key, String data) {
		try {
			JSONObject map1 = (JSONObject) json.get("dataMap");
			map1.put(key, data);
		} catch (JSONException e) {			
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		CommonMessage commMsg = new CommonMessage();
		Map<String, String> myMap = commMsg.getDataMap();
		
		if(myMap != null) {
			System.out.println(" myMap : "+ myMap.toString());
		}		
		String data1 = commMsg.getData("testkey1");
		System.out.println(" my data (testkey1): "+ data1);
		System.out.println(" -----------------------------------------");
		
		Map<String, String> newMap = new HashMap<String, String>();
		newMap.put("testkey1", "new data 1");
		newMap.put("new-key2", "new data 2");
		
		commMsg.setDataMap(newMap);
					
		Map<String, String> myMap1 = commMsg.getDataMap();
		System.out.println(" myMap : "+ myMap1.toString());
		System.out.println(" my data (testkey1): "+ commMsg.getData("testkey1"));
		System.out.println(" -----------------------------------------");
		
		commMsg.setData("testkey1", "again new data");
		System.out.println(" my data (testkey1): "+ commMsg.getData("testkey1"));
	}
	
}
