package com.bomwebportal.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ServerUtils {

	protected final static Log logger = LogFactory.getLog(ServerUtils.class);
	
	public static boolean testCeksCatureReady(String testLink, String testString){
		boolean ceksReady = false;
		try{
			//For testing
			
			logger.info("testLink: " + testLink);
			logger.info("testString: " + testString);
			//URL ceksTestURL = new URL(testLink);
			logger.info("Open Connection to " + testLink);
			URLConnection urlConnection = getDummySSLConnection(testLink);//ceksTestURL.openConnection();
			logger.info("set Timeout");
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
			logger.info("URLContent: " + URLContent);
			if(testString.equalsIgnoreCase(URLContent)){
				ceksReady = true;
			}else{
				ceksReady = false;
			}
		} catch(Exception e) {
			//ceksReady = false;
			ceksReady = true;
			logger.error("Error while testCeksCatureReady", e);
		}
		
		return ceksReady;

	}
	
	private static HostnameVerifier hostnameVerifier = new DummyHostnameVerifier();
	private static SSLSocketFactory sslSocketFactory = null;

	public static URLConnection getDummySSLConnection(String url) throws Exception{
		
		URLConnection conn = new URL(url).openConnection();
		if (conn instanceof HttpsURLConnection) {
			trustAllSSLConnection((HttpsURLConnection)conn);
		}
		return conn;
			
					
	}
	
	private static void initSocketFactory() throws Exception {
		if (sslSocketFactory==null){
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(new KeyManager[0],
					new TrustManager[] { new DefaultTrustManager() },
					new SecureRandom());
			sslSocketFactory = ctx.getSocketFactory();
		}
	}
	
	public static HttpsURLConnection trustAllSSLConnection(HttpsURLConnection conn) throws Exception {
		if (sslSocketFactory==null){
			initSocketFactory();
		}
		
		conn.setSSLSocketFactory(sslSocketFactory);
		conn.setHostnameVerifier(hostnameVerifier);
		return conn;
	}
	
	public static class DummyHostnameVerifier implements HostnameVerifier {

		public boolean verify(String urlHost, SSLSession sslSession) {
			return true;
		}
	}

	public static class DefaultTrustManager implements X509TrustManager {

		public void checkClientTrusted(
				java.security.cert.X509Certificate[] arg0, String arg1)
				throws java.security.cert.CertificateException {
		}

		public void checkServerTrusted(
				java.security.cert.X509Certificate[] arg0, String arg1)
				throws java.security.cert.CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

	}
	
}
