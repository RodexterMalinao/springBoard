package com.bomwebportal.offercode.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import com.bomwebportal.offercode.exception.AppServiceException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class OfferCodeReqService {

	//@Value("${sbext.mobofferpin.username:bomsbapi}")
	String username;
	
	//@Value("${sbext.mobofferpin.password:ipabsmob}")
	String password;
	
	//@Value("${sbext.mobofferpin.baseUrl}")
	String apiBaseUrl = "http://localhost:8080/SBExt/rs/api/mobofferpin";
	
	Gson gson = new Gson();
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getApiBaseUrl() {
		return apiBaseUrl;
	}

	public void setApiBaseUrl(String apiBaseUrl) {
		this.apiBaseUrl = apiBaseUrl;
	}

	public String  requestCode(String mobilenum, Locale locale) throws AppServiceException {
		return requestCode(mobilenum, "BomWebPortal", locale);
	}
	
	public String  requestCode(String mobilenum, String requestedBy, Locale locale) throws AppServiceException {
		HttpURLConnection conn = null;
		OutputStream os = null;
		try {
			String url = apiBaseUrl+"/request.json";
			conn = null;

			String userPassword = username + ":" + password;
			String encoded = new String(Base64.encodeBase64(userPassword.getBytes(), false),  "UTF-8");
			conn = (HttpURLConnection)new URL(url).openConnection();
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Authorization", "Basic " + encoded);
			conn.setRequestProperty("Accept-Language", locale.getLanguage());
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			List<NameValuePair> nvlist = new ArrayList<NameValuePair>();
			BasicNameValuePair nv = new BasicNameValuePair("mrt", mobilenum);
			nvlist.add(nv);
			nvlist.add(new BasicNameValuePair("requestedBy", requestedBy));
			
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvlist);
			os = conn.getOutputStream();

			formEntity.writeTo(os);
			
			os.flush();
			
			int httpStatus = conn.getResponseCode();
			
			InputStream is = null;
			if (httpStatus == 200) {
				// Successful..
				is = conn.getInputStream();
			} else {
				// errors ...
				is = conn.getErrorStream();
			}
			
	
			BufferedReader br = new BufferedReader(new InputStreamReader(
					is, "UTF-8"));
			StringBuffer sb = new StringBuffer();
		
			String s;
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			//System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR " + sb);
			Map<String, String> map = new HashMap<String, String>();
			Type mapType = new TypeToken<Map<String, String>>(){}.getType();
			if (httpStatus == 200) {
				map = gson.fromJson(sb.toString(), mapType);
				return (String)map.get("pin");
			} else if (httpStatus == 400) {
				//map = gson.fromJson(sb.toString(), mapType);
				Error err = gson.fromJson(sb.toString(), Error.class);
				throw new AppServiceException( err.getCode(), err.getMessage());
			} else {
				throw new AppServiceException("29999", "Unexpected Error");
			}
			
			/*
			System.out.println("status=" + httpStatus);
			System.out.println("result=" + sb);
			System.out.println("result=" + map);
			*/
			
		} catch (IOException e) {
			throw new AppServiceException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(os);
			if (conn != null) {
				conn.disconnect();
			}
		}
	}
	
	static class Error {
		int status;
		String code;
		String message;
		String systemMessage;
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getSystemMessage() {
			return systemMessage;
		}
		public void setSystemMessage(String systemMessage) {
			this.systemMessage = systemMessage;
		}
		
	}

}
