package com.cts.gto.techngage.smartride.backend.pubnub.utils.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class CommonUtil {
	
	public static List<String> readData(String uri) {
		List<String> list=null;
		BufferedReader reader = null;
		try {
			reader= new BufferedReader(new FileReader(uri));
			
			String line = ""; 
			int i = 0;
			
			while((line=reader.readLine()) != null) {
				if(i==0) {
					list = new ArrayList<String>(100);
				}
				list.add(line);
				i++;
			}
			
			System.out.println("readData done");
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	
		
	private void copyData() {
		PrintWriter out = null;
		try {
			out= new PrintWriter(new BufferedWriter(
				new FileWriter("E:/codebase/all-demo/gto-pocs/pubnub-demo/src/main/resources/rout-data.txt")));
			
			StringTokenizer tok =null; // new StringTokenizer(IConstants.KOL_NJP.toString(), "|");
			
			while(tok.hasMoreTokens()) {
				String val = tok.nextToken();
				out.println(val);
			}
			
			System.out.println("Copy done");
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CommonUtil cUtil = new CommonUtil();
		cUtil.copyData();
	}

}
