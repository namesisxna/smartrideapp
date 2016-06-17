package com.cts.gto.techngage.smartride.backend.scheduler.job;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.security.crypto.codec.Base64;


public class CommonUtil {
	
	public static Properties readPropertyFile(String absolutePropFileUri) throws IOException {
		Properties prop = new Properties();
		InputStream is = new FileInputStream(new File(absolutePropFileUri));
        prop.load(is);
		is.close();
		
		return prop;
	}
	
	public static Properties readPropertyFileFromClasspath(String fileName) {
		InputStream is = null;
		Properties prop = null;
        try {
        	is = CommonUtil.class.getResourceAsStream("/"+fileName);
        	prop = new Properties();
        	prop.load(is);
        }catch(IOException e){
            e.printStackTrace();            
        } finally {
        	try {
				is.close();
			} catch (IOException e) {}
        }
        return prop;
	}
	
	
	public static String escapeDoubleQuote(String txt) throws Exception {
		return txt.replace('"', '^').replace("^", "\\\"");
	}
	
	public static String escapeSlash(String txt) throws Exception {
		return txt.replace('\\', '^').replace("^", "\\\\");
	}
	
	
	public static String toUnixPathSeparator(String pathStr) {
		if(pathStr != null && pathStr.contains("\\")) {
			pathStr = pathStr.replace('\\', '/');
		}
		return pathStr;
	}
	
	
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String txt = "<br>C:\\aaaa\\ssss\\aaa &gt; <DIR>";
		
		
		byte[]   bytesEncoded = Base64.encode(txt.getBytes());
		String encodedStr = new String(bytesEncoded );
		System.out.println("ecncoded value is " + encodedStr);

		// Decode data on other side, by processing encoded data
		byte[] valueDecoded= Base64.decode(encodedStr.getBytes() );
		System.out.println("Decoded value is " + new String(valueDecoded));

	}

}
