package com.bomwebportal.lts.util.acq;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketTimeoutException;
import java.util.TreeMap;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import com.bomwebportal.lts.dto.acq.LtsAcqFaxImageDTO;
import com.bomwebportal.web.util.ReportRepository;
import com.google.gson.Gson;

public class LtsAcqFaxSerialHelper {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private static final String FAX_URL = "getimage/retrievefax.asp";
	//private static final String FAX_HOST = "http://10.193.43.33/";
	
	private static final String imageNotFoundMessage = "Image File Not Found"; 	

	public final String FAX_ERROR_BLANK_FAX_SERIAL = "Blank fax serial value";
	public final String FAX_ERROR_IMAGE_NOT_FOUND = "No fax image can be found for input fax serial: ";
	public final String FAX_ERROR_UNABLE_TO_GET_FILE_PATH = "Unable to obtain file path: ";
	public final String FAX_ERROR_EMPTY_HTTP_CONTENT = "Empty http content";
	public final String FAX_ERROR_BLANK_URL = "Blank url value(s)";
	public final String FAX_ERROR_CONNECTION_TIMEOUT = "Connection timeout";
	public final String FAX_SUCCESS = "SUCCESS";
	
	private String environment;
	private String dataFilePath;
	private String faxFileUrl;
	
	private ReportRepository reportRepository;
	
	public LtsAcqFaxImageDTO retrieveFaxImageUrl(String pFaxSerial, boolean isPipbOrder) {
		LtsAcqFaxImageDTO result = new LtsAcqFaxImageDTO();
		String rtnString = null;
		
		try{
			HttpClient httpClient = HttpClientHelper.getHttpClient(environment);
			
			if(StringUtils.isBlank(pFaxSerial)) {
				result.setErrorMsg(FAX_ERROR_BLANK_FAX_SERIAL);
				return result;
			}
			
			result.setFaxSerial(pFaxSerial);
			
			String team = pFaxSerial.replaceAll("\\d+", ""); 		//removing numbers from the order id, team name remains
			String url = faxFileUrl + FAX_URL + "?p1=" + team + "&p2=" + pFaxSerial;
			
			if(isPipbOrder){
				url = url + "&enquiry_only=N";
			}
			else{
				url = url + "&enquiry_only=Y";
			}
			
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity resEntity = response.getEntity();
			if(resEntity != null){
				rtnString = EntityUtils.toString(resEntity);
				FaxImageResult faxImageResult = null;
				Gson gson = new Gson();
				faxImageResult = gson.fromJson (rtnString, FaxImageResult.class);
				
				if(faxImageResult != null){
					if(imageNotFoundMessage.equals(faxImageResult.getGetFileUrl())){
						result.setErrorMsg(FAX_ERROR_IMAGE_NOT_FOUND + pFaxSerial);
					}
					else{
						result.setFileImageUrl(faxImageResult.getGetFileUrl());
						result.setFileRemoveUrl(faxImageResult.getDelFileUrl());
					}
				}
				else{
					result.setErrorMsg(FAX_ERROR_UNABLE_TO_GET_FILE_PATH + url);
				}
			}
			else{
				result.setErrorMsg(FAX_ERROR_EMPTY_HTTP_CONTENT);
			}
			
			return result;
		}
		catch(ConnectTimeoutException e){
			logger.error(ExceptionUtils.getFullStackTrace(e));
			result.setErrorMsg(FAX_ERROR_CONNECTION_TIMEOUT);
			return result;
		}
		catch(SocketTimeoutException e){
			logger.error(ExceptionUtils.getFullStackTrace(e));
			result.setErrorMsg(FAX_ERROR_CONNECTION_TIMEOUT);
			return result;
		}
		catch(Exception e){
			logger.error(ExceptionUtils.getFullStackTrace(e));
			if(StringUtils.isNotBlank(rtnString)){
				result.setErrorMsg("Return: " + rtnString + "\n" + e.toString());
			}else{
				result.setErrorMsg(e.toString());
			}
			return result;
		}
	}
	
