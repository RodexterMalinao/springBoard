package com.bomwebportal.ims.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.lts.wsClientLts.ViQuadplayWsClient;
import com.bomwebportal.lts.wsViQuadplayClient.SBGetProfileServiceResponse;
import com.bomwebportal.lts.wsViQuadplayClient.SBGetProfileSubscribedPlanResponse;

public class ImsViQuadplayServiceImpl implements ImsViQuadplayService{
	protected final Log logger = LogFactory.getLog(getClass());
	private ViQuadplayWsClient viQuadplayWsClient;
	public ViQuadplayWsClient getViQuadplayWsClient() {
		return viQuadplayWsClient;
	}

	public void setViQuadplayWsClient(ViQuadplayWsClient viQuadplayWsClient) {
		this.viQuadplayWsClient = viQuadplayWsClient;
	}	
	
	
	
	
	public SBGetProfileServiceResponse getProfileService(String pFsa) throws Exception {
		try {
			return viQuadplayWsClient.getProfileService(pFsa);			
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw ex;
		}
	}
	
	public SBGetProfileSubscribedPlanResponse getProfileSubscirbedPlan(String pFsa) throws Exception {
		try {
			return viQuadplayWsClient.getProfileSubscirbedPlan(pFsa);			
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw ex;
		}
	}
}
