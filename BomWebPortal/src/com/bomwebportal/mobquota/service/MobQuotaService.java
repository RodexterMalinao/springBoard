package com.bomwebportal.mobquota.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.mobquota.dto.MobQuotaUsageDTO;
import com.bomwebportal.mobquota.dto.QuotaConsumeRequest;
import com.bomwebportal.mobquota.dto.QuotaItem;
import com.bomwebportal.mobquota.exception.AppServiceException;
import com.bomwebportal.util.ServerUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class MobQuotaService {
	
	private final static int CONN_TIMEOUT = 20000;
	
	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
	
	private final Log logger = LogFactory.getLog(getClass());
	
	private String quotaServiceUrlBase = "http://localhost:8080/SBExt/rs/api/mobquota";
	
	private String username = "bomsbapi";
	private String password = "ipabsmob";
	
	

	
	public String getQuotaServiceUrlBase() {
		return quotaServiceUrlBase;
	}


	public void setQuotaServiceUrlBase(String quotaServiceUrlBase) {
		this.quotaServiceUrlBase = quotaServiceUrlBase;
	}


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


	public MobQuotaUsageDTO[] getMobQuotaUsageList(String docType, String docNum) throws com.bomwebportal.mobquota.exception.AppServiceException {
		HttpURLConnection conn = null;
		OutputStream os = null;
		try {
			String url = quotaServiceUrlBase + "/usage.json?docType="+docType+"&docNum="+docNum;
			
			conn = (HttpURLConnection)ServerUtils.getDummySSLConnection(url);
			setHeaders(conn);
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			
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

			if (httpStatus == 200) {
				
				MobQuotaUsageDTO[] dto = gson.fromJson(sb.toString(), MobQuotaUsageDTO[].class);
				return dto;
				
			} else if (httpStatus == 400) {
				//map = gson.fromJson(sb.toString(), mapType);
				Error err = gson.fromJson(sb.toString(), Error.class);
				throw new AppServiceException( err.getCode(), err.getMessage());
			} else {
				throw new AppServiceException("29999", "Unexpected Error");
			}
			
		} catch (Exception e) {
			throw new AppServiceException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(os);
			if (conn != null) {
				conn.disconnect();
			}
		}
		
	}
	
	
	public MobQuotaUsageDTO[] mockConsumeQuota(QuotaConsumeRequest qcr) throws AppServiceException {
		HttpURLConnection conn = null;
		OutputStream os = null;
		try {
			String url = quotaServiceUrlBase + "/mockconsume.json";
			
			conn = (HttpURLConnection)ServerUtils.getDummySSLConnection(url);
			setHeaders(conn);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			
			String req = new Gson().toJson(qcr);
			OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			osw.write(req);
			osw.flush();
			
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

			if (httpStatus == 200) {
				
				MobQuotaUsageDTO[] dto = gson.fromJson(sb.toString(), MobQuotaUsageDTO[].class);
				return dto;
				
			} else if (httpStatus == 400) {
				//map = gson.fromJson(sb.toString(), mapType);
				Error err = gson.fromJson(sb.toString(), Error.class);
				throw new AppServiceException( err.getCode(), err.getMessage());
			} else {
				throw new AppServiceException("29999", "Unexpected Error");
			}
			
		} catch (Exception e) {
			throw new AppServiceException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(os);
			if (conn != null) {
				conn.disconnect();
			}
		}
		
	}	
	

	public MobQuotaUsageDTO[] consumeQuota(QuotaConsumeRequest qcr) throws AppServiceException {
		HttpURLConnection conn = null;
		OutputStream os = null;
		try {
			String url = quotaServiceUrlBase + "/consume.json";
			
			conn = (HttpURLConnection)ServerUtils.getDummySSLConnection(url);
			setHeaders(conn);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			
			String req = new Gson().toJson(qcr);
			OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			osw.write(req);
			osw.flush();
			
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

			if (httpStatus == 200) {
				
				MobQuotaUsageDTO[] dto = gson.fromJson(sb.toString(), MobQuotaUsageDTO[].class);
				return dto;
				
			} else if (httpStatus == 400) {
				//map = gson.fromJson(sb.toString(), mapType);
				Error err = gson.fromJson(sb.toString(), Error.class);
				throw new AppServiceException( err.getCode(), err.getMessage());
			} else {
				throw new AppServiceException("29999", "Unexpected Error");
			}
			
		} catch (Exception e) {
			throw new AppServiceException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(os);
			if (conn != null) {
				conn.disconnect();
			}
		}
		
	}	
	
	public void cancelQuotaUsage(String orderType, String orderId, String updatedBy) throws AppServiceException {
		HttpURLConnection conn = null;
		OutputStream os = null;
		try {
			String url = quotaServiceUrlBase + "/usage.json?orderType="+orderType+"&orderId="+orderId+"&updatedBy="+updatedBy;
			
			conn = (HttpURLConnection)ServerUtils.getDummySSLConnection(url);
			setHeaders(conn);
			conn.setRequestMethod("DELETE");
			conn.setDoOutput(true);
			
			int httpStatus = conn.getResponseCode();
			InputStream is = null;
			if (httpStatus >=200 && httpStatus < 300) {
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

			if (httpStatus == 204) {
				
				return;
				
			} else if (httpStatus == 400) {
				//map = gson.fromJson(sb.toString(), mapType);
				Error err = gson.fromJson(sb.toString(), Error.class);
				throw new AppServiceException( err.getCode(), err.getMessage());
			} else {
				throw new AppServiceException("29999", "Unexpected Error");
			}
			
		} catch (Exception e) {
			throw new AppServiceException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(os);
			if (conn != null) {
				conn.disconnect();
			}
		}
		
	}
	
	
	
	
	
	
	private void setHeaders(HttpURLConnection conn) {
		String userPassword = username + ":" + password;
		String encoded;
		try {
			encoded = new String(Base64.encodeBase64(userPassword.getBytes(), false),  "UTF-8");
			conn.setRequestProperty("Authorization", "Basic " + encoded);
			
			conn.setConnectTimeout(CONN_TIMEOUT);
			
		} catch (UnsupportedEncodingException e) {}
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
	
	/*
	private static class DateTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
		
		DateTimeFormatter dtf;
		private DateTypeAdapter() {
			dtf = ISODateTimeFormat.dateTime().withZone(DateTimeZone.forOffsetHours(8));
		}


		public synchronized JsonElement serialize(Date date, Type type,
				JsonSerializationContext jsonSerializationContext) {
			String dateFormatAsString = dtf.print(date.getTime());
			return new JsonPrimitive(dateFormatAsString);

		}


		public synchronized Date deserialize(JsonElement jsonElement, Type type,
				JsonDeserializationContext jsonDeserializationContext) {
			return dtf.parseDateTime(jsonElement.getAsString()).toDate();
			//throw new JsonSyntaxException(jsonElement.getAsString(), e);

		}
	}	
	*/
	
	public static void main(String args[]) {
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			MobQuotaService s = new MobQuotaService();
			MobQuotaUsageDTO[] dtos = s.getMobQuotaUsageList("HKID", "M000000(6)");
			System.out.println("dto=" + gson.toJson(dtos));
			
			QuotaConsumeRequest qcr = new QuotaConsumeRequest();
			qcr.setDocNum("M000000(6)");
			qcr.setDocType("HKID");
			qcr.setApplnDate("2015-05-07");
			qcr.setOrderId("RTTWM000001");
			qcr.setOrderType("S");
			qcr.setUpdatedBy("TMOBACONS");
			
			List<QuotaItem> items = new ArrayList<QuotaItem>();
			QuotaItem item = new QuotaItem();
			item.setQuotaId("1");
			item.setQuantity(1);
			item.setRemarks("remarks ............");
			item.setAuthBy("TMOBAAUTH");
			items.add(item);
			qcr.setItems(items);
			
			dtos = s.mockConsumeQuota(qcr);
			
			System.out.println("dto=" + gson.toJson(dtos));

			dtos = s.consumeQuota(qcr);
			
			System.out.println("dto=" + gson.toJson(dtos));
			
			s.cancelQuotaUsage("S", "RTTWM000001", "TMOBACNCL");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