	public String getFaxImage(LtsAcqFaxImageDTO faxImageInfo, String pOrderId, String pFileName, TreeMap<String, String> pFaxSerialMap){
		try{
			HttpClient httpClient = HttpClientHelper.getHttpClient(environment);
			
			if(faxImageInfo == null || StringUtils.isBlank(faxImageInfo.getFaxSerial()) || StringUtils.isBlank(faxImageInfo.getFileImageUrl()) 
					|| StringUtils.isBlank(faxImageInfo.getFileRemoveUrl()) || StringUtils.isBlank(environment)) {
				return FAX_ERROR_BLANK_URL;
			}
			
			if(pFaxSerialMap.get(faxImageInfo.getFaxSerial()) == null || StringUtils.isBlank(pFaxSerialMap.get(faxImageInfo.getFaxSerial()))){						
				HttpGet httpget = new HttpGet(faxImageInfo.getFileImageUrl());
				HttpResponse getFileResult = httpClient.execute(httpget);
				if(HttpStatus.SC_OK == getFileResult.getStatusLine().getStatusCode()){
					HttpEntity entity = getFileResult.getEntity();
					if (entity != null) {
						InputStream bis = entity.getContent();
						System.out.println("Retrieved file: " + entity.getContentType()); 
						//String fileName = faxImageInfo.getFileImageUrl().substring(StringUtils.indexOf(faxImageInfo.getFileImageUrl(), faxImageInfo.getFaxSerial()));
						String errMsg = saveFaxImage(bis, pFileName, pOrderId);				
						if(StringUtils.isBlank(errMsg)){
							try{
								pFaxSerialMap.put(faxImageInfo.getFaxSerial(), pFileName);
								HttpGet deleteFileHttpGet = new HttpGet(faxImageInfo.getFileRemoveUrl());
								httpClient.execute(deleteFileHttpGet);
							}catch(Exception e){
								
							}
							httpClient.getConnectionManager().shutdown();
							return FAX_SUCCESS;
						}
						else{
							httpClient.getConnectionManager().shutdown();
							return errMsg;
						}
					}
				}
				else{
					httpClient.getConnectionManager().shutdown();
					return "Http Status: " + getFileResult.getStatusLine().getStatusCode() + ", " 
							+ FAX_ERROR_UNABLE_TO_GET_FILE_PATH + faxImageInfo.getFileImageUrl() + "\n"
							+ getFileResult.getEntity().toString();
				}
			}
			else{
				File targetDir = new File(dataFilePath + pOrderId + "/");
				if (!targetDir.isDirectory()) {
					targetDir.mkdir();
				}
				
				File source = new File(dataFilePath + pOrderId + "/" + pFaxSerialMap.get(faxImageInfo.getFaxSerial()));
				if(source != null){
					File destFile = new File(targetDir, pFileName);
					FileUtils.copyFile(source, destFile);
					return FAX_SUCCESS;
				}
			}
			return FAX_ERROR_UNABLE_TO_GET_FILE_PATH + faxImageInfo.getFileImageUrl();
		}
		catch(ConnectTimeoutException e){
			logger.error(ExceptionUtils.getFullStackTrace(e));
			return FAX_ERROR_CONNECTION_TIMEOUT;
		}
		catch(SocketTimeoutException e){
			logger.error(ExceptionUtils.getFullStackTrace(e));
			return FAX_ERROR_CONNECTION_TIMEOUT;
		}
		catch(Exception e){
			logger.error(ExceptionUtils.getFullStackTrace(e));
			return e.toString();
		}
	}
	
	private String saveFaxImage(InputStream pFile, String pFileName, String pOrderId){		
		File targetDir = new File(dataFilePath + pOrderId + "/");
		if (!targetDir.isDirectory()) {
			targetDir.mkdir();
		}
		
		try {
			File destFile = new File(targetDir, pFileName);
			File tempFile = File.createTempFile("faxImage_" + pOrderId, ".tif");	
			tempFile.deleteOnExit();
			OutputStream outputStream = new FileOutputStream(tempFile);
			IOUtils.copy(pFile, outputStream);
			reportRepository.convertFile(destFile, tempFile, pFileName);
		} catch (IOException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			return e.getMessage();
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			return e.getMessage();
		}
		return null;
	}
	
	class FaxImageResult {
		private String getFileUrl;
		private String delFileUrl;

		public String getGetFileUrl() {
			return getFileUrl;
		}

		public void setGetFileUrl(String getFileUrl) {
			this.getFileUrl = getFileUrl;
		}

		public String getDelFileUrl() {
			return delFileUrl;
		}

		public void setDelFileUrl(String delFileUrl) {
			this.delFileUrl = delFileUrl;
		}
	}

	public String getDataFilePath() {
		return dataFilePath;
	}

	public void setDataFilePath(String dataFilePath) {
		this.dataFilePath = dataFilePath;
	}

	public String getFaxFileUrl() {
		return faxFileUrl;
	}

	public void setFaxFileUrl(String faxFileUrl) {
		this.faxFileUrl = faxFileUrl;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public ReportRepository getReportRepository() {
		return reportRepository;
	}

	public void setReportRepository(ReportRepository reportRepository) {
		this.reportRepository = reportRepository;
	}
}
