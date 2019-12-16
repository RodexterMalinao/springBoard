package com.bomwebportal.sso.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.pccw.paska.server.json.AllocEtJson;
import com.pccw.paska.server.json.IsAliveJson;
import com.pccw.paska.server.json.LoginJson;
import com.pccw.paska.server.json.VerifyEtJson;

public class SSOService {
	
	private Gson gson = new Gson();
	
	private final Log logger = LogFactory.getLog(getClass());
		
	//@Value("${sso.isAliveUrl}")
	private String isAliveUrl = "http://10.87.120.207:8180/paska/jsonApi/isAlive" ;
	
	//@Value("${sso.verifyEtUrl}")
	private String verifyEtUrl = "http://10.87.120.207:8180/paska/jsonApi/verifyEt";
	
	//@Value("${sso.verifyEtUrl}")
	private String allocEtUrl = "http://10.87.120.207:8180/paska/jsonApi/allocEt";
	
	private String loginUrl=   "http://10.87.120.207:8180/paska/jsonApi/login";
	
	private String rejoinUrl;
	
	public String getIsAliveUrl() {
		return isAliveUrl;
	}

	public void setIsAliveUrl(String isAliveUrl) {
		this.isAliveUrl = isAliveUrl;
	}

	public String getVerifyEtUrl() {
		return verifyEtUrl;
	}

	public void setVerifyEtUrl(String verifyEtUrl) {
		this.verifyEtUrl = verifyEtUrl;
	}

	public String getAllocEtUrl() {
		return allocEtUrl;
	}

