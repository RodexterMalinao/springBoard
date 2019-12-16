package com.bomwebportal.ims.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.configuration.BomPropertyPlaceholderConfigurer;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.ims.service.ImsSyncOrderService;
import com.bomwebportal.util.BomWebPortalConstant;

public class ImsAutoProcess {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private BomPropertyPlaceholderConfigurer propertyConfigurer;
	private ImsSyncOrderService service;
	
	public void autoSyncBomWebImsOrder(){
		logger.info("autoSyncBomWebImsOrder is starting");
		String appEnv = "";
		
		try{
			appEnv = propertyConfigurer.getMergedProperties().getProperty(
					BomWebPortalConstant.APP_ENV);
			//appEnv = "uat"; //temp use
			logger.info("Environment: " + appEnv);
			
			if (!"dev".equals(appEnv)) {
				service.syncOrderToBOM();			
			}else{
				logger.info("dev env skipping the auto process...");
			}
			
		}catch(Exception e){
			logger.error("Exception caught in autoSyncBomWebImsOrder()", e);
			throw new AppRuntimeException(e);
		}
		
		logger.info("autoSyncBomWebImsOrder completed");
	}

	public BomPropertyPlaceholderConfigurer getPropertyConfigurer() {
		return propertyConfigurer;
	}

	public void setPropertyConfigurer(
			BomPropertyPlaceholderConfigurer propertyConfigurer) {
		this.propertyConfigurer = propertyConfigurer;
	}

	public ImsSyncOrderService getService() {
		return service;
	}

	public void setService(ImsSyncOrderService service) {
		this.service = service;
	}
	
	

}
