package com.bomltsportal.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServerUtils {

	protected final Log logger = LogFactory.getLog(getClass());
	
	public static boolean testCeksCatureReady(String testLink, String testString){
		boolean ceksReady = false;
		ServerUtils utils = new ServerUtils();
		try{
			//For testing
			
			utils.logger.info("testLink: " + testLink);
			utils.logger.info("testString: " + testString);
			URL ceksTestURL = new URL(testLink);
			utils.logger.info("Open Connection to " + testLink);
			URLConnection urlConnection = ceksTestURL.openConnection();
			utils.logger.info("set Timeout");
			urlConnection.setConnectTimeout(100000);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String URLContent = "";
			String inStr = "";
			inStr = in.readLine();
			while (inStr != null){
			    URLContent += inStr;
			    inStr = in.readLine();
			}
			in.close();
			utils.logger.info("URLContent: " + URLContent);
			if(testString.equalsIgnoreCase(URLContent)){
				ceksReady = true;
			}else{
				ceksReady = false;
			}
		}catch(Exception e){
			//ceksReady = false;
			ceksReady = true;
			utils.logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		return ceksReady;

	}
	
}
