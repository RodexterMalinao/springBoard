/**
 * 
 */
package com.activity.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.task.TaskExecutor;

import com.bomwebportal.dto.WqStatusChangedActionDTO;
import com.bomwebportal.service.lts.activity.WorkQueueStatusChangeService;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

// @author MAX.R.MENG LTS
public class WorkQueueStatusChangeServiceSCSImpl implements WorkQueueStatusChangeService{

	protected final Log logger = LogFactory.getLog(getClass());
	private String url;
	private int scsRequestTimeOut;
	private TaskExecutor taskExecutor;
	private WqStatusChangedActionDTO wqStatus;
	@Override
	public void statusChangedAction(String pWqWpAssgnId, String pSbId, String pSbDtlId, 
			String pSbActvId, String pWqStatus, String[] pWqNatureIds, String pRemarks, String pUserId) throws Exception {

		
		wqStatus = new WqStatusChangedActionDTO();
		wqStatus.setWqWpAssgnId(pWqWpAssgnId);
		wqStatus.setpSbId(pSbActvId);
		wqStatus.setpSbDtlId(pSbDtlId);
		wqStatus.setpSbActvId(pSbActvId);
		wqStatus.setpWqStatus(pWqStatus);
		wqStatus.setpWqNatureIds(pWqNatureIds);
		wqStatus.setpRemarks(pRemarks);
		wqStatus.setpUser(pUserId);        
        
        taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub			
				Gson gson = new Gson();	  
				String input = gson.toJson(wqStatus);
				ClientConfig clientConfig = new DefaultClientConfig();				
				clientConfig.getFeatures().put(
						JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
				Client client = Client.create(clientConfig);
			    
			    client.setConnectTimeout(scsRequestTimeOut);
			    client.setReadTimeout(scsRequestTimeOut);
			    
				//url = URLEncoder.encode(url, "UTF-8"); 
				WebResource webResource = client
						.resource(url);
				
				logger.info("request " + url + " begin... Timeout: " + scsRequestTimeOut);
				
				ClientResponse response = webResource.accept("application/json")
						.type("application/json").put(ClientResponse.class, input);

				logger.info("request " + url + " end... Timeout: " + scsRequestTimeOut + " body and status " + response.getHeaders() + "Status " +response.getStatus());
				if (response.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ response.getStatus() + " message body"  + response.toString());
				}
				String output = response.getEntity(String.class);
				System.out.println("Server response .... \n");
				System.out.println(output);
				
				
			}});
        

	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getScsRequestTimeOut() {
		return scsRequestTimeOut;
	}
	public void setScsRequestTimeOut(int scsRequestTimeOut) {
		this.scsRequestTimeOut = scsRequestTimeOut;
	}
	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}
	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	public WqStatusChangedActionDTO getWqStatus() {
		return wqStatus;
	}
	public void setWqStatus(WqStatusChangedActionDTO wqStatus) {
		this.wqStatus = wqStatus;
	}
}
