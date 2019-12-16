/*
 * Created on Dec 02, 2011
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.bomwebportal.lts.service;

import java.text.SimpleDateFormat;
import java.util.Date;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.lts.wsClientLts.ViQuadplayWsClient;
import com.bomwebportal.lts.wsViQuadplayClient.SBGetARPUResponse;
import com.bomwebportal.lts.wsViQuadplayClient.SBGetProfileServiceResponse;
import com.bomwebportal.lts.wsViQuadplayClient.SBGetProfileSubscribedPlanResponse;
import com.bomwebportal.lts.wsViQuadplayClient.SBIsTVBSubscriberResponse;


public class LtsViQuadplayServiceImpl implements LtsViQuadplayService {
	
	private final Log logger = LogFactory.getLog(getClass());
	
	private ViQuadplayWsClient viQuadplayWsClient;

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
	
	public float getARPU(String pFsa) throws Exception {
		try {
			SBGetARPUResponse response = viQuadplayWsClient.getARPU(pFsa);			
			return response.getAmount();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw ex;
		}		
	}
	
	public void setDateOfBirth(String pFsa, Date pDOB, String pModifiedBy) throws Exception {
		try {
			viQuadplayWsClient.setDateOfBirth(pFsa,pDOB, pModifiedBy);
			return;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw ex;
		}				
	}
	
	public void setDateOfBirth(String pFsa, String pDOByyyyMMdd, String pModifiedBy) throws Exception {
		try {
  		    SimpleDateFormat dateFormatyyyyMMdd = new SimpleDateFormat("yyyyMMdd");
		    setDateOfBirth(pFsa,dateFormatyyyyMMdd.parse(pDOByyyyMMdd),pModifiedBy);
		    return;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw ex;
		}				
	}
	
	public boolean isTVBSubscriber(String pFsa) throws Exception {
		try {
			SBIsTVBSubscriberResponse response = viQuadplayWsClient.isTVBSubscriber(pFsa);
			return response.isSubscriber();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw ex;
		}			
	}
	
	public void setAdultContentReceivable(String pFsa, boolean pReceiveAdultContent, String pModifiedBy) throws Exception {
		try {
			viQuadplayWsClient.setAdultContentReceivable(pFsa,pReceiveAdultContent,pModifiedBy);
			return;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw ex;
		}			
	}
	

	public ViQuadplayWsClient getViQuadplayWsClient() {
		return viQuadplayWsClient;
	}

	public void setViQuadplayWsClient(ViQuadplayWsClient viQuadplayWsClient) {
		this.viQuadplayWsClient = viQuadplayWsClient;
	}	
}
