package com.cts.gto.techngage.smartride.backend.dataobj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

@SuppressWarnings("serial")
public class StringData implements Serializable {
	
	private String fieldSep;
	private String recordSep;
	
	private String stringValue;
	
	private List<Map<String, String>> dataList;
	
	private boolean valueChanged;
	
	private void init() {
		//Map<String, String> map = new HashMap<String, String>(5);
		dataList = new ArrayList<Map<String, String>>(2);
		//dataList.add(map);
	}
	
	public StringData() {
		init();
	}
	
	public StringData(String stringValue) throws Exception {
		
		init();
		setRecordSep("^");
		setFieldSep("|");
		
		setStringValue(stringValue);
	}
	
	public StringData(String stringValue, String recordSeperator, String fieldSeperator) throws Exception {
		init();
		setRecordSep(recordSeperator);
		setFieldSep(fieldSeperator);
		
		setStringValue(stringValue);
	}
	
	
	private void parse(String stringValue) throws Exception {
		recordSep = recordSep == null ? "^" : recordSep;
		fieldSep = fieldSep == null ? "|" : fieldSep;
		
		StringTokenizer stokRec = new StringTokenizer(stringValue, recordSep);
		
		try {
			while(stokRec.hasMoreTokens()) {
				String recData = stokRec.nextToken();
				
				StringTokenizer stokField = new StringTokenizer(recData, fieldSep);			
				Map<String, String> recMap = new HashMap<String, String>(5);
				
				while(stokField.hasMoreTokens()) {
					String fieldData = stokField.nextToken();
					String fldNm = fieldData.substring(0, fieldData.indexOf('='));
					String value = fieldData.substring(fieldData.indexOf('=')+1);
					
					recMap.put(fldNm, value);
				}
				dataList.add(recMap);
			}
		} catch (Exception e) {			
			//e.printStackTrace();
			throw new Exception("Parse Error : Invalid Format");
		}
	}
	
	
	private void reSerializeData() {
		StringBuilder sBuild = new StringBuilder();
		for(Map<String, String> recMap : dataList) {
			for(String key : recMap.keySet()) {
				sBuild.append(key).append('=').append(recMap.get(key)).append('|');
			}
			sBuild.deleteCharAt(sBuild.length()-1);
			sBuild.append('^');
		}
		sBuild.deleteCharAt(sBuild.length()-1);
		stringValue = sBuild.toString();
	}
	
	
		
	public String getValue(int recordIndex, String fieldName) throws Exception {
		return dataList.get(recordIndex).get(fieldName);
	}
	
	public void setValue(int recordIndex, String fieldName, String value) throws Exception {
		dataList.get(recordIndex).put(fieldName, value);
	}
	
	public void updateValueForKey(String key, String value) throws Exception {
		for(Map<String, String> recMap : dataList) {
			if(recMap.containsKey(key)) {
				valueChanged = true;
				recMap.put(key, value);
			}
		}//--for---
	}
	
	public void putValueForKey(String finderKey, String finderValue, String newKey, String newValue) throws Exception {
		for(Map<String, String> recMap : dataList) {
			String val = recMap.get(finderKey);			
			if(val != null && val.equals(finderValue) ) {	
				valueChanged = true;
				recMap.put(newKey, newValue);
			}
		}//--for---
	}
	
	public String getValueForKey(String finderKey, String finderValue, String key) throws Exception {
		String toReturn = null;
		for(Map<String, String> recMap : dataList) {
			String val = recMap.get(finderKey);			
			if(val != null && val.equals(finderValue) ) {				
				toReturn = recMap.get(key);
				break;
			}
		}//--for---
		return toReturn;
	}
	
	
	public Map<String, String> getRecord(int recordIndex) {
		return dataList.get(recordIndex);
	}

	public List<Map<String, String>> getDataList() {
		return dataList;
	}

	public void setStringValue(String stringValue) throws Exception {
		this.stringValue = stringValue;
		parse(stringValue);
	}
	
	public String toString() {
		if(valueChanged) {
			reSerializeData();
		}
		
		return stringValue;
	}
	
	
//---------------------------------------------------------------------	
	
	public String getFieldSep() {
		return fieldSep;
	}

	public void setFieldSep(String fieldSep) {
		this.fieldSep = fieldSep;
	}

	public String getRecordSep() {
		return recordSep;
	}

	public void setRecordSep(String recordSep) {
		this.recordSep = recordSep;
	}

	
	
	
	
	//----- To Demostrate the Usage ---------------------------	
	public static void test() {		
		String data = "Id=1|fName=Sougata|lName=Sinha"
						+ "^Id=2|fName=Sumit|lName=Datta"
						+ "^Id=3|fName=Shouvik|lName=Sasmal";	
		
		try {
			StringData strData = new StringData(data);
			System.out.println("Rec:2 First Name :"+strData.getValue(1, "fName"));
			
			for(Map<String, String> recMap : strData.getDataList()) {
				String ID = recMap.get("Id");
				String firstName = recMap.get("fName");
				String lastName = recMap.get("lName");
				System.out.println("ID:"+ID+", First Name:"+firstName+", Last Name:"+lastName);
			}			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] a) {
		 test();
	}

}