	public void setAllocEtUrl(String allocEtUrl) {
		this.allocEtUrl = allocEtUrl;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getRejoinUrl() {
		return rejoinUrl;
	}

	public void setRejoinUrl(String rejoinUrl) {
		this.rejoinUrl = rejoinUrl;
	}

	public boolean isAlive(String pAppId, String pUsiHash){
		HttpURLConnection conn = null;
		BufferedReader br = null;
		OutputStreamWriter os = null;
		try {
			conn = (HttpURLConnection)getDummySSLConnection(isAliveUrl);
			//conn = (HttpURLConnection)new URL(isAliveUrl).openConnection();
			conn.setRequestProperty("Content-Type", "application/json");
			HashMap<String,String> p = new HashMap<String,String>();
			p.put("apiTy", "ISALIVE");
			p.put("iAppId", pAppId);
			p.put("iUsiHash", pUsiHash);
			
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			os = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");System.out.println(gson.toJson(p));
			os.write(gson.toJson(p));
			os.flush();
			br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			StringBuffer sb = new StringBuffer();
		
			String s;
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			System.out.println(sb);
			logger.debug("isAlive response=" + sb);
			IsAliveJson isAliveJson = gson.fromJson(sb.toString(), IsAliveJson.class);
			return isAliveJson.oAlive;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while checking sso hash isAlive", e);
		} finally {
			IOUtils.closeQuietly(os);
			IOUtils.closeQuietly(br);
		}
		return false;
	}

	public VerifyEtJson verifyEtCheck(String pAppId, String pETicket) {
		logger.debug("Verifying ticket appId="+pAppId+ ", SSO_ET=" + pETicket);
		HttpURLConnection conn = null;
		BufferedReader br = null;
		OutputStreamWriter os = null;
		try {
//			conn = (HttpURLConnection)new URL(verifyEtUrl).openConnection();
			conn = (HttpURLConnection)getDummySSLConnection(verifyEtUrl);
			conn.setRequestProperty("Content-Type", "application/json");
			HashMap<String,String> p = new HashMap<String,String>();
			p.put("apiTy", "VERIFYET");
			p.put("iAppId", pAppId);
			p.put("iEt",pETicket);
			
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			os = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			os.write(gson.toJson(p));
			os.flush();
			br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			StringBuffer sb = new StringBuffer();
		
			String s;
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			logger.debug("Verify ticket response=" + sb);
			VerifyEtJson verf = gson.fromJson(sb.toString(), VerifyEtJson.class);
			return verf;
		} catch (Exception e) {
			logger.error("Error while verifying sso ticket", e);
		} finally {
			IOUtils.closeQuietly(os);
			IOUtils.closeQuietly(br);
		}
		return null;
	}

	
	
	public String allocEt(String pAppId, String pHash, String pTargetAppId) {
		HttpURLConnection conn = null;
		BufferedReader br = null;
		OutputStreamWriter os = null;
		
		if (StringUtils.isEmpty(pTargetAppId)) {
			pTargetAppId = pAppId;
		}
		try {
			//conn = (HttpURLConnection)new URL(allocEtUrl).openConnection();
			conn = (HttpURLConnection)getDummySSLConnection(allocEtUrl);
			conn.setRequestProperty("Content-Type", "application/json");
			HashMap<String,String> p = new HashMap<String,String>();
			p.put("apiTy", "ALLOCET");
			p.put("iAppId", pAppId);
			p.put("iUsiHash", pHash);
			p.put("iDesAppId", pTargetAppId);
			
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			os = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");System.out.println(gson.toJson(p));
			os.write(gson.toJson(p));
			os.flush();
			br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			StringBuffer sb = new StringBuffer();
		
			String s;
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			
			logger.debug("allocEt response=" + sb);
			AllocEtJson verf = gson.fromJson(sb.toString(), AllocEtJson.class);
			return verf.oEt;
		} catch (Exception e) {
			logger.error("Error while alloc sso ticket", e);
		} finally {
			IOUtils.closeQuietly(os);
			IOUtils.closeQuietly(br);
		}
		return null;
	}
	
	
	public String login(String pAppId, String pUserId, String pPassword) {
		HttpURLConnection conn = null;
		BufferedReader br = null;
		OutputStreamWriter os = null;
		
		try {
			//conn = (HttpURLConnection)new URL(loginUrl).openConnection();
			conn = (HttpURLConnection)getDummySSLConnection(loginUrl);
			conn.setRequestProperty("Content-Type", "application/json");
			HashMap<String,String> p = new HashMap<String,String>();
			p.put("apiTy", "LOGIN");
			p.put("iAppId", pAppId);
			p.put("iPwd", pPassword);
			p.put("iUsrId", pUserId);
			
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			os = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			os.write(gson.toJson(p));
			os.flush();
			br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			StringBuffer sb = new StringBuffer();
		
			String s;
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			logger.debug("login response=" + sb);
			LoginJson login = gson.fromJson(sb.toString(), LoginJson.class);
			if (login != null && "RC_SUCC".equals(login.reply) ) {
				return login.oUsiHash;
			}
			return null;
		} catch (Exception e) {
			logger.error("Error while login sso", e);
		} finally {
			IOUtils.closeQuietly(os);
			IOUtils.closeQuietly(br);
		}
		return null;
	}
	
	/*
	public String createRejoinUrl(String pTarget, String pFail) {
		return createRejoinUrl(pAppId, pTarget, pFail);
	}
	*/
	
	// dont need self appid
	public String createRejoinUrl(String pTargetAppId, String pTarget, String pFail) throws UnsupportedEncodingException {
		return rejoinUrl + "?a=" + pTargetAppId + "&go="+URLEncoder.encode(pTarget, "UTF-8")+"&back="+URLEncoder.encode(pFail, "UTF-8");
	}
	
	private static SSLSocketFactory sslSocketFactory = null;
	private static HostnameVerifier hostnameVerifier = new DummyHostnameVerifier();

	
	private static void initSocketFactory() throws Exception {
		if (sslSocketFactory==null){
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(new KeyManager[0],
					new TrustManager[] { new DefaultTrustManager() },
					new SecureRandom());
			sslSocketFactory = ctx.getSocketFactory();
		}
	}
	
	public static URLConnection getDummySSLConnection(String url) throws Exception {
		
		
		if (sslSocketFactory==null){
			initSocketFactory();
		}
		
		
		URLConnection conn = new URL(url).openConnection();
		if (conn instanceof HttpsURLConnection) {
			HttpsURLConnection httpsConn = (HttpsURLConnection)conn;
			httpsConn.setHostnameVerifier(hostnameVerifier);
			httpsConn.setSSLSocketFactory(sslSocketFactory);
		}
		return conn;
			
					
	}
	
	public static void main(String args[]) {
//		new SSOService().login("TMOBA011", "sbmobuat11q");
//		
//		new SSOService().isAlive("aaaaaaaaaaaa");
//		new SSOService().verifyEtCheck("aaaaaaaaaaaa");
//		new SSOService().allocEt("aaaaaaaaaa", "SBRET_MOB");
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
	
	public static class DummyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String urlHostName, String certHostName) {
			return true;
		}

		public boolean verify(String urlHost, SSLSession sslSession) {
			return true;
		}
	}
}
